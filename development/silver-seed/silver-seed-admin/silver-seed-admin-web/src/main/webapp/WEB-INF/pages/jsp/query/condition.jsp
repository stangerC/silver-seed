<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div ng-controller="ConditionController" class="main">
    <div class="main-header">
        <h2>Query Setting</h2>
    </div>
    <ul class="nav nav-tabs nav-query" role="tablist">
        <li role="presentation"><a href="#basic">Basic Setting</a></li>
        <li role="presentation"><a href="#">Table</a></li>
        <li role="presentation" class="active"><a href="#">Condition</a></li>
        <li role="presentation"><a href="#">Column</a></li>
    </ul>

    <form class="form-horizontal" role="form" method="POST" name="querySettingForm" id="querySettingForm"
          action="${ctx}/query/store-condition-and-forward" novalidate>
        <div class="panel panel-default" style="margin-top: 10px;">
            <div class="panel-heading">Select Table</div>
            <div class="panel-body">
                <div class="form-group">
                    <label for="name" class="control-label col-sm-2">Name</label>
                    <div class="col-sm-8">
                        <input type="text" id="name" name="name" class="form-control" ng-model="name" required
                               ng-maxlength="10"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-8 col-sm-offset-2" ng-messages="querySettingForm.name.$error" multiple>
                        <div ng-message="required">Name must be not null</div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="table" class="control-label col-sm-2">Table</label>
                    <div class="col-sm-8">
                        <select ng-model="table" class="form-control" id="table" name="table" required
                                ng-options="t.name for t in tables" ng-change="refreshColumns()">
                            <option value="">--choose--</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-8 col-sm-offset-2" ng-messages="querySettingForm.table.$error" multiple>
                        <div ng-message="required">Table must be not null</div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="column" class="control-label col-sm-2">Column</label>
                    <div class="col-sm-8">
                        <select ng-model="column" id="column" name="column" class="form-control" required
                                ng-options="c.name for c in columns">
                            <option value="">--choose--</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-8 col-sm-offset-2" ng-messages="querySettingForm.column.$error" multiple>
                        <div ng-message="required">Column must be not null</div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="dataType" class="control-label col-sm-2">Data Type</label>
                    <div class="col-sm-8">
                        <select ng-model="dataType" id="dataType" name="dataType" class="form-control" required
                                ng-options="d.name for d in dataTypes">
                            <option value="">--choose--</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-8 col-sm-offset-2" ng-messages="querySettingForm.dataType.$error" multiple>
                        <div ng-message="required">Data type must be not null</div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="operation" class="control-label col-sm-2">Operation</label>
                    <div class="col-sm-8">
                        <select ng-model="operation" id="operation" name="operation" class="form-control" required
                                ng-options="o.label for o in operations">
                            <option value="">--choose--</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-8 col-sm-offset-2" ng-messages="querySettingForm.operation.$error" multiple>
                        <div ng-message="required">Operation must be not null</div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="value" class="control-label col-sm-2">Value</label>
                    <div class="col-sm-8">
                        <input type="text" id="value" name="value" class="form-control" ng-model="value" required
                               ng-maxlength="10"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-8 col-sm-offset-2" ng-messages="querySettingForm.value.$error" multiple>
                        <div ng-message="required">Value must be not null</div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-2">
                        <button type="button" class="btn btn-primary btn-block" ng-click="add()">Add</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading">Existed Conditions</div>
            <div class="panel-body"></div>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Condition Name</th>
                    <th>Table</th>
                    <th>Column</th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="t in conditions">
                    <td>{{t.name}}</td>
                    <td>{{t.table}}</td>
                    <td>{{t.column}}</td>
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
