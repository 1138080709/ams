package com.ams.finance.service;

import java.util.List;

import com.ams.finance.entity.CashFlowInfo;
import com.ams.user.entity.User;

public interface ICashService {

	int queryCurrentBalance();

	int applicateCash(CashFlowInfo cashFlowApplicate, String proposerId);

	List<CashFlowInfo> getAuditList(String queryInfo, int offset, int limit);

	int getCashFlowInfoCount();

	int updateApplicateRejectStatus(List<String> idList, String string);

	int updateApplicateAuthorizeStatus(List<String> idList,String auditorId);

	int updateApplicateCancelStatus(List<String> idList,String auditorId);

	List<CashFlowInfo> getExecuteList(String queryInfo, int offset, int limit);

	int getExecuteCount();

	int updateCashCount(String id);

	int updateExecuteFlag(String id);

	int cancelCashCount(String id);

	int cancelExecuteFlag(String id);

	List<CashFlowInfo> getCashFlowInfoList(String queryInfo, int offset, int limit);

	int getCashFlowInfoListCount();

	int getpersonalAuditHistoryCount(String id);

	List<CashFlowInfo> getpersonalAuditHistory(String id, String queryInfo, int offset, int limit);

}
