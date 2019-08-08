<%@ page import="com.ssm.pojo.User" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/19
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<%--你们可以看看 isELIgnored作用--%>
<html>
<head>
    <title>Title</title>
</head>
<body>
${cc }<br>
${cc.pwd}
<br>
<%
    User user=(User)session.getAttribute("u");
    out.println(user.getPname());
%>
</body>
</html>
