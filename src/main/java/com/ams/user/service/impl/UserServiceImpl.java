package com.ams.user.service.impl;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ams.department.dao.DepartmentDao;
import com.ams.department.dao.JobDao;
import com.ams.department.dto.QuerySaveDepartmentInfoDTO;
import com.ams.department.dto.QuerySaveJobInfoDTO;
import com.ams.department.dto.QuerySaveMemberInfoDTO;
import com.ams.department.entity.Department;
import com.ams.department.entity.Job;
import com.ams.user.dao.MemberApplicateInfoDao;
import com.ams.user.dao.UserDao;
import com.ams.user.entity.MemberApplicateInfo;
import com.ams.user.entity.User;
import com.ams.user.service.IUserService;
import com.ams.utils.ExcelUtil;
import com.ams.utils.IdGen;
import com.ams.utils.entity.ExcelBean;
@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private UserDao userDao;
	@Resource
	private DepartmentDao departmentDao;
	@Resource
	private JobDao jobDao;
	@Resource
	private MemberApplicateInfoDao memberApplicateInfoDao; 
	
	public User getUserById(String userId) {
		return this.userDao.getUserById(userId);
	}

	public User getUserByDigits(String digits) {
		return this.userDao.getUserByDigits(digits);
	}

	public int updatePasswordByDigits(String digits,String encryptPassword) {
		return userDao.updatePasswordByDigits(digits,encryptPassword);
	}

	public int getUserCountByJobId(String selectJobId) {
		return this.userDao.getUserCountByJobId(selectJobId);
	}

	public List<QuerySaveMemberInfoDTO> getUserByDepartmentId(String departmentId, String queryInfo, int offset,
			int limit) {
		List<QuerySaveMemberInfoDTO> departmentMembers=new ArrayList<QuerySaveMemberInfoDTO>();
		List<User> userInfo=this.userDao.getUserByDepartmentId(departmentId,queryInfo,offset,limit);
		QuerySaveMemberInfoDTO userDTO=null;
		Department department=null;
		Job job=null;
		for(User user:userInfo) {
			userDTO=new QuerySaveMemberInfoDTO();
			userDTO.setId(user.getId());
			userDTO.setName(user.getName());
			userDTO.setDigits(user.getDigits());
			userDTO.setDepartment(user.getDepartment());
			userDTO.setMajor(user.getMajor());
			userDTO.setBelongClass(user.getBelongClass());
			userDTO.setGrade(user.getGrade());
			userDTO.setPhone(user.getPhone());
			userDTO.setEmail(user.getEmail());
			department=this.departmentDao.getDepartmentById(user.getBelongId());
			job=this.jobDao.getJobById(user.getJobId());
			userDTO.setBelongId(department);
			userDTO.setJobId(job);
			userDTO.setRoleFlag(user.getRoleFlag());
			userDTO.setFinanceFlag(user.getFinanceFlag());
			userDTO.setCreateTime(user.getCreateTime());
			departmentMembers.add(userDTO);
		}                 
		return departmentMembers;
	}

	public int getMemberCountByDepartmentId(String departmentId) {
		return this.userDao.getMemberCountByDepartmentId(departmentId);
	}


	public int updateMemberInfoById(User newInfoUser) {
		return this.userDao.updateById(newInfoUser);
	}

	public int updateJobById(String memberId, Job job) {
		return this.userDao.updateJobById(memberId,job.getId(),job.getBelongId(),job.getRoleFlag());
	}

	public void importExcelInfo(InputStream in, MultipartFile file) {
		List<List<Object>> list;
		System.out.println("文件名:"+file.getOriginalFilename());
		try {
			list=ExcelUtil.getBankListByExcel(in, file.getOriginalFilename());
			List<User> userList=new ArrayList<User>();
			//遍历list中的数据，把数据放到list中
			for(List<Object> ob:list) {
				User user=new User();
				user.setId(IdGen.uuid());
				user.setName(String.valueOf(ob.get(0)));
				user.setDigits(String.valueOf(ob.get(1)));
				user.setDepartment(String.valueOf(ob.get(2)));
				user.setMajor(String.valueOf(ob.get(3)));
				user.setBelongClass(String.valueOf(ob.get(4)));
				user.setGrade(String.valueOf(ob.get(5)));
				user.setPhone(String.valueOf(ob.get(6)));
				user.setEmail(String.valueOf(ob.get(7)));
				user.setRoleFlag(Integer.parseInt(ob.get(8).toString()));
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				user.setCreateTime(df.format(new Date()));
				userList.add(user);
			}
			//批量插入 
			userDao.insertUserList(userList);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	public XSSFWorkbook exportExcelInfo(List<String> idList) {
		//根据条件查询数据，把数据装载到一个list中
		List<User> userInfo=userDao.getUserByIdList(idList);
		List<ExcelBean> ems=new ArrayList<>();
		Map<Integer,List<ExcelBean>> map=new LinkedHashMap<>();
		List<QuerySaveMemberInfoDTO> member=new ArrayList<QuerySaveMemberInfoDTO>();
		QuerySaveMemberInfoDTO userDTO=null;
		Department department=null;
		Job job=null;
		for(User user:userInfo) {
			userDTO=new QuerySaveMemberInfoDTO();
			userDTO.setId(user.getId());
			userDTO.setName(user.getName());
			userDTO.setDigits(user.getDigits());
			userDTO.setDepartment(user.getDepartment());
			userDTO.setMajor(user.getMajor());
			userDTO.setBelongClass(user.getBelongClass());
			userDTO.setGrade(user.getGrade());
			userDTO.setPhone(user.getPhone());
			userDTO.setEmail(user.getEmail());
			department=this.departmentDao.getDepartmentById(user.getBelongId());
			job=this.jobDao.getJobById(user.getJobId());
			userDTO.setBelongId(department);
			userDTO.setJobId(job);
			userDTO.setRoleFlag(user.getRoleFlag());
			userDTO.setFinanceFlag(user.getFinanceFlag());
			userDTO.setCreateTime(user.getCreateTime());
			member.add(userDTO);
		}
		XSSFWorkbook book=null;
		ems.add(new ExcelBean("Id","Id",0));
		ems.add(new ExcelBean("姓名","name",0));
		ems.add(new ExcelBean("学号","digits",0));
		ems.add(new ExcelBean("学院","department",0));
		ems.add(new ExcelBean("专业","major",0));
		ems.add(new ExcelBean("班级","belongClass",0));
		ems.add(new ExcelBean("年级","grade",0));
		ems.add(new ExcelBean("手机号码","phone",0));
		ems.add(new ExcelBean("电子邮件","email",0));
		ems.add(new ExcelBean("职位","jobId",0));
		ems.add(new ExcelBean("所属部门","belongId",0));
		ems.add(new ExcelBean("用户权限","roleFlag",0));
		ems.add(new ExcelBean("创建时间","createTime",0));
		map.put(0,ems);
		try {
			book=ExcelUtil.createExcelFile(QuerySaveMemberInfoDTO.class, member, map, "成员信息表");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return book;
	}

	@Override
	public int addSingleUser(User user) {
		return this.userDao.addSingleUser(user);
	}

	@Override
	public int updateDelFlagByDigits(String digits, int delFlag) {
		return this.userDao.updateDelFlagByDigits(digits,delFlag);
	}

	@Override
	public int updateUserInfoByDigits(String digits, String name, String department, String major, String belongClass,
			String grade, String phone, String email, User user) {
		if(name == null) {
			name = user.getName();
		}
		if(department == null) {
			department = user.getDepartment();
		}
		if(major == null) {
			major = user.getMajor();
		}
		if(belongClass == null) {
			belongClass = user.getBelongClass();
		}
		if(grade == null) {
			grade = user.getGrade();
		}
		if(phone == null) {
			phone = user.getPhone();
		}
		if(email == null) {
			email = user.getEmail();
		}
		return this.userDao.updateUserInfoByDigits(digits, name, department, major, belongClass, grade, phone, email);
	}

	@Override
	public int getUsersCount() {
		return this.userDao.getUsersCount();
	}

	@Override
	public List<User> getUsersByPage(int startPos, int pageSize) {
		return this.userDao.getUsersByPage(startPos, pageSize);
	}

	@Override
	public int addUserByApplication(List<String> idList) {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		MemberApplicateInfo memberInfo=null;
		List<User> userList=new ArrayList<User>();
		for(String id:idList) {
			memberInfo=memberApplicateInfoDao.selectMemberApplicateInfoById(id);
			if(memberInfo==null)
				continue;
			User user=new User();
			user.setId(IdGen.uuid());
			user.setName(memberInfo.getName());
			user.setDigits(memberInfo.getDigits());
			user.setDepartment(memberInfo.getDepartment());
			user.setGrade(memberInfo.getGrade());
			user.setMajor(memberInfo.getMajor());
			user.setBelongClass(memberInfo.getBelongClass());
			user.setPhone(memberInfo.getPhone());
			user.setEmail(memberInfo.getEmail());
			user.setBelongId(memberInfo.getApplicateSection());
			user.setJobId(memberInfo.getApplicateJob());
			Job job=jobDao.getJobById(memberInfo.getApplicateJob());
			user.setRoleFlag(job.getRoleFlag());
			user.setCreateTime(df.format(new Date()));
			userList.add(user);
		}
		if(userList.size()==0)
			return 0;
		else
			return this.userDao.insertUserList(userList);
	}

	@Override
	public int updateCurrentLoginTimeById(String id, String date) {
		return this.userDao.updateCurrentLoginTimeById(id,date);
		
	}

	@Override
	public List<User> getUserList() {
		return this.userDao.getUserList();
	}

	@Override
	public int updateRoleFlagById(String memberId, Integer roleFlag) {
		return this.userDao.updateRoleFlagById(memberId,roleFlag);
	}

	@Override
	public List<User> getMemberList() {
		return this.userDao.getMemberList();
	}

	@Override
	public List<User> getMemberListByDepartmentId(String departmentId) {
		return this.userDao.getMemberListByDepartmentId(departmentId);
	}
}
