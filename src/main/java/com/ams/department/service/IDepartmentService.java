package com.ams.department.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ams.department.dto.QuerySaveDepartmentInfoDTO;
import com.ams.department.entity.Department;
import com.ams.user.entity.User;


public interface IDepartmentService {

	public List<QuerySaveDepartmentInfoDTO> getAlldepartmentList(String queryInfo, int offset, int limit);

	public int getAlldepartmentCount();

	//public int updateInfoById(String departmentId, String newDepartmentName, String newMinisterId, String newDescribe);

	public int insertNewDepartment(Department newDepartment);

	public Department getDepatmentById(String orderDepartmentId);

	public int updateDelFlagById(String selectDepartmentId);

	public Department selectDepartmentByName(String departmentName);

	public Department selectDepartmentById(String departmentId);

	public int insertNewDepartment(Department department, User ministerUser);

	public int updateInfoById(Department department, User ministerUser);
}
