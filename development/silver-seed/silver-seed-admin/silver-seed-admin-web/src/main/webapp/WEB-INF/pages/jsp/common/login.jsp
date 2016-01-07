<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html ng-app="adminApp">
<head>
    <title>Admin:Login</title>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
    <link rel="stylesheet" href="http://web.myresource.org/bootstrap/current/css/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/css/style.css">
    <link rel="stylesheet" href="${ctx}/css/admin.css">
    <script type="text/javascript" src="http://web.myresource.org/angular/current/angular.js"></script>
    <script type="text/javascript" src="http://web.myresource.org/angular/current/angular-route.js"></script>
    <script type="text/javascript" src="http://web.myresource.org/angular/current/angular-resource.js"></script>
    <script type="text/javascript" src="http://web.myresource.org/angular/current/angular-messages.js"></script>

    <script type="text/javascript" src="${ctx}/js/app.js"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div ng-controller="LoginController" class="center-block main">
            <div class="main-header center-block" style="width: 600px;">
                <h2>Login</h2>
            </div>
            <form class="form-horizontal" style="width: 600px;margin:0 auto;padding:15px;" role="form" method="POST"
                  action="${ctx}/login" id="basicForm" name="loginForm" novalidate>
                <div class="form-group">
                    <label for="userName" class="col-sm-2 control-label">User Name</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="userName" id="userName" placeholder="userName" ng-model="userName"
                               required ng-maxlength="50" ng-minlength="3" char >
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-10 col-sm-offset-2" ng-messages="loginForm.userName.$error" multiple>
                        <div ng-message="required">用户名不能为空</div>
                        <div ng-message="minlength">名称不能小于3个字符</div>
                        <div ng-message="maxlength">名称不能大于50个字符</div>
                        <div ng-message="userNotExisted">用户不存在</div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="label" class="col-sm-2 control-label">Password</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="label" id="label" placeholder="label" ng-model="label"
                               required ng-maxlength="50">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-10 col-sm-offset-2" ng-messages="basicForm.label.$error" multiple>
                        <div ng-message="required">密码不能为空</div>
                        <div ng-message="maxlength">密码不能大于50个字符</div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="button" class="btn btn-primary btn-block" ng-click="submit()">Login</button>
                        <button type="button" class="btn btn-primary btn-block" ng-click="clear()">Clear</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
