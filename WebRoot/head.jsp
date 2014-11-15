<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
	<c:when test="${empty sessionScope.user}">
		<a href="login.jsp">登陆</a>
		<a href="reg.jsp">注册</a>
	</c:when>
	<c:otherwise>
		欢迎你,${sessionScope.user.uname},
		<a href="user?method=exit">退出登陆</a>
	</c:otherwise>
</c:choose>
<br/><br/><br/><br/>
<hr color="red"/>