package com.ams.user.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ams.department.entity.Department;
import com.ams.department.entity.Job;
import com.ams.user.dao.MemberApplicateInfoDao;
import com.ams.user.dto.MemberApplicateInfoDTO;
import com.ams.user.entity.MemberApplicateInfo;
import com.ams.user.service.IMemberApplicateInfoService;
import com.ams.utils.IdGen;
@Service("memberApplicateInfoService")
public class MemberApplicateInfoServiceImpl implements IMemberApplicateInfoService{
	@Resource
	private MemberApplicateInfoDao memberApplicateInfoDao;
	@Resource
	private JavaMailSender javaMailSender;
	
	
	public MemberApplicateInfo getMemberApplicateInfoById(String MemberApplicateInfoId) {
		return this.memberApplicateInfoDao.getInfoById(MemberApplicateInfoId);
	}

	public List<MemberApplicateInfoDTO> getAllApplicateInfoList() {
		return this.memberApplicateInfoDao.getAllApplicateInfoList();
	}

	@Override
	public List<String> updateApplicateSuccessStatus(List<String> idList) {
		MemberApplicateInfo memberInfo=null;
		List<String> errorId =new ArrayList<String>();
		for(String id:idList) {
			memberInfo=memberApplicateInfoDao.getInfoById(id);
			if(memberInfo==null) {
				errorId.add(id);
				continue;
			}
			boolean result=this.sendSuccessMail(memberInfo.getEmail());
			if(result==false)
				errorId.add(id);
		}
		this.memberApplicateInfoDao.updateApplicateSuccessStatus(idList);
		return errorId;
	}

	@Override
	public List<String> updateApplicateFailStatus(List<String> idList) {
		MemberApplicateInfo memberInfo=null;
		List<String> errorId =new ArrayList<String>();
		for(String id:idList) {
			memberInfo=memberApplicateInfoDao.getInfoById(id);
			if(memberInfo==null) {
				errorId.add(id);
				continue;
			}
			boolean result=this.sendFailMail(memberInfo.getEmail());
			if(result==false)
				errorId.add(id);
		}
		this.memberApplicateInfoDao.updateApplicateFailStatus(idList);
		return errorId;
	}

	@Override
	public int addInfoByMemberApplicateInfo(MemberApplicateInfo memberApplicateInfo,Department department,Job job) {
		String id=IdGen.uuid();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime=df.format(new Date());
		memberApplicateInfo.setApplicateTime(createTime);
		memberApplicateInfo.setId(id);
		memberApplicateInfo.setApplicateSection(department.getId());
		memberApplicateInfo.setApplicateJob(job.getId());
		return this.memberApplicateInfoDao.insertSelective(memberApplicateInfo);
	}

	@Override
	public MemberApplicateInfo getMemberApplicateInfoByDigits(String digits) {
		return this.memberApplicateInfoDao.getMemberApplicateInfoByDigit(digits);
	}

	@Override
	public boolean sendSuccessMail(String mail) {
		boolean result=true;
		MimeMessage mMessage=javaMailSender.createMimeMessage();//创建邮件对象
		MimeMessageHelper mMessageHelper;
		Properties prop=new Properties();
		try {
			prop.load(this.getClass().getResourceAsStream("/mail.properties"));
			String mailFrom=(String) prop.get("mail.smtp.username");
			mMessageHelper=new MimeMessageHelper(mMessage,true);
			mMessageHelper.setFrom(mailFrom);
			mMessageHelper.setTo(mail);
			mMessageHelper.setSubject("部门申请通过");
			mMessageHelper.setText("<p>恭喜您成功通过审核:您的社团账号为本人学号,默认密码为000000</p>",true);
			javaMailSender.send(mMessage);
		} catch (Exception e) {
			result=false;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean sendFailMail(String mail) {
		boolean result=true;
		MimeMessage mMessage=javaMailSender.createMimeMessage();//创建邮件对象
		MimeMessageHelper mMessageHelper;
		Properties prop=new Properties();
		try {
			prop.load(this.getClass().getResourceAsStream("/mail.properties"));
			String mailFrom=(String) prop.get("mail.smtp.username");
			mMessageHelper=new MimeMessageHelper(mMessage,true);
			mMessageHelper.setFrom(mailFrom);
			mMessageHelper.setTo(mail);
			mMessageHelper.setSubject("社团申请失败");
			mMessageHelper.setText("<p>通知:很遗憾您没有通过审核</p>",true);
			javaMailSender.send(mMessage);
		} catch (Exception e) {
			result=false;
			e.printStackTrace();
		}
		return result;
	}
}
