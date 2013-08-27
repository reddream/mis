package org.web.test;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.models.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.web.security.AuthMng;
import org.web.session.SessionProvider;


@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ContextConfiguration(locations = { "classpath:org-servlet.xml" })
public class TestLoginService {
	
	@Autowired
	protected AuthMng authMng;
	
	@Autowired
	protected SessionProvider sessionProvider;
	
	@Before
	public void prepareTestData() {
		System.out.println("before TestLoginService");
	}
	
	@Test
	public void testLogin() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		Operator user;
			user = authMng.login("sysadmin", "sysadmin",1, request,
					response, sessionProvider);

		Assert.assertTrue(user != null);
		Assert.assertTrue(user.getId() > 0);
		Assert.assertTrue(StringUtils.isNotBlank(user.getActionUrls()));
		user = authMng.login("sysadmin", "sysadmin",-1, request,
				response, sessionProvider);
		Assert.assertTrue(user != null);
		Assert.assertTrue(user.getId() == null);
	}
	
	
	@After
	public void after() {
		System.out.println("after TestLoginService");
	}
	
}
