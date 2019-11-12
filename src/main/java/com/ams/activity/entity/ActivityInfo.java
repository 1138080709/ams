package com.ams.activity.entity;

/**
 * 活动信息表
 * 
 * @author 老吴
 * @create 2019-09-05
 */
public class ActivityInfo {
    private String id;
    /**
	 * 活动主题
	 */
    private String activityTheme;
    /**
	 * 活动开始时间
	 */
    private String startTime;
    /**
	 * 活动结束时间
	 */
    private String overTime;
    /**
	 * 活动负责人id(与用户表关联)
	 */
    private String principalName;
    /**
	 * 活动状态(0-未开始 1-正在进行 2-已结束)
	 */
    private Integer activityFlag;
    /**
	 * 活动详细信息
	 */
    private String detailInfo;
    /**
	 * 删除标志 0-未删除 1-已删除
	 */
    private Integer delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getActivityTheme() {
        return activityTheme;
    }

    public void setActivityTheme(String activityTheme) {
        this.activityTheme = activityTheme == null ? null : activityTheme.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime == null ? null : overTime.trim();
    }

    public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public Integer getActivityFlag() {
        return activityFlag;
    }

    public void setActivityFlag(Integer activityFlag) {
        this.activityFlag = activityFlag;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo == null ? null : detailInfo.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}