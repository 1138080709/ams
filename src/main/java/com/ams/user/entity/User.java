package com.ams.user.entity;

/**
 * 用户表
 * 
 * @author 老吴
 * @create 2019-09-05
 */
public class User {
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
	 * 登录密码
	 */
    private String password;
    /**
	 * 手机号码
	 */
    private String phone;
    /**
	 * 邮箱
	 */
    private String email;
    /**
	 * 职业id(与职业表关联)
	 */
    private String jobId;
    /**
	 * 所属部门id(与部门表关联)
	 */
    private String belongId;
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
    /**
     * 最近登录时间
     */
    private String currentLoginTime;
    /**
	 * 删除标志 (0-未删除 1-已删除)
	 */
    private Integer delFlag;
    public User() {
    	super();
    }
    
	public User(String id, String name, String digits, String department, String major, String belongClass,
			String grade, String phone, String email) {
		super();
		this.id = id;
		this.name = name;
		this.digits = digits;
		this.department = department;
		this.major = major;
		this.belongClass = belongClass;
		this.grade = grade;
		this.phone = phone;
		this.email = email;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDigits() {
        return digits;
    }

    public void setDigits(String digits) {
        this.digits = digits == null ? null : digits.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public String getBelongClass() {
        return belongClass;
    }

    public void setBelongClass(String belongClass) {
        this.belongClass = belongClass == null ? null : belongClass.trim();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId == null ? null : jobId.trim();
    }

    public String getBelongId() {
        return belongId;
    }

    public void setBelongId(String belongId) {
        this.belongId = belongId == null ? null : belongId.trim();
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
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

	public String getCurrentLoginTime() {
		return currentLoginTime;
	}

	public void setCurrentLoginTime(String currentLoginTime) {
		this.currentLoginTime = currentLoginTime == null ? null : currentLoginTime.trim();
	}
}