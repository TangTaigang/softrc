<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>软件人才</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="软件招聘,人才,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/styles.css">
  </head>
  <body>
    <c:import url="head.jsp"></c:import>
   	<table border="1" style="border-collapse:collapse" bordercolor="gray">
   		<tr>
   			<td>id</td><td>姓名</td><td>密码</td><td>邮箱</td><td>电话</td>
   			<td>年龄</td><td>性别</td><td>地址</td><td>学历</td><td>注册时间</td>
   		</tr>
   		<c:forEach items="${requestScope.pagenation.list}" var="u">
   			<tr>
   			<td>${u.id}</td><td>${u.uname}</td><td>${u.pwd}</td><td>${u.email}</td><td>${u.phone}</td>
   			<td>${u.age}</td><td>${u.gender}</td><td>${u.address}</td><td>${u.degree}</td><td>${u.joinTime}</td>
   		</tr>
   		</c:forEach>
   	</table>
   	<br/><br/>
   	<c:import url="pagenation_util.jsp">
   		<c:param name="url" value="user?method=listAllUsers"></c:param>
   	</c:import>
   	
   	<br/><br/>
   	
    <c:import url="foot.jsp"></c:import>
  </body>
</html>
