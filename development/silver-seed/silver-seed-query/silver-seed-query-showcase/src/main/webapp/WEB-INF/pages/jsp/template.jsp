<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html ng-app="querySetting">
<head>
    <title>Query Setting</title>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
    <link rel="stylesheet" href="http://10.143.172.73/bootstrap/current/css/bootstrap.css">
    <link rel="stylesheet" href="${ctx}/css/style.css">

    <script type="text/javascript" src="http://10.143.172.73/angular/current/angular.js"></script>
    <script type="text/javascript" src="http://10.143.172.73/angular/current/angular-route.js"></script>
    <script type="text/javascript" src="http://10.143.172.73/angular/current/angular-resource.js"></script>
    <script type="text/javascript" src="http://10.143.172.73/angular/current/angular-messages.js"></script>

    <script type="text/javascript" src="${ctx}/js/app.js"></script>
</head>
<body>
<div class="container-fluid" ng-view>
</div>
</body>
</html>
