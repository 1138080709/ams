package com.ams.user.dto;

public class MemberApplicateInfoDTO {
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
	 * 联系方式
	 */
    private String phone;
    /**
	 * 邮箱
	 */
    private String email;
    /**
	 * 申请部门
	 */
    private String applicateSection;
    /**
	 * 申请职位
	 */
    private String applicateJob;
    /**
	 * 申请状态(0-未审核 1-已通过 2-未通过)
	 */
    private Integer applicateFlag;
    /**
	 * 申请时间
	 */
    private String applicateTime;

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

    public String getApplicateSection() {
        return applicateSection;
    }

    public void setApplicateSection(String applicateSection) {
        this.applicateSection = applicateSection == null ? null : applicateSection.trim();
    }

    public String getApplicateJob() {
        return applicateJob;
    }

    public void setApplicateJob(String applicateJob) {
        this.applicateJob = applicateJob == null ? null : applicateJob.trim();
    }

    public Integer getApplicateFlag() {
        return applicateFlag;
    }

    public void setApplicateFlag(Integer applicateFlag) {
        this.applicateFlag = applicateFlag;
    }

    public String getApplicateTime() {
        return applicateTime;
    }

    public void setApplicateTime(String applicateTime) {
        this.applicateTime = applicateTime == null ? null : applicateTime.trim();
    }

}
