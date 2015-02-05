(function (angular) {

    var existsValidation = function ($q, $http) {
        return {
            require: 'ngModel',
            link: function (scope, elm, attrs, ctrl) {
                ctrl.$asyncValidators.exists = function (mv, vv) {
                    var defer = $q.defer();
                    if (ctrl.$isEmpty(mv || vv)) {
                        defer.resolve();
                    }
                    else {
                        $http.get(scope.existsUrl + mv + ".json").success(function (result) {
                            if (result['exists'] == true) {
                                defer.reject();
                            } else {
                                defer.resolve();
                            }
                        }).error(function (result) {
                            defer.resolve();
                        });
                    }
                    return defer.promise;
                }
            }
        }
    }

    var charValidation = function () {
        return {
            require: 'ngModel',
            link: function (scop, elm, attrs, ctrl) {
                ctrl.$validators.char = function (mv, vv) {
                    if (ctrl.$isEmpty(mv)) {
                        return true;
                    }
                    return /^[0-9a-zA-z]+$/.test(vv);
                }
            }
        }
    }

    var querySetting = angular.module('querySetting', ['ngRoute', 'ngMessages']);

    querySetting.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.
            when('/basic', {templateUrl: '/query/setting/create/basic'}).
            when('/store-basic-and-forward', {templateUrl: '/query/setting/create/store-basic-and-forward'}).
            when('/table', {templateUrl: '/query/setting/create/table'}).
            otherwise({redirectTo: '/basic'})
    }]);

    querySetting.controller('BasicController', ['$http', '$location', '$scope', function ($http, $location, $scope) {
        $scope.existsUrl = 'tables/exists/';

        $scope.submit = function () {
            if ($scope.basicForm.$invalid) {
                return
            }
            $http.post('create/store-basic-and-forward.json', {name: $scope.name, label: $scope.label }).
                success(function (data, status, headers, config) {
                    $location.path('/table');
                }).
                error(function (data, status, headers, config) {
                    alert('error')
                })
        }
    }])
        .directive('exists', existsValidation)
        .directive('char', charValidation);

    querySetting.factory('DBInfoService', ['$http', function ($http) {
        var refreshTables = function () {
            return $http.get('create/tables.json')
        };

        return {
            refreshTables: refreshTables
        };
    }]);

    querySetting.controller('TableAndViewController', ['$http', '$location', '$scope', 'DBInfoService', '$element',
        function ($http, $location, $scope, DBInfoService, $element) {
            $scope.tablesSelected = [];
            $scope.tablesUnselected = [];
            $scope.joinedFiles = [];

            $scope.refresh = function () {
                DBInfoService.refreshTables().then(function (result) {
                    $scope.tables = result.data.tables;
                });
            };

            $scope.submit = function () {
                $http.post('create/store-table-and-forward.json', angular.toJson($scope.tablesSelected)).
                    success(function (data, status, headers, config) {
                        $location.path('/condition');
                    }).
                    error(function (data, status, headers, config) {
                        alert('error')
                    })
            };

            $scope.add = function () {
                console.log($scope.table);
                $scope.tablesSelected.push({name: $scope.table, alias: $scope.alias});
            }

            $scope.delete = function (table) {
                for (var i = 0; i < $scope.tablesSelected.length; i++) {
                    if ($scope.tablesSelected[i].name == table) {
                        $scope.tablesSelected.splice(i, 1);
                        $scope.tablesUnselected.push(table);
                        break;
                    }
                }
            }

            $scope.addField = function (table, field) {

            }

            DBInfoService.refreshTables().then(function (result) {
                $scope.tables = result.data.tables;
                $scope.tablesUnselected = result.data.tables;
            });
        }])
        .directive('char', charValidation);
})(window.angular);