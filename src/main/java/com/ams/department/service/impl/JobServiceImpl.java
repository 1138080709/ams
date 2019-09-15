package com.ams.department.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ams.department.dao.DepartmentDao;
import com.ams.department.dao.JobDao;
import com.ams.department.dto.QuerySaveDepartmentInfoDTO;
import com.ams.department.dto.QuerySaveJobInfoDTO;
import com.ams.department.entity.Department;
import com.ams.department.entity.Job;
import com.ams.department.service.IJobService;

@Service("jobService")
public class JobServiceImpl implements IJobService{
	@Resource
	private JobDao jobDao;
	@Resource
	private DepartmentDao departmentDao;

	public List<QuerySaveJobInfoDTO> getAllJobList(String queryInfo, int offset, int limit) {
		List<QuerySaveJobInfoDTO> jobs=new ArrayList<QuerySaveJobInfoDTO>();
		List<Job> jobInfo=this.jobDao.getAllJobList(queryInfo,offset,limit);
		Department department=null;
		QuerySaveJobInfoDTO jobDTO=null;
		for(Job job:jobInfo) {
			jobDTO=new QuerySaveJobInfoDTO();
			jobDTO.setId(job.getId());
			jobDTO.setJobName(job.getJobName());
			department=this.departmentDao.getDepartmentById(job.getBelongId());
			jobDTO.setBelongId(department);
			jobDTO.setRoleFlag(job.getRoleFlag());
			jobDTO.setCreateTime(job.getCreateTime());
			jobs.add(jobDTO);
		}
		return jobs;
	}

	public int getAllJobCount() {
		return this.jobDao.getAllJobCount();
	}

	public int insertNewJob(Job job) {
		return this.jobDao.insert(job);
	}

	public Job getJobById(String orderJobId) {
		return this.jobDao.getJobById(orderJobId);
	}

	public int updateInfoById(String orderJobId, String newJobName, int newRoleFlag, String newBelongId) {
		return this.jobDao.updateInfoById(orderJobId,newJobName,newRoleFlag,newBelongId);
	}

	public int updateDelFlagById(String selectJobId) {
		return this.jobDao.updateDelFlagById(selectJobId);
	}

	public int getJobCountByDepartmentId(String selectDepartmentId) {
		return this.jobDao.getJobCountByDepartmentId(selectDepartmentId);
	}
	
}
