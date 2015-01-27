<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <div ng-controller="BasicController">
    <div class="page-header">
        <h2>Query Setting</h2>
    </div>
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#basic">Basic Setting</a></li>
        <li role="presentation"><a href="#table-and-view">Table & View</a></li>
        <li role="presentation"><a href="#parameter">Parameter</a></li>
        <li role="presentation"><a href="#field">Field</a></li>
    </ul>
    <form class="form-horizontal" style="width: 600px;margin:0 auto;padding:15px;" role="form" method="POST"
          action="${ctx}/setting/create#/store-basic-and-forward" id="basicForm" name="basicForm" novalidate>
        <div class="form-group">
            <label for="name" class="col-sm-2 control-label">Name</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="name" id="name" placeholder="name" ng-model="name"
                       required ng-maxlength="50" ng-minlength="3" char  exists>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-10 col-sm-offset-2" ng-messages="basicForm.name.$error" multiple>
                <div ng-message="required">名称不能为空</div>
                <div ng-message="minlength">名称不能小于3个字符</div>
                <div ng-message="maxlength">名称不能大于50个字符</div>
                <div ng-message="char">名称必须为字母和数字</div>
                <div ng-message="exists">名称已经存在</div>
            </div>
        </div>
        <div class="form-group">
            <label for="label" class="col-sm-2 control-label">Label</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="label" id="label" placeholder="label" ng-model="label"
                       required ng-maxlength="50">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-10 col-sm-offset-2" ng-messages="basicForm.label.$error" multiple>
                <div ng-message="required">标签不能为空</div>
                <div ng-message="maxlength">标签不能大于50个字符</div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="button" class="btn btn-primary btn-block" ng-click="submit()">Next</button>
            </div>
        </div>
    </form>
    </div>