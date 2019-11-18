package com.ams.department.service.impl;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ams.department.dao.DepartmentDao;
import com.ams.department.dto.QuerySaveDepartmentInfoDTO;
import com.ams.department.entity.Department;
import com.ams.department.service.IDepartmentService;
import com.ams.user.dao.UserDao;
import com.ams.user.entity.User;
import com.ams.utils.IdGen;
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

	public int insertNewDepartment(Department newDepartment) {
		return this.departmentDao.insert(newDepartment);
	}

	public Department getDepatmentById(String orderDepartmentId) {
		return this.departmentDao.getDepartmentById(orderDepartmentId);
	}

	public int updateDelFlagById(String selectDepartmentId) {
		return this.departmentDao.updateDelFlagById(selectDepartmentId);
	}

	@Override
	public Department selectDepartmentByName(String departmentName) {
		if(departmentName!=null)
			departmentName=departmentName.trim();
		return this.departmentDao.selectDepartmentByName(departmentName);
	}

	@Override
	public Department selectDepartmentById(String departmentId) {
		return this.departmentDao.getDepartmentById(departmentId);
	}

	@Override
	public int insertNewDepartment(Department department, User ministerUser) {
		String departmentId=IdGen.uuid();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime=df.format(new Date());
		department.setId(departmentId);
		department.setCreateTime(createTime);
		if(ministerUser!=null)
			department.setMinisterId(ministerUser.getId());
		
		return this.departmentDao.insert(department);
	}

	@Override
	public int updateInfoById(Department department, User ministerUser) {
		if(ministerUser!=null)
			department.setMinisterId(ministerUser.getId());
		return this.departmentDao.updateInfoById(department);
	}

	@Override
	public List<Department> getDepartmentList() {
		return this.departmentDao.getDepartmentList();
	}

	@Override
	public Integer getDepartmentNumber(String id) {
		return this.userDao.getDepartmentNumber(id);
	}


}
