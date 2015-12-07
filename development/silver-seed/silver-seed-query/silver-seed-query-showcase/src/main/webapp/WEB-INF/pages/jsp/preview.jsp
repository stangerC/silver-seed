<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div ng-controller="PreviewController" class="main">
    <div class="main-header">
        <h2>Query Setting</h2>
    </div>
    <ul class="nav nav-tabs nav-query" role="tablist">
        <li role="presentation"><a href="#basic">Basic Setting</a></li>
        <li role="presentation"><a href="#">Table</a></li>
        <li role="presentation"><a href="#">Condition</a></li>
        <li role="presentation"><a href="#">Column</a></li>
        <li role="presentation" class="active"><a href="#">Preview</a></li>
    </ul>

    <form class="form-horizontal" role="form" method="POST" name="querySettingForm" id="querySettingForm"
          action="${ctx}/query/store-column-then-priview-sql" novalidate>
        <div class="panel panel-default" style="margin-top: 10px;">
            <div class="panel-heading">Preview SQL</div>
            <div class="panel-body">
                ${previewSQL}
            </div>
        </div>
        <div class="row">
            <div class="col-sm-2">
                <button type="button" class="btn btn-primary btn-block" ng-click="submit()">Save</button>
            </div>
        </div>
    </form>
</div>
