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
            when('/condition', {templateUrl:'/query/setting/create/condition'}).
            when('/field', {templateUrl:'/query/setting/create/field'}).
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

    /**
     * 数据库信息服务。此服务提供数据库的相关信息，如数据库表，数据库字段等
     */
    querySetting.factory('DBInfoService', ['$http', function ($http) {
        /**
         * 获取一个schema下所有的表的信息
         * @returns {*}
         */
        var refreshTables = function () {
            return $http.get('create/tables.json');
        };
        /**
         * 获取一个表所有的column信息
         * @param table 需要返回的表的名称
         * @returns {*}
         */
        var refreshColumns = function(table) {
            return $http.get('create/columns/' + table + '.json');
        }

        return {
            refreshTables: refreshTables,
            refreshColumns:refreshColumns
        };
    }]);

    /**
     * 数据表控制器
     */
    querySetting.controller('TableController', ['$http', '$location', '$scope', 'DBInfoService', '$element',
        function ($http, $location, $scope, DBInfoService, $element) {
            $scope.tablesSelected = [];
            $scope.tablesUnselected = [];
            $scope.columnsJoined = [];
            $scope.columnsInTableOne = [];
            $scope.columnsInTableTwo = [];
            $scope.tablesForTableOne = [];
            $scope.tablesForTableTwo = [];
            $scope.alias = '';

            $scope.refresh = function () {
                DBInfoService.refreshTables().then(function (result) {
                    $scope.tables = result.data.tables;
                });
            };

            $scope.submit = function () {
                $http.post('create/store-table-and-forward.json', angular.toJson({
                    tables:$scope.tablesSelected,
                    columns:$scope.columnsJoined
                })).
                    success(function (data, status, headers, config) {
                        $location.path('/condition');
                    }).
                    error(function (data, status, headers, config) {
                        alert('error')
                    })
            };

            $scope.add = function () {
                if($scope.tableForm.table.$valid && $scope.tableForm.alias.$valid) {
                    $scope.tablesSelected.push({name: $scope.table, alias: $scope.alias});
                    $scope.tablesForTableOne.push({name: $scope.table, alias: $scope.alias});
                    $scope.tablesForTableTwo.push({name: $scope.table, alias: $scope.alias});
                    $scope.alias = '';
                }
            }

            $scope.delete = function (table) {
                for (var i = 0; i < $scope.tablesSelected.length; i++) {
                    if ($scope.tablesSelected[i].name == table) {
                        $scope.tablesSelected.splice(i, 1);
                        $scope.tablesUnselected.push(table);
                        //删除之后，需要重新校验别名，因为可能重复的别名已经不存在
                        $scope.tableForm.alias.$validate();
                        break;
                    }
                }
            }

            /**
             *刷新第一个表下拉菜单
             */
            $scope.refreshWithTableOne = function() {
                if(angular.isUndefined($scope.tableTwo)) {
                    for (var i = 0; i < $scope.tablesForTableTwo.length; i++) {
                        if ($scope.tablesForTableTwo[i].alias == $scope.tableOne.alias) {
                            $scope.tablesForTableTwo.splice(i, 1);
                            break;
                        }
                    }
                }
                DBInfoService.refreshColumns($scope.tableOne.name).then(function(result) {
                    $scope.columnsInTableOne = result.data.columns;
                });
            }

            $scope.refreshWithTableTwo = function() {
                if(angular.isUndefined($scope.tableOne)) {
                    for (var i = 0; i < $scope.tablesForTableOne.length; i++) {
                        if ($scope.tablesForTableOne[i].alias == $scope.tableTwo.alias) {
                            $scope.tablesForTableOne.splice(i, 1);
                            break;
                        }
                    }
                }

                DBInfoService.refreshColumns($scope.tableTwo.name).then(function(result) {
                    $scope.columnsInTableTwo = result.data.columns;
                });
            }

            $scope.addJoinedColumns = function () {
                $scope.columnsJoined.push({
                    tableOne:$scope.tableOne.name,
                    tableOneAlias:$scope.tableOne.alias,
                    columnOne:$scope.columnOne.name,
                    tableTwo:$scope.tableTwo.name,
                    tableTwoAlias:$scope.tableTwo.alias,
                    columnTwo:$scope.columnTwo.name
                });
            }

            DBInfoService.refreshTables().then(function (result) {
                $scope.tables = result.data.tables;
                $scope.tablesUnselected = result.data.tables;
            });
        }])
        //检查是否有重复的别名
        .directive('aliasExists', function () {
            return {
                require: 'ngModel',
                link: function (scope, elm, attrs, ctrl) {
                    ctrl.$validators.aliasExists = function (mv, vv) {
                        var aliasValue = mv || vv;
                        if (ctrl.$isEmpty(aliasValue)) {
                            return true;
                        }
                        for(var i = 0; i < scope.tablesSelected.length; i ++) {
                            if(aliasValue == scope.tablesSelected[i].alias) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
        })
        .directive('duplicate', function(){
            return {
                require:'ngModel',
                link: function(scope, elm, attrs, ctrl) {
                    var duplicateKey = attrs['duplicate'];
                    if(angular.isUndefined(scope.errors)) {
                        scope.errors = {duplicate : {}};
                        scope.errors.duplicate[duplicateKey] = true;
                    } else if(angular.isUndefined(scope.errors.duplicate)){
                        scope.errors.duplicate = {};
                        scope.errors.duplicate[duplicateKey] = true;
                    }

                    if(angular.isUndefined(scope.duplicateCtrls)) {
                        scope.duplicateCtrls = {};
                    }
                    if(angular.isUndefined(scope.duplicateCtrls[attrs['duplicate']])) {
                        scope.duplicateCtrls[duplicateKey] = [];
                    }
                    scope.duplicateCtrls[duplicateKey].push(ctrl);

                    ctrl.$validators.duplicate = function (mv, vv) {
                        for(var i = 0; i < scope.duplicateCtrls[duplicateKey].length; i ++) {
                            var outerValue = scope.duplicateCtrls[duplicateKey][i].$viewValue ||
                                scope.duplicateCtrls[duplicateKey][i].$modelValue;

                            if(angular.isUndefined(outerValue)) {
                                continue;
                            }

                            for(var j = i + 1; j < scope.duplicateCtrls[duplicateKey].length; j++) {
                                var innerValue = scope.duplicateCtrls[duplicateKey][j].$viewValue ||
                                    scope.duplicateCtrls[duplicateKey][j].$modelValue;

                                if(angular.isUndefined(innerValue)) {
                                    continue;
                                }

                                if(angular.equals(outerValue, innerValue)) {
                                    scope.errors.duplicate[duplicateKey] = false
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        });

    /**
     * 条件控制器
     */
    querySetting.controller('ConditionController', ['$http', '$scope', 'DBInfoService', function ($http, $scope, DBInfoService) {
        $scope.operations = [{label:'>', name:'greaterThan'},{label:'<', name:'lessThan'},{label:'=', name:'equalsTo'},
            {label:'>=', name:'greaterOrEqualsTo'},{label:'<=', name:'lessOrEqualsTo'}];
        $scope.dataTypes = [{name:'String'},{name:'Decimal'}];

        $scope.conditions = [];

        $http.get('create/tablesSelected.json').then(function (result) {
            $scope.tables = result.data.pendingQuery.tables;
        });

        /**
         * 根据选择的表名，刷新Field下拉菜单的值，
         */
        $scope.refreshColumns = function() {
            DBInfoService.refreshColumns($scope.table.name).then(function(result) {
                $scope.columns = result.data.columns;
            });
        }
        /**
         * 增加一个查询条件
         */
        $scope.add = function () {
           if($scope.querySettingForm.$valid) {
               $scope.conditions.push({name:$scope.name, table:$scope.table.name, column:$scope.column.name,
                   dataType:$scope.dataType.name,operation:$scope.operation.label,value:$scope.value});
           }
        }

        $scope.submit = function () {
            $http.post('create/store-condition-and-forward.json', angular.toJson($scope.conditions)).//将数组转化为json字符串
                success(function (data, status, headers, config) {
                    $location.path('/condition');
                }).
                error(function (data, status, headers, config) {
                    alert('error')
                })
        }
    }])
})(window.angular);