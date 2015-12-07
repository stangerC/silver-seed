<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div ng-controller="TableController" class="main">
    <div class="main-header">
        <h2>Query Setting</h2>
    </div>
    <ul class="nav nav-tabs nav-query" role="tablist">
        <li role="presentation"><a href="#basic">Basic Setting</a></li>
        <li role="presentation" class="active"><a href="#">Table & View</a></li>
        <li role="presentation"><a href="#">Condition</a></li>
        <li role="presentation"><a href="#">Field</a></li>
    </ul>

    <form class="form-inline" role="form" method="POST" name="tableForm" id="tableForm"
          action="${ctx}/query/store-basic-and-forward" novalidate>

        <div class="panel panel-default" style="margin-top: 10px;">
            <div class="panel-heading">Select Table</div>
            <div class="panel-body">
                <div class="box-simple">
                    <div class="form-group">
                        <label for="table" class="control-label">Table</label>
                        <select ng-model="table" class="form-control" id="table" name="table" required
                                ng-options="t for t in tables">
                            <option value="">--choose--</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="alias" class="control-label">Alias</label>
                        <input type="text" id="alias" name="alias" class="form-control" ng-model="alias"
                               ng-maxlength="10" columnTwo-exists/>
                    </div>
                    <button type="button" class="btn btn-default" ng-click="add()">Add</button>
                </div>
                <div ng-messages="tableForm.table.$error" multiple>
                    <div ng-message="required">Table不能为空</div>
                </div>
                <div ng-messages="tableForm.alias.$error" multiple>
                    <div ng-message="maxlength">别名不能大于10个字符</div>
                </div>
                <div ng-messages="tableForm.alias.$error" multiple>
                    <div ng-message="char">别名必须为字母和数字</div>
                </div>
                <div ng-messages="tableForm.alias.$error" multiple>
                    <div ng-message="aliasExists">别名已经存在</div>
                </div>
            </div>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Table</th>
                    <th>Alias</th>
                    <th>Operation</th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="t in tablesSelected">
                    <td>{{t.name}}</td>
                    <td>{{t.alias}}</td>
                    <td>
                        <button type="button" class="btn btn-primary btn-block" ng-click="delete(t.name)">Delete
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">Joined Fields</div>
            <div class="panel-body">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>Table One</th>
                        <th>Table Two</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <div class="form-group">
                                <label for="tableOne" class="control-label">Alias</label>
                                <select ng-model="tableOne" class="form-control" id="tableOne" name="tableOne"
                                        ng-options="t.alias for t in tablesForTableOne" ng-change="refreshWithTableOne()" required
                                        duplicate>
                                    <option value="">--choose--</option>
                                </select>
                                <div ng-messages="tableForm.tableOneName.$error">
                                    <div ng-message="required">别名不能为空</div>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <label for="tableTwo" class="control-label">Alias</label>
                                <select ng-model="tableTwo" class="form-control" id="tableTwo" name="tableTwo"
                                        ng-options="t.alias for t in tablesForTableTwo" ng-change="refreshWithTableTwo()" required duplicate>
                                    <option value="">--choose--</option>
                                </select>
                                <div ng-messages="tableForm.tableTwo.$error">
                                    <div ng-message="required">别名不能为空</div>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="form-group">
                                <label for="columnOneSelect" class="control-label">Column</label>
                                <select ng-model="columnOne" class="form-control" id="columnOneSelect" name="columnOneSelect"
                                        ng-options="c.name for c in columnsInTableOne" required>
                                    <option value="">--choose--</option>
                                </select>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <label for="columnTwoSelect" class="control-label">Column</label>
                                <select ng-model="columnTwo" class="form-control" id="columnTwoSelect" name="columnTwoSelect"
                                        ng-options="c.name for c in columnsInTableTwo" required>
                                    <option value="">--choose--</option>
                                </select>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="btn-group" role="group">
                    <button type="button" class="btn btn-default" ng-click="addJoinedColumns()">Add</button>
                </div>
            </div>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Table</th>
                    <th>Alias</th>
                    <th>Column</th>
                    <th>Operation</th>
                    <th>Table</th>
                    <th>Alias</th>
                    <th>Column</th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="t in columnsJoined">
                    <td>{{t.tableOneName}}</td>
                    <td>{{t.tableOneAlias}}</td>
                    <td>{{t.columnOne.name}}</td>
                    <td>
                        =
                    </td>
                    <td>{{t.tableTwoName}}</td>
                    <td>{{t.tableTwoAlias}}</td>
                    <td>{{t.columnTwo.name}}</td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="row">
            <div class="col-sm-2">
                <button type="button" class="btn btn-primary btn-block" ng-click="submit()">Next</button>
            </div>
        </div>
    </form>
</div>
