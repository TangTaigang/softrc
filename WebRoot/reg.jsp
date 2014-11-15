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
	<script src="js/util.js"></script>
	<script src="js/ajaxUtil.js"></script>
	
	<script src="dwr/engine.js"></script>
	<script src="dwr/util.js"></script>
	<script src="dwr/interface/userdao.js"></script>
	
	<script type="text/javascript">
		var name_only_ok = ${(empty param.uname)?"false":"true"};
		
		function checkForm(frm){
			var uname_ok = checkFormField(frm.uname,$("unameMsg"),/^[a-zA-Z0-9_]{1,20}$/,"用户名不能为空","用户名必须为字母、数字、下划线、并且长度在1~20之间");
			var pwd_ok = checkFormField(frm.pwd,$("pwdMsg"),/^[a-zA-Z0-9_]{1,20}$/,"密码不能为空","密码必须为字母、数字、下划线、并且长度在1~20之间");
			var email_ok = checkFormField(frm.email,$("emailMsg"),/^[a-zA-Z0-9_]+@[\w-]+(\.[a-zA-Z]+){1,2}$/,"邮箱不能为空","邮箱格式不真确");
			var phone_ok = checkFormField(frm.phone,$("phoneMsg"),/^1[345678]\d{9}$/,"手机号不能为空","请输入真实的手机号");
			var age_ok = checkFormField(frm.age,$("ageMsg"),/^\d{2}$/,"年龄不能为空","请输入真实的年龄");
			var inrand_ok = checkFormField(frm.inrand,$("inrandMsg"),/^\w{4}$/,"验证码不能为空","请输入四位验证码");
			
			var pwd2_ok = true;
			$("pwd2Msg").innerHTML = "";
			if(frm.pwd.value != $("pwd2").value){
				var pwd2_ok = false;
				$("pwd2Msg").innerHTML = "两次输入密码不一致！";
			}
			
			if(!name_only_ok){
				$("unameMsg").innerHTML = "很遗憾！用户名已注册！";
			}
			
			return uname_ok&&pwd_ok&&email_ok&&phone_ok&&age_ok&&pwd2_ok&&name_only_ok&&inrand_ok;
		}
		
		function nameOnly(uname){
			/*
			sendAjaxReq("get","user","method=nameOnly&uname="+uname.value,true,function(data){
				if(data=="true"){
					name_only_ok = true;
					$("unameMsg").innerHTML = "恭喜你！用户名可用！";					
				} else{
					$("unameMsg").innerHTML = "很遗憾！用户名已注册！";
					name_only_ok = false;
				}
			});
			*/
			userdao.countUnameNum(uname.value,function(num){
				if(num==0){
					name_only_ok = true;
					$("unameMsg").innerHTML = "恭喜你！用户名可用！";					
				} else{
					$("unameMsg").innerHTML = "很遗憾！用户名已注册！";
					name_only_ok = false;
				}
			});
		}
		
		function changeDegree(){
			var dr = document.regFrm.degree;
			var a = '${param.degree}';
			if(a!=''){
				dr.value=a;
			}
		}
		
	</script>
  </head>
  
  <body onload="changeDegree()">
    <c:import url="head.jsp"></c:import>
   	<form name=regFrm method=post action="user" onsubmit="return checkForm(this)"><br/>
   		用户名：<input type="text" name="uname" value="${param.uname}" onblur="nameOnly(this)" /><span id="unameMsg" class="error"></span><br/>
   		密码：<input type="password" name="pwd" /><span id="pwdMsg" class="error"></span><br/>
   		确认密码：<input type="password" id="pwd2"/><span id="pwd2Msg" class="error"></span><br/>
   		邮箱：<input type="text" name="email" value="${param.email}"/><span id="emailMsg" class="error"></span><br/>
   		手机号：<input type="text" name="phone" value="${param.phone}" /><span id="phoneMsg" class="error"></span><br/>
   		年龄：<input type="text" name="age" value="${param.age}" /><span id="ageMsg" class="error"></span><br/>
   		性别：<br/>
   		<input type="radio" name="gender" value=0 ${(empty param.gender)?"checked":(param.gender==0?"checked":"")} />男
   		<input type="radio" name=gender value=1 ${(empty param.gender)?"":(param.gender==1?"checked":"")}/>女<br/>
   		地址：<input type="text" name="address" value="${param.address}" /><br/>
   		学历：<br/>
   		<select name="degree">
   			<option value="0">高中</option>
   			<option value="1">专科</option>
   			<option value="2">本科</option>
   			<option value="3">硕士</option>
   			<option value="4" selected>博士</option>
   		</select>
   		<input type="hidden" name="method" value="reg" />
   		<input type="hidden" name="beanFullName" value="com.bjsxt.softrc.po.User" />
   		<%
   			session.setAttribute("token",new Date().getTime());
   		%>
   		<input type="hidden" name="token" value="${sessionScope.token}"/>
   		
   		<input type="text" name="inrand"/><span id="inrandMsg" class="error">${inrandError}</span>
   		<img src="image.jsp" id="rand" />
   		<a href="javascript:void(0);" onclick="document.getElementById('rand').src='image.jsp?s='+Math.random();">看不清，换一张</a>
   		<br/>
   		<input type="submit" value="注册">
   	</form>
   	
   	
    <c:import url="foot.jsp"></c:import>
  </body>
</html>
