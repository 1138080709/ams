package com.ams.department.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ams.department.dao.DepartmentDao;
import com.ams.department.dto.QuerySaveDepartmentInfoDTO;
import com.ams.department.entity.Department;
import com.ams.department.service.IDepartmentService;
import com.ams.user.dao.UserDao;
import com.ams.user.entity.User;
@Service("departmentService")
public class DepartmentServiceImpl implements IDepartmentService{
	@Resource
	private DepartmentDao departmentDao;
	@Resource
	private UserDao userDao;
	
	public List<QuerySaveDepartmentInfoDTO> getAlldepartmentList(String queryInfo, int offset, int limit) {
		List<QuerySaveDepartmentInfoDTO> departments=new ArrayList<QuerySaveDepartmentInfoDTO>();
		List<Department> departmentInfo=this.departmentDao.getAlldepartmentList(queryInfo,offset,limit);
		User user=null;
		QuerySaveDepartmentInfoDTO departmentDTO=null;
		for(Department department:departmentInfo) {
			departmentDTO=new QuerySaveDepartmentInfoDTO();
			departmentDTO.setId(department.getId());
			departmentDTO.setDepartmentName(department.getDepartmentName());
			user=this.userDao.getUserById(department.getMinisterId());
			departmentDTO.setMinister(user);
			departmentDTO.setDescription(department.getDescription());
			departmentDTO.setCreateTime(department.getCreateTime());
			departments.add(departmentDTO);
		}
		return departments;
	}

	public int getAlldepartmentCount() {
		return this.departmentDao.getAlldepartmentCount();
	}

	public int updateInfoById(String departmentId, String newDepartmentName, String newMinisterId, 
			String newDesc) {
		return this.departmentDao.updateInfoById(departmentId, newDepartmentName, newMinisterId, newDesc);
	}

	public int insertNewDepartment(Department newDepartment) {
		return this.departmentDao.insert(newDepartment);
	}

	public Department getDepatmentById(String orderDepartmentId) {
		return this.departmentDao.getDepartmentById(orderDepartmentId);
	}

	public int updateDelFlagById(String selectDepartmentId) {
		return this.departmentDao.updateDelFlagById(selectDepartmentId);
	}

}
