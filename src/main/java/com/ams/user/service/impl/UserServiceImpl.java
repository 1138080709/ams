package com.ams.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ams.department.dao.DepartmentDao;
import com.ams.department.dao.JobDao;
import com.ams.department.dto.QuerySaveDepartmentInfoDTO;
import com.ams.department.dto.QuerySaveJobInfoDTO;
import com.ams.department.dto.QuerySaveMemberInfoDTO;
import com.ams.department.entity.Department;
import com.ams.department.entity.Job;
import com.ams.user.dao.UserDao;
import com.ams.user.entity.User;
import com.ams.user.service.IUserService;
@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private UserDao userDao;
	@Resource
	private DepartmentDao departmentDao;
	@Resource
	private JobDao jobDao;
	
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

	public int updateJobById(String memberId, String memberJobId, Integer roleFlag) {
		return this.userDao.updateJobById(memberId, memberJobId, roleFlag);
	}
}
