<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>系统管理员页面</title>
</head>
<body>
	密码重置:
	<br />
	<form method="post" action="resetpassword">
		学号：<input type="text" name="digits"> <input type="submit"
			value="重置密码">
	</form>
	<br />
	<% 	
  			String result1=(String)request.getAttribute("error");
  			String result2=(String)request.getAttribute("success");
  			if(result1==null&&result2==null)
  				out.println("");
  			else if(result1==null)
  				out.println(result2);
  			else if(result2==null)
  				out.println(result1);
  		%>
</body>
</html>