var test = document.getElementById("test");
var test1 = document.getElementById("test1");
var test2 = document.getElementById("test2");
var test3 = document.getElementById("test3");
var test4 = document.getElementById("test4");
var test5 = document.getElementById("test5");
var test6 = document.getElementById("test6");
var test7 = document.getElementById("test7");
var test8 = document.getElementById("test8");
var test9 = document.getElementById("test9");
var test10 = document.getElementById("test10");
var test11 = document.getElementById("test11");
var test12 = document.getElementById("test12");
var test13 = document.getElementById("test13");
var test14 = document.getElementById("test14");
var test15 = document.getElementById("test15");
var test16 = document.getElementById("test16");
var test17 = document.getElementById("test17");
var login=document.getElementById("login");
test.onclick = function(){
	$.ajax({
		url:'department/addDepartment',
		type:'GET',
		cache:false,
		dataType:'json',
		data:{
			'departmentName':'快乐团学',
			'describe':'办公的部门',
			'ministerDigits':'201710098111',
		},
		success:function(data){
			alert(data.message);
		},
		error:function(data){
			alert(data.message);
		}
	})
}
test1.onclick = function(){
	$.ajax({
		url:'department/updateDepartmentInfo',
		type:'GET',
		cache:false,
		dataType:'json',
		data:{
			'id':'4ce67327693945d298f2d9b11272fe05',
			'departmentName':'快乐上学去',
			'description':'快乐的部门',
			'ministerDigits':'201710098111'
		},
		success:function(data){
			alert(data.message);
		},
		error:function(data){
			alert(data.message);
		}
	})
}
test2.onclick = function(){
	$.ajax({
		url:'department/addJob',
		type:'GET',
		cache:false,
		dataType:'json',
		data:{
			'jobName':'高级顾问',
			'roleFlag':3,
			'belongId':'1'
		},
		success:function(data){
			alert(data.message);
		},
		error:function(data){
			alert(data.message);
		}
	})
}
test3.onclick = function(){
	$.ajax({
		url:'department/updateJob',
		type:'GET',
		cache:false,
		dataType:'json',
		data:{
			'id':'7ee79d0f0ea4430e8b172a42a227872e',
			'jobName':'实践部高级顾问',
			'roleFlag':2,
			'belongId':'1'
		},
		success:function(data){
			alert(data.message);
		},
		error:function(data){
			alert(data.message);
		}
	})
}
test4.onclick = function(){
	$.ajax({
		url:'department/updateMemberInfo',
		type:'GET',
		cache:false,
		dataType:'json',
		data:{
			'id':'5',
			'name':'2017003',
			'digits':'201710098003',
			'department':'计算机工程学院',
			'major':'计算科学与技术',
			'belongClass':'17计算机科学与技术4班',
			'grade':'2017',
			'phone':'000000000',
			'email':'000000000'
		},
		success:function(data){
			alert(data.message);
		},
		error:function(data){
			alert(data.message);
		}
	})
}
test5.onclick = function(){
	$.ajax({
		url:'user/saveMemberApplicationInfo',
		type:'GET',
		cache:false,
		dataType:'json',
		data:{
			'name':'xiaoxiaowu',
			'digits':'201910098050',
			'department':'计算机工程学院',
			'major':'计算科学与技术',
			'belongClass':'19计算机科学与技术4班',
			'grade':'2019',
			'phone':'000000000',
			'email':'000000000',
			'departmentName':'实践部1',
			'jobName':'实践部干事1'
		},
		success:function(data){
			alert(data.message);
		},
		error:function(data){
			alert(data.message);
		}
	})
}
test6.onclick = function(){
	$.ajax({
		url:'user/getMemberApplicationInfo',
		type:'GET',
		cache:false,
		dataType:'json',
		data:{
			'queryInfo':'',
			'offset':0,
			'limit':1
		},
		success:function(data){
			alert(data.message+data.resultMap.total);
		},
		error:function(data){
			alert(data.message);
		}
	})
}
test7.onclick = function(){
	$.ajax({
		url:'finance/queryCurrentBalance',
		type:'GET',
		cache:false,
		dataType:'json',
		data:{
		},
		success:function(data){
			alert(data.message);
		},
		error:function(data){
			alert(data.message);
		}
	})
}
test8.onclick = function(){
	$.ajax({
		url:'finance/applicateCash',
		type:'GET',
		cache:false,
		dataType:'json',
		data:{
			'infoType':1,//0-支出 1-收入
			'money':100,
			'description':'铨哥哥发福利拉',
		},
		success:function(data){
			alert(data.message);
		},
		error:function(data){
			alert(data.message);
		}
	})
}
test9.onclick = function(){
	$.ajax({
		url:'finance/getAuditList',
		type:'GET',
		cache:false,
		dataType:'json',
		data:{
			'queryInfo':'铨',
			'offset':0,
			'limit':3,
		},
		success:function(data){
			alert(data.message);
		},
		error:function(data){
			alert(data.message);
		}
	})
}
test10.onclick = function(){
	$.ajax({
		url:'finance/getExecuteList',
		type:'GET',
		cache:false,
		dataType:'json',
		data:{
			'queryInfo':'',
			'offset':0,
			'limit':1
		},
		success:function(data){
			alert(data.message);
		},
		error:function(data){
			alert(data.message);
		}
	})
}
test11.onclick = function(){
	$.ajax({
		url:'finance/getCashFlowInfoList',
		type:'GET',
		cache:false,
		dataType:'json',
		data:{
			'queryInfo':'',
			'offset':0,
			'limit':1
		},
		success:function(data){
			alert(data.message);
		},
		error:function(data){
			alert(data.message);
		}
	})
}
test12.onclick = function(){
	$.ajax({
		url:'finance/personalAuditHistory',
		type:'GET',
		cache:false,
		dataType:'json',
		data:{
			'queryInfo':'',
			'offset':0,
			'limit':1
		},
		success:function(data){
			alert(data.message);
		},
		error:function(data){
			alert(data.message);
		}
	})
}
test13.onclick = function(){
	$.ajax({
		url:'finance/registerMaterial',
		type:'GET',
		cache:false,
		dataType:'json',
		data:{
			'materialName':'木人桩',
			'totalNumber':1,
			'description':'咏春木人桩'
		},
		success:function(data){
			alert(data.message);
		},
		error:function(data){
			alert(data.message);
		}
	})
}
test14.onclick = function(){
	$.ajax({
		url:'finance/updateMaterial',
		type:'GET',
		cache:false,
		dataType:'json',
		data:{
			'id':'2143cf4a2b4c4c1c874172bb1d9e7afb',
			'materialName':'木人桩',
			'totalNumber':2,
			'remainNumber':1,
			'description':'咏春木人桩'
		},
		success:function(data){
			alert(data.message);
		},
		error:function(data){
			alert(data.message);
		}
	})
}
test15.onclick = function(){
	$.ajax({
		url:'finance/showMaterial',
		type:'GET',
		cache:false,
		dataType:'json',
		data:{
			'queryInfo':'',
			'offset':0,
			'limit':1
		},
		success:function(data){
			alert(data.message);
		},
		error:function(data){
			alert(data.message);
		}
	})
}
test16.onclick = function(){
	$.ajax({
		url:'finance/checkLoanStatus',
		type:'GET',
		cache:false,
		dataType:'json',
		data:{
			'queryInfo':'',
			'offset':0,
			'limit':1
		},
		success:function(data){
			alert(data.message);
		},
		error:function(data){
			alert(data.message);
		}
	})
}
test17.onclick = function(){
	$.ajax({
		url:'finance/lendAndReturnMaterial',
		type:'GET',
		cache:false,
		dataType:'json',
		data:{
			'MaterialId':'2143cf4a2b4c4c1c874172bb1d9e7afb',
			'infoType':0,
			'number':1,
			'purpose':'训练',
			'date':'2019-09-07 21:38:21',
			'organizationName':'咏春',
			'principalName':'陈飞朝',
			'principalPhone':'18476202009',
			'remarks':'吴伟铨好帅'
		},
		success:function(data){
			alert(data.message);
		},
		error:function(data){
			alert(data.message);
		}
	})
}
login.onclick = function(){
	$.ajax({
		url:'user/login',
		type:'GET',
		cache:false,
		dataType:'json',
		data:{
			'digits':'201710098111',
			'password':'000000'
		},
		success:function(data){
			alert(data.message);
		},
		error:function(data){
			alert(data.message);
		}
	})
}
