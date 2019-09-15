package com.ams.department.service;

import java.util.List;

import com.ams.department.dto.QuerySaveJobInfoDTO;
import com.ams.department.entity.Job;

public interface IJobService {

	List<QuerySaveJobInfoDTO> getAllJobList(String queryInfo, int offset, int limit);

	int getAllJobCount();

	int insertNewJob(Job job);

	Job getJobById(String orderJobId);

	int updateInfoById(String orderJobId, String newJobName, int newRoleFlag, String newBelongId);

	int updateDelFlagById(String selectJobId);

	int getJobCountByDepartmentId(String selectDepartmentId);
	
}
