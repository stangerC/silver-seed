<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div ng-controller="TableAndViewController" class="main">
    <div class="main-header">
        <h2>Query Setting</h2>
    </div>
    <ul class="nav nav-tabs nav-query" role="tablist">
        <li role="presentation"><a href="#basic">Basic Setting</a></li>
        <li role="presentation" class="active"><a href="#">Table & View</a></li>
        <li role="presentation"><a href="#">Parameter</a></li>
        <li role="presentation"><a href="#">Field</a></li>
    </ul>

    <form class="form-inline" role="form" method="POST" name="tableForm" id="tableForm"
          action="${ctx}/query/store-basic-and-forward" novalidate>

        <div class="panel panel-default" style="margin-top: 10px;">
            <div class="panel-heading">Select Table</div>
            <div class="panel-body">
                <div class="form-group">
                    <label for="table" class="control-label">Table</label>
                    <select ng-model="table" class="form-control" id="table">
                        <option ng-repeat="t in tables">{{t}}</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="alias" class="control-label">Alias</label>
                    <input type="text" id="alias" name="alias" class="form-control" ng-model="alias"
                           ng-maxlength="10" char/>
                </div>
                <button type="button" class="btn btn-default" ng-click="add()">Add</button>
                <div class="form-group">
                    <div ng-messages="tableForm.alias.$error" multiple>
                        <div ng-message="maxlength">别名不能大于10个字符</div>
                        <div ng-message="char">别名必须为字母和数字</div>
                        <div ng-message="exists">别名已经存在</div>
                    </div>
                </div>
            </div>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>表名</th>
                    <th>别名</th>
                    <th>操作</th>
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
            <div class="panel-heading">关联字段</div>
            <div class="btn-group" role="group" style="padding:10px;">
                <button type="button" class="btn btn-default">新增</button>
                <button type="button" class="btn btn-default">删除</button>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-2">
                <button type="button" class="btn btn-primary btn-block" ng-click="submit()">Next</button>
            </div>
        </div>
    </form>
</div>
