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
<title>五级管理员页面</title>
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
</body>
</html>