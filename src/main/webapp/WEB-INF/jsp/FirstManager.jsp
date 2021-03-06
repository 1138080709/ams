<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="com.ams.department.dto.QuerySaveDepartmentInfoDTO"%>
<%@ page import="com.ams.user.entity.User"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.ams.department.dto.QuerySaveJobInfoDTO"%>
<%@ page import="com.ams.department.dto.QuerySaveMemberInfoDTO"%>
<html>
<head>
<title>一级管理员页面</title>

</head>
<body>
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
	<br />
	<br /> 修改密码功能：
	<br />
	<form method="post" action="/ams/user/setpassword">
		旧密码：<input type="password" name="oldPassword"> <br /> 新密码：<input
			type="password" name="newPassword"> <br /> <input
			type="submit" value="修改密码">
	</form>
	<br />

	<h2>部门模块</h2>
	部门列表：
	<!--<a href="http://localhost:8080/ams/department/getDepartmentList?queryInfo=实践部&offset=0&limit=3">打开</a>-->
	<a
		href="http://localhost:8080/ams/department/getDepartmentList?offset=0&limit=3">打开</a>
	<table>
		<tr>
			<th>Id</th>
			<th>部门名</th>
			<th>现任部长</th>
		</tr>
	</table>
	<br />
	<%
  	Map<String,Object> resultMap=null;
  	List<QuerySaveDepartmentInfoDTO> departments=null;
  	int total=0;
  	resultMap=(Map<String,Object>)request.getAttribute("departmentUserList");
  	if(resultMap==null)
  		out.println("没有取值!");
  	else{
  		departments=(List<QuerySaveDepartmentInfoDTO>)resultMap.get("departments");
  		total=(Integer)resultMap.get("total");
  		out.println("总共有"+total+"个部门<br/>");
  		for(QuerySaveDepartmentInfoDTO department:departments){
  			if(department.getMinister()!=null)
  				out.println(department.getId()+" "+department.getDepartmentName()+" 部长:"+department.getMinister().getName()+"<br/>");
  			else
  				out.println(department.getId()+" "+department.getDepartmentName()+" 部长:空<br/>");
  		}
  	}
  	%>
	编辑部门信息功能
	<br />
	<form action="/ams/department/updateDepartmentInfo" method="post">
		输入修改的部门的Id(模拟前端选中，并向后端传值)：<input type="text" name="departmentId"><br />
		部门新名称：<input type="text" name="newDepartmentName"> 新部长学号：<input
			type="text" name="newMinisterDigits"> 部门描述:<input type="text"
			name="newDescribe"> <input type="submit" value="提交">
	</form>

	添加部门功能
	<br />
	<form action="/ams/department/addDepartment" method="post">
		部门名称：<input type="text" name="newDepartmentName"> 部长学号：<input
			type="text" name="newMinisterDigits"> 部门描述:<input type="text"
			name="newDescribe"> <input type="submit" value="添加">
	</form>
	删除部门功能
	<br />
	<form action="/ams/department/delDepartment" method="post">
		输入删除的部门的Id(模拟前端选中，并向后端传值)：<input type="text" name="selectDepartmentId">
		<input type="submit" value="删除"><br />
	</form>

	<h2>职位功能模块</h2>
	职位列表：
	<!--<a href="http://localhost:8080/ams/department/getJobList?queryInfo=实践部部长&offset=0&limit=3">打开</a>-->
	<a
		href="http://localhost:8080/ams/department/getJobList?offset=0&limit=3">打开</a>
	<table>
		<tr>
			<th>Id</th>
			<th>职位名</th>
			<th>所属部门</th>
		</tr>
	</table>
	<br />
	<%
  	Map<String,Object> resultMap_job=null;
  	List<QuerySaveJobInfoDTO> jobs=null;
  	int total_job=0;
  	resultMap_job=(Map<String,Object>)request.getAttribute("jobsList");
  	if(resultMap_job==null)
  		out.println("没有取值!");
  	else{
  		jobs=(List<QuerySaveJobInfoDTO>)resultMap_job.get("jobs");
  		total_job=(Integer)resultMap_job.get("total");
  		out.println("总共有"+total_job+"个职位<br/>");
  		for(QuerySaveJobInfoDTO job:jobs){
  			if(job.getBelongId()!=null)
  				out.println(job.getId()+" "+job.getJobName()+" 所属部门:"+job.getBelongId().getDepartmentName()+"<br/>");
  			else
  				out.println(job.getId()+" "+job.getJobName()+" 所属部门:空<br/>");
  		}
  	}
  	%>
	<br /> 添加职位功能
	<br />
	<form action="/ams/department/addJob" method="post">
		职位名称：<input type="text" name="jobName"><br /> 职位权限：<input
			type="text" name="roleFlag"><br /> 所属部门Id(选项卡,返回Id):<input
			type="text" name="departmentId"><br /> <input type="submit"
			value="添加">
	</form>
	<br /> 修改职业信息功能
	<br />
	<form action="/ams/department/updateJob" method="post">
		修改的职位ID(模拟列表被选中，向后端传Id)：<input type="text" name="jobId"><br />
		职位新名称：<input type="text" name="newJobName"><br /> 职位新权限：<input
			type="text" name="newRoleFlag"><br /> 新所属部门Id(选项卡,返回Id):<input
			type="text" name="newDepartmentId"><br /> <input
			type="submit" value="修改">
	</form>
	<br /> 删除职业功能
	<br />
	<form action="/ams/department/delJob" method="post">
		输入删除的部门的Id(模拟前端选中，并向后端传值)：<input type="text" name="selectJobId">
		<input type="submit" value="删除"><br />
	</form>
	<h2>成员功能模块</h2>
	成员列表：
	<!--<a href="http://localhost:8080/ams/department/getDepartmentMemberList?currentDepartment=1&queryInfo=吴伟铨&offset=0&limit=3">打开</a>-->
	<a href="http://localhost:8080/ams/department/getDepartmentMemberList?currentDepartment=1&offset=0&limit=3">打开</a>
	<table>
		<tr>
			<th>学号</th>
			<th>姓名</th>
			<th>所属部门</th>
			<th>职位</th>
		</tr>
	</table>
	<br />
	<%
  	Map<String,Object> resultMap_departmentMember=null;
  	List<QuerySaveMemberInfoDTO> departmentMembers=null;
  	int total_departmentMember=0;
  	resultMap_departmentMember=(Map<String,Object>)request.getAttribute("departmentMemberList");
  	if(resultMap_departmentMember==null)
  		out.println("没有取值!");
  	else{
  		departmentMembers=(List<QuerySaveMemberInfoDTO>)resultMap_departmentMember.get("departmentMembers");
  		total_departmentMember=(Integer)resultMap_departmentMember.get("total");
  		out.println("总共有"+total_departmentMember+"个成员<br/>");
  		for(QuerySaveMemberInfoDTO departmentMember:departmentMembers){
  			if(departmentMember.getBelongId()!=null&&departmentMember.getJobId()!=null)
  				out.println(departmentMember.getDigits()+" "+departmentMember.getName()+" 所属部门:"+departmentMember.getBelongId().getDepartmentName()
  						+" 职位:"+departmentMember.getJobId().getJobName()+"<br/>");
  			else if(departmentMember.getBelongId()!=null)
  				out.println(departmentMember.getDigits()+" "+departmentMember.getName()+" 所属部门:"+departmentMember.getBelongId().getDepartmentName()
  						+" 职位:无<br/>");
  			else if(departmentMember.getJobId()!=null)
  				out.println(departmentMember.getDigits()+" "+departmentMember.getName()+" 所属部门:空 职位:"+departmentMember.getJobId().getJobName()+"<br/>");
  			else
  				out.println(departmentMember.getDigits()+" "+departmentMember.getName()+" 所属部门:空 职位:空<br/>");
  		}
  	}
  	%>
	<br />
	部门成员基本信息修改功能<br/>
	<form action="/ams/department/updateMemberInfo" method="post">
		修改的成员Id(模拟列表被选中，向后端传Id)：<input type="text" name="memberId"><br />
		成员新姓名：<input type="text" name="memberName"><br /> 
		职位新学号：<input type="text" name="memberDigits"><br /> 
		职位新学院：<input type="text" name="memberDepartment"><br /> 
		职位新专业：<input type="text" name="memberMajor"><br /> 
		职位新班级：<input type="text" name="memberClass"><br /> 
		职位新年级：<input type="text" name="memberGrade"><br /> 
		职位新号码：<input type="text" name="memberPhone"><br /> 
		职位新邮件：<input type="text" name="memberEmail"><br /> 
		<input	type="submit" value="修改">
	</form>
	<br/>
	职位变更功能<br/>
	<form action="/ams/department/updatePostInfo" method="post">
		修改的成员Id(模拟列表被选中，向后端传Id)：<input type="text" name="memberId"><br />
		新职位Id(模拟选项卡选中，向后端传Id)：<input type="text" name="memberJobId"><br />
		<input	type="submit" value="变更">
	</form>
	<br/>
	用户权限更改<br/>
	<form action="/ams/department/updateRoleFlag" method="post">
		修改的成员Id(模拟列表被选中，向后端传Id)：<input type="text" name="memberId"><br />
		权限等级：<input type="text" name="roleFlag"><br />
		<input	type="submit" value="更改">
	</form>
	<br/>
</body>
</html>