package com.ams.finance.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ams.finance.dao.CashDao;
import com.ams.finance.dao.CashFlowInfoDao;
import com.ams.finance.entity.CashFlowInfo;
import com.ams.finance.service.ICashService;
import com.ams.user.dao.UserDao;
import com.ams.user.entity.User;
import com.ams.utils.IdGen;
@Service("cashService")
public class CashServiceImpl implements ICashService {
	@Resource
	private  CashDao cashDao;
	@Resource
	private CashFlowInfoDao cashFlowInfoDao;
	@Resource
	private UserDao userDao;
	
	@Override
	public int queryCurrentBalance() {
		return this.cashDao.queryCurrentBalance();
	}

	@Override
	public int applicateCash(CashFlowInfo cashFlowApplicate, String proposerId) {
		String id=IdGen.uuid();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime=df.format(new Date());
		cashFlowApplicate.setId(id);
		cashFlowApplicate.setProposerId(proposerId);
		cashFlowApplicate.setApplicateTime(createTime);
		System.out.println(cashFlowApplicate.getInfoType());
		return this.cashFlowInfoDao.insertSelective(cashFlowApplicate);
	}

	@Override
	public List<CashFlowInfo> getAuditList(String queryInfo, int offset, int limit) {
		return this.cashFlowInfoDao.getAuditList(queryInfo,offset,limit);
	}

	@Override
	public int getCashFlowInfoCount() {
		return this.cashFlowInfoDao.getCashFlowInfoCount();
	}

	@Override
	public int updateApplicateRejectStatus(List<String> idList,String auditorId) {
		return this.cashFlowInfoDao.updateApplicateRejectStatus(idList,auditorId);
	}

	@Override
	public int updateApplicateAuthorizeStatus(List<String> idList,String auditorId) {
		return this.cashFlowInfoDao.updateApplicateAuthorizestatus(idList,auditorId);
	}


	@Override
	public int updateApplicateCancelStatus(List<String> idList ,String auditorId) {
		return this.cashFlowInfoDao.updateApplicateCancelStatus(idList,auditorId);
	}

	@Override
	public List<CashFlowInfo> getExecuteList(String queryInfo, int offset, int limit) {
		return this.cashFlowInfoDao.getExecuteList(queryInfo,offset,limit);
	}

	@Override
	public int getExecuteCount() {
		return this.cashFlowInfoDao.getExecuteCount();
	}

	@Override
	public int updateCashCount(String id) {
		int count=cashDao.queryCurrentBalance();
		int newCount=count;
		CashFlowInfo cashFlowInfo=cashFlowInfoDao.getInfoById(id);
		if(cashFlowInfo==null)
			return 0;
		if(cashFlowInfo.getInfoType()==0)
			newCount=count-cashFlowInfo.getMoney();
		else if(cashFlowInfo.getInfoType()==1)
			newCount=count+cashFlowInfo.getMoney();
		else
			return 0;
		return this.cashDao.updateMoney(newCount);
	}

	public int updateExecuteFlag(String id) {
		return this.cashFlowInfoDao.updateExecuteFlag(id);
	}

	@Override
	public int cancelCashCount(String id) {
		int count=cashDao.queryCurrentBalance();
		int newCount=count;
		CashFlowInfo cashFlowInfo=cashFlowInfoDao.getInfoById(id);
		if(cashFlowInfo==null)
			return 0;
		if(cashFlowInfo.getInfoType()==1)
			newCount=count-cashFlowInfo.getMoney();
		else if(cashFlowInfo.getInfoType()==0)
			newCount=count+cashFlowInfo.getMoney();
		else
			return 0;
		return this.cashDao.updateMoney(newCount);
	}

	@Override
	public int cancelExecuteFlag(String id) {
		return this.cashFlowInfoDao.updateCancelExecuteFlag(id);
	}

	@Override
	public List<CashFlowInfo> getCashFlowInfoList(String queryInfo, int offset, int limit) {
		return this.cashFlowInfoDao.getCashFlowInfoList(queryInfo,offset,limit);
	}

	@Override
	public int getCashFlowInfoListCount() {
		return this.cashFlowInfoDao.getCashFlowInfoListCount();
	}

	@Override
	public int getpersonalAuditHistoryCount(String id) {
		return this.cashFlowInfoDao.getpersonalAuditHistoryCount(id);
	}

	@Override
	public List<CashFlowInfo> getpersonalAuditHistory(String id, String queryInfo, int offset, int limit) {
		return this.cashFlowInfoDao.getpersonalAuditHistory(id,queryInfo,offset,limit);
	}

}
