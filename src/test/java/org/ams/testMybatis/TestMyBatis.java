package org.ams.testMybatis;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.ams.user.entity.MemberApplicateInfo;
import com.ams.user.entity.User;
import com.ams.user.service.IMemberApplicateInfoService;
import com.ams.user.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class) //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations= {"classpath:spring-mybatis.xml"})
public class TestMyBatis {
	private static Logger logger=Logger.getLogger(TestMyBatis.class);
	@Resource
	private IUserService userService=null;
	@Resource
	private IMemberApplicateInfoService memberApplicateInfoService=null;
	@Test
	public void test1() {
		User user=userService.getUserById("1");
		logger.info(JSON.toJSON(user));
		MemberApplicateInfo memberApplicateInfo=memberApplicateInfoService.getMemberApplicateInfoById("1");
		logger.info(JSON.toJSON(memberApplicateInfo));
	}
}
