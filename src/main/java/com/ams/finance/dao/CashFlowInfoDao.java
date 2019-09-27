package com.ams.finance.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ams.finance.entity.CashFlowInfo;

public interface CashFlowInfoDao {
//    int deleteByPrimaryKey(String id);
//
//    int insert(CashFlowInfo record);
//
//    CashFlowInfo selectByPrimaryKey(String id);
//
//    int updateByPrimaryKeySelective(CashFlowInfo record);
//
//    int updateByPrimaryKey(CashFlowInfo record);
    
    int insertSelective(CashFlowInfo record);

	int getCashFlowInfoCount();

	List<CashFlowInfo> getAuditList(@Param("queryInfo")String queryInfo, @Param("offset")int offset, @Param("limit")int limit);

	int updateApplicateRejectStatus(@Param("list")List<String> idList, @Param("auditorId")String auditorId);

	int updateApplicateAuthorizestatus(@Param("list")List<String> idList, @Param("auditorId")String auditorId);

	List<CashFlowInfo> getInfoByList(List<String> idList);

	int updateApplicateCancelStatus(@Param("list")List<String> idList, @Param("auditorId")String auditorId);

	List<CashFlowInfo> getExecuteList(@Param("queryInfo")String queryInfo, @Param("offset")int offset, @Param("limit")int limit);

	int getExecuteCount();

	CashFlowInfo getInfoById(String id);

	int updateExecuteFlag(String id);

	int updateCancelExecuteFlag(String id);

	List<CashFlowInfo> getCashFlowInfoList(@Param("queryInfo")String queryInfo, @Param("offset")int offset, @Param("limit")int limit);

	int getCashFlowInfoListCount();

	int getpersonalAuditHistoryCount(String id);

	List<CashFlowInfo> getpersonalAuditHistory(@Param("id")String id, @Param("queryInfo")String queryInfo, @Param("offset")int offset, @Param("limit")int limit);
}