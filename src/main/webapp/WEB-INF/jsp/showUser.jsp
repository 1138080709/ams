<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>测试</title>
</head>

<body>
	${user.name}
	<form name="form1" method="post" action="login">
		用户名：<input type="text" name="digits"> 密码：<input
			type="password" name="password"> <input type="submit"
			value="登录">

	</form>
</body>
</html>
