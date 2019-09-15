package com.ams.department.dto;

import com.ams.department.entity.Department;
import com.ams.department.entity.Job;

public class QuerySaveMemberInfoDTO {
	private String id;
    /**
	 * 姓名
	 */
    private String name;
    /**
	 * 学号
	 */
    private String digits;
    /**
	 * 学院
	 */
    private String department;
    /**
	 * 专业
	 */
    private String major;
    /**
	 * 班级
	 */
    private String belongClass;
    /**
	 * 年级
	 */
    private String grade;
    /**
	 * 手机号码
	 */
    private String phone;
    /**
	 * 邮箱
	 */
    private String email;
    /**
	 * 职位id(与职位表关联)
	 */
    private Job jobId;
    /**
	 * 所属部门id(与部门表关联)
	 */
    private Department belongId;
    /**
	 * 用户权限（0-系统管理员 1-一级管理员 2-二级管理员 3-三级管理员 4-四级管理员 5-五级管理员） 默认与职业对应权限相同
	 */
    private Integer roleFlag;
    /**
	 * 管理财务的权限(0-否 1-是)
	 */
    private Integer financeFlag;
    /**
	 * 创建时间
	 */
    private String createTime;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDigits() {
		return digits;
	}
	public void setDigits(String digits) {
		this.digits = digits;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getBelongClass() {
		return belongClass;
	}
	public void setBelongClass(String belongClass) {
		this.belongClass = belongClass;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getRoleFlag() {
		return roleFlag;
	}
	public void setRoleFlag(Integer roleFlag) {
		this.roleFlag = roleFlag;
	}
	public Integer getFinanceFlag() {
		return financeFlag;
	}
	public void setFinanceFlag(Integer financeFlag) {
		this.financeFlag = financeFlag;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Job getJobId() {
		return jobId;
	}
	public void setJobId(Job jobId) {
		this.jobId = jobId;
	}
	public Department getBelongId() {
		return belongId;
	}
	public void setBelongId(Department belongId) {
		this.belongId = belongId;
	}
}
