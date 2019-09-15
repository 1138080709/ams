<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>测试</title>
</head>

<body>
	<form name="form1" method="post" action="/ams/user/login">
		用户名：<input type="text" name="digits"> 密码：<input
			type="password" name="password"> <input type="submit"
			value="登录">
	</form>
	<% 		String result1=(String)request.getAttribute("error");
  			String result2=(String)request.getAttribute("success");
  			if(result1==null)
  				out.println(result2);
  			else if(result2==null)
  				out.println(result1);
  			else
  				out.println("没有结果！");
  		%>
</body>
</html>
