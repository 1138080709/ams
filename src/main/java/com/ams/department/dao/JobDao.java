package com.ams.department.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ams.department.dto.QuerySaveJobInfoDTO;
import com.ams.department.entity.Job;

public interface JobDao {
    int deleteByPrimaryKey(String id);

    int insertSelective(Job record);

    Job selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Job record);

    int updateByPrimaryKey(Job record);
    
    int insert(Job job);

	List<Job> getAllJobList(@Param("queryInfo")String queryInfo, @Param("offset")int offset, @Param("limit")int limit);

	int getAllJobCount();

	Job getJobById(String orderJobId);

	int updateInfoById(@Param("orderJobId")String orderJobId, @Param("newJobName")String newJobName, @Param("newRoleFlag")Integer newRoleFlag, @Param("newBelongId")String newBelongId);

	int updateDelFlagById(String selectJobId);

	int getJobCountByDepartmentId(String selectDepartmentId);

	Job getJobByName(String jobName);

	List<Job> getJobList();
}