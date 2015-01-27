(function(angular){
var querySetting = angular.module('querySetting', ['ngRoute','ngMessages']);

querySetting.config(['$routeProvider', function($routeProvider){
    $routeProvider.
        when('/basic',{templateUrl:'/query/setting/create/basic'}).
        when('/store-basic-and-forward',{templateUrl:'/query/setting/create/store-basic-and-forward'}).
        when('/table',{templateUrl:'/query/setting/create/table'}).
        otherwise({redirectTo: '/basic'})
}]);

querySetting.controller('BasicController',['$http', '$location', '$scope', function($http, $location, $scope) {
    $scope.validate = function() {
        console.log('pristine:' + $scope.basicForm.$pristine);
        console.log('valid:' + $scope.basicForm.$valid);
        console.log('invalid:' + $scope.basicForm.$invalid);
        console.log('error:' + $scope.basicForm.name.$error);
        console.log('validators:' + $scope.basicForm.name.$validators);
        angular.forEach($scope.basicForm.$error, function(key, value) {
            console.log('key:' + key);
            console.log('value:' + value)
        });

    };
    $scope.submit = function() {
        if($scope.basicForm.$invalid) { return }
            $http.post('create/store-basic-and-forward.json',{name: $scope.name ,label:$scope.label }).
            success(function(data, status, headers, config) {
                $location.path('/table');
            }).
            error(function(data, status, headers, config) {
                alert('error')
            })
    }
}])
.directive('exists', function($q, $http){
    return {
        require : 'ngModel',
        link : function(scope, elm, attrs, ctrl) {
            ctrl.$asyncValidators.exists = function(mv, vv) {
                var defer = $q.defer();
                $http.get('tables/exists/' + mv + ".json").success(function(result){
                    if(result['exists'] == true) {
                        defer.reject();
                    } else {
                        defer.resolve();
                    }
                }).error(function(result) {
                    defer.resolve();
                });

                return defer.promise;
            }
        }
    }
})
    .directive('char', function() {
        return {
            require : 'ngModel',
            link : function(scop, elm, attrs, ctrl) {
                ctrl.$validators.char = function(mv, vv) {
                    if(ctrl.$isEmpty(mv)) {
                        return true;
                    }
                    return /^[0-9a-zA-z]+$/.test(vv);
                }
            }
        }
    });

querySetting.factory('DBInfoService',['$http', function($http) {
    var refreshTables = function() {
        return $http.get('create/tables.json')
    };

    return {
        refreshTables:refreshTables
    };
}]);

querySetting.controller('TableAndViewController',['$http', '$location','$scope', 'DBInfoService','$element',
    function($http, $location, $scope, DBInfoService, $element) {

    $scope.tablesSelected = [];
    $scope.tablesUnselected = [];
    $scope.joinedFiles = [];

    $scope.refresh = function() {
        DBInfoService.refreshTables().then(function(result) {
            $scope.tables = result.data.tables;
        });
    };

    $scope.submit = function() {
        $http.post('create/store-table-and-forward.json',{tables: $scope.tablesSelected}).
            success(function(data, status, headers, config) {
                $location.path('/condition');
            }).
            error(function(data, status, headers, config) {
                alert('error')
            })
    };

    $scope.add = function(table) {
        for(var i = 0; i < $scope.tablesUnselected.length; i ++) {
            if($scope.tablesUnselected[i] == table) {
                $scope.tablesUnselected.splice(i, 1);
                $scope.tablesSelected.push({name:table,alias:table});
                break;
            }
        }
    }

    $scope.delete = function(table) {
        for(var i = 0; i < $scope.tablesSelected.length; i ++) {
            if($scope.tablesSelected[i].name == table) {
                $scope.tablesSelected.splice(i, 1);
                $scope.tablesUnselected.push(table);
                break;
            }
        }
    }

    $scope.addField = function(table, field) {

    }

    DBInfoService.refreshTables().then(function(result) {
        $scope.tables = result.data.tables;
        $scope.tablesUnselected = result.data.tables;
    });
}]);
})(window.angular);