<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String url = request.getParameter("url");
	String new_url = "";
	if(url.indexOf("?")<0){
		new_url = url+"?pageNum=";
	} else{
		new_url = url+"&pageNum=";
	}
	request.setAttribute("new_url",new_url);
%>


<c:choose>
   		<c:when test="${pagenation.pageNum==1}">
   			首页
   		</c:when>
   		<c:otherwise>
		   	<a href="${new_url}${pagenation.first}">首页</a>
		   	<a href="${new_url}${pagenation.previous}">上一页</a>
   		</c:otherwise>
   	</c:choose>
   	<c:forEach var="i" begin="${pagenation.startNav}" end="${pagenation.endNav}">
   		<c:choose>
   			<c:when test="${pagenation.pageNum==i}">
				${i}   			
   			</c:when>
   			<c:otherwise>
		   		<a href="${new_url}${i}">${i}</a>
   			</c:otherwise>
   		</c:choose>
   	</c:forEach>
   	<c:choose>
   		<c:when test="${pagenation.pageNum==pagenation.last}">
   			末页
   		</c:when>
   		<c:otherwise>
		   	<a href="${new_url}${pagenation.next}">下一页</a>
		   	<a href="${new_url}${pagenation.last}">末页</a>
   		</c:otherwise>
   	</c:choose>