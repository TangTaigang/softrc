<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'testdwr1.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="dwr/engine.js"></script>
	<script src="dwr/util.js"></script>
	<script src="dwr/interface/td.js"></script>
	<script>
		function aaa(){
			//td.test1();
			//td.test2(123,'tang',false); //DWR可以做类型的自动转换
			/*
			    td.test3(function(dd){
					alert(dd);
				});
			*/
			/*
				td.test4(true,function(data){
					if(data){
						alert("服务器传了个true");
					}
				});
			*/
			/*
				td.test5(function(data){
					alert(data);
				});
			*/
			/*
				td.test6(function(t){
					alert(t.name);
				});
			*/
			/*
			var t1 = {id:2,name:'杨萌'};
			td.test7(t1);
			*/
			var teachers = [{id:1,name:'aa'},{id:2,name:'bb'}];
			td.test8(teachers,function(ts){
				alert(ts[1].name);
			});
			
		}	
	</script>
  </head>
  
  <body>
  	<input type="button" value="测试dwr" onclick="aaa()" />  
  </body>
</html>
