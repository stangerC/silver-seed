<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div ng-controller="TableAndViewController">
    <div class="page-header">
        <h2>Query Setting</h2>
    </div>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation"><a href="#basic">Basic Setting</a></li>
        <li role="presentation" class="active"><a href="#">Table & View</a></li>
        <li role="presentation"><a href="#">Parameter</a></li>
        <li role="presentation"><a href="#">Field</a></li>
    </ul>
    <form class="form-horizontal" class="col-lg-10" style="margin:0 auto;padding:15px;" role="form" method="POST"
          action="${ctx}/query/store-basic-and-forward">
        <div class="row">
            <div class="col-lg-5">
                <p class="title">可选</p>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>表名</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="t in tablesUnselected" id="{{t}}">
                        <td>{{t}}</td>
                        <td>
                            <button type="button" class="btn btn-primary btn-block" ng-click="add(t)">add</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="col-lg-5">
                <p class="title">已选</p>
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
                            <button type="button" class="btn btn-primary btn-block" ng-click="delete(t.name)">Delete</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="row">

        </div>
        <div class="row">
            <div class="form-group">
                <div class="col-sm-4">
                    <button type="button" class="btn btn-primary btn-block" ng-click="submit()">Next</button>
                </div>
            </div>
        </div>
    </form>
</div>
