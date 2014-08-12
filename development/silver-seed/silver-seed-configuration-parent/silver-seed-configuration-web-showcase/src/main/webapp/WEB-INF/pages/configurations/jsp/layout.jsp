<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@include file="/WEB-INF/pages/common/jspf/header-setting.jspf" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><tiles:insertAttribute name="title"/></title>
        <jsp:include page="/WEB-INF/pages/configurations/jspf/common-css.jspf"/>
        <jsp:include page="/WEB-INF/pages/configurations/jspf/common-js.jspf"/>
    </head>
    <body>
        <jsp:include page="/WEB-INF/pages/configurations/jspf/menu.jspf"/>
        <div class="container-fluid">
            <jsp:include page="/WEB-INF/pages/configurations/jspf/sub-menu.jspf"/>
            <tiles:insertAttribute name="main"/>
        </div>
    </body>
</html>
