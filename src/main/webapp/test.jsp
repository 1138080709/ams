<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>测试</title>
	
</head>
<body>
	<button id="login">登录</button>
	<button id="test">创建部门</button>
	<button id="test1">更改部门信息</button>
	<button id="test2">创建职位</button>
	<button id="test3">修改职位信息</button>
	<button id="test4">修改成员基本信息</button>
	<button id="test5">成员申请</button>
	<button id="test6">获取申请列表</button>
	<br/>
	<form action="user/updateApplicationFailStatus">
		<input type="checkbox" name="ids" value="4">345</input>
		<input type="checkbox" name="ids" value="015449b8ac0444abae8bdf4909742565">xiaoxiaowu</input>
		<br/>
		<button type="submit">审核不通过</button>
	</form>
	<br/>
	<form action="user/updateApplicationSuccessStatus">
		<input type="checkbox" name="ids" value="4">345</input>
		<input type="checkbox" name="ids" value="015449b8ac0444abae8bdf4909742565">xiaoxiaowu</input>
		<br/>
		<button type="submit">审核通过</button>
	</form>
	
	<br/>
	<h2>财务管理模块</h2>
	<button id="test7">查询余额</button>
	<button id="test8">资金申请</button>
	<button id="test9">获取审核列表</button>
	<br/>
	<form action="finance/reject">
		<input type="checkbox" name="ids" value="24634a198dd24b7fb1c2e119c6e59d21">1</input>
		<input type="checkbox" name="ids" value="6a22f11864ad474b98834229a7518951">2</input>
		<br/>
		<button type="submit">审核驳回</button>
	</form>
	<form action="finance/authorize">
		<input type="checkbox" name="ids" value="24634a198dd24b7fb1c2e119c6e59d21">1</input>
		<input type="checkbox" name="ids" value="6a22f11864ad474b98834229a7518951">2</input>
		<br/>
		<button type="submit">审核通过</button>
	</form>
	<form action="finance/cancel">
		<input type="checkbox" name="ids" value="24634a198dd24b7fb1c2e119c6e59d21">1</input>
		<input type="checkbox" name="ids" value="6a22f11864ad474b98834229a7518951">2</input>
		<br/>
		<button type="submit">取消审核</button>
	</form>
	<button id="test10">获取待执行列表</button>
	<form action="finance/execute">
		<input type="checkbox" name="id" value="24634a198dd24b7fb1c2e119c6e59d21">1</input>
		<input type="checkbox" name="id" value="6a22f11864ad474b98834229a7518951">2</input>
		<br/>
		<button type="submit">批准</button>
	</form>
	<form action="finance/cancelExecute">
		<input type="checkbox" name="id" value="24634a198dd24b7fb1c2e119c6e59d21">1</input>
		<input type="checkbox" name="id" value="6a22f11864ad474b98834229a7518951">2</input>
		<br/>
		<button type="submit">撤资</button>
	</form>
	<button id="test11">获取资金流动列表</button>
	<button id="test12">获取个人列表</button>
	<button id="test13">登记物资</button>
	<button id="test14">更改物资</button>
	<button id="test15">查看当前物资情况</button>
	<button id="test16">查看借还信息记录</button>
	<button id="test17">物资借还登记</button>
	<form action="finance/loseMaterial">
		<input type="checkbox" name="ids" value="2143cf4a2b4c4c1c874172bb1d9e7afb">1</input>
		<input type="checkbox" name="ids" value="245486b206c5497480096e2fd5854284">2</input>
		<br/>
		<button type="submit">物资丢失</button>
	</form>
	<script type="text/javascript" src="static/jquery.js"></script>
	<script type="text/javascript" src="static/test.js"></script>
</body>

</html>