package com.ams.activity.entity;

/**
 * 活动参与者信息表
 * 
 * @author 老吴
 * @create 2019-09-05
 */
public class ActivityParticipatorInfo {
    private String id;
    /**
	 * 参与者姓名
	 */
    private String participatorName;
    /**
	 * 参与者学号
	 */
    private String participatorDigits;
    /**
	 * 参与者学院
	 */
    private String participatorDepartment;
    /**
	 * 专业班级
	 */
    private String participatorClass;
    /**
	 * 参与活动id(与活动信息表关联)
	 */
    private String activityId;
    /**
	 * 删除标志 (0-未删除 1-已删除)
	 */
    private Integer delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getParticipatorName() {
        return participatorName;
    }

    public void setParticipatorName(String participatorName) {
        this.participatorName = participatorName == null ? null : participatorName.trim();
    }

    public String getParticipatorDigits() {
        return participatorDigits;
    }

    public void setParticipatorDigits(String participatorDigits) {
        this.participatorDigits = participatorDigits == null ? null : participatorDigits.trim();
    }

    public String getParticipatorDepartment() {
        return participatorDepartment;
    }

    public void setParticipatorDepartment(String participatorDepartment) {
        this.participatorDepartment = participatorDepartment == null ? null : participatorDepartment.trim();
    }

    public String getParticipatorClass() {
        return participatorClass;
    }

    public void setParticipatorClass(String participatorClass) {
        this.participatorClass = participatorClass == null ? null : participatorClass.trim();
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivitId(String activityId) {
        this.activityId = activityId == null ? null : activityId.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}