
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/jspf/header-setting.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
        <jsp:include page="/WEB-INF/pages/configuration/jspf/common-css.jspf"/>
    </head>
    <body role="document">
        <div class="container">
        <h2>Error Page</h2>
        <h4>
            <c:out value="${exception}"/>
        </h4>        
        </div>
    </body>
</html>
