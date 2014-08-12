<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/common/jspf/header-setting.jspf" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
    <%
    Cookie cookie = new Cookie("appLocale", "zh_CN");
    response.addCookie(cookie);
    %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Index</title>
        <script type="text/javascript">        
        window.location = "${ctx}/configurations";
        </script>        
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
