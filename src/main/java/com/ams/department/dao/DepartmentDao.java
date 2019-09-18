package com.ams.department.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ams.department.entity.Department;

public interface DepartmentDao {
	
	
    int deleteByPrimaryKey(String id);

    int insertSelective(Department record);


    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
    
    
    Department getDepartmentById(String id);
    
    int insert(Department newDepartment);
    
	List<Department> getAlldepartmentList(@Param("queryInfo")String queryInfo, @Param("offset")int offset, @Param("limit")int limit);

	int getAlldepartmentCount();
	
	int updateInfoById(@Param("departmentId")String departmentId, @Param("newDepartmentName")String newDepartmentName, @Param("newMinisterId")String newMinisterId, @Param("newDescription")String newDescription);

	int updateDelFlagById(String selectDepartmentId);

	Department selectDepartmentByName(String departmentName);
}