package com.lasho.guzz;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lasho.guzz.domain.UserAccount;
import com.lasho.guzz.service.UserAccountService;

public class UserAccountAllDbShadowTest {

	static ApplicationContext appContext = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	static UserAccountService userAccountService = (UserAccountService) appContext
			.getBean("userAccountAllService", UserAccountService.class);
	
	@Test
	public void testSave(){
		UserAccount acct = new UserAccount() ;
		acct.setUserId(3);
		acct.setAreaId(2) ;
		acct.setAccount("account") ;
		int saveResult = userAccountService.save(acct);
		System.out.println(String.format("save result is %s", saveResult));
		Assert.assertTrue(saveResult > 0) ;
	}

	@Test
	public void testUpdate(){
		UserAccount acct = new UserAccount() ;
		acct.setId(1);
		acct.setUserId(3) ;
		acct.setAreaId(2) ;
		acct.setAccount("account-new") ;
		boolean upResult = userAccountService.update(acct);
		System.out.println(String.format("update result is %s", upResult));
	}
	
	@Test
	public void testFindAll(){
		List<UserAccount> accts = userAccountService.findAllAccounts() ;
		System.out.println(accts.size());
	}
}
