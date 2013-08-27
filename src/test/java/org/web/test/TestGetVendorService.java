package org.web.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mis.service.vendor.VendorService;
import org.models.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ContextConfiguration(locations = { "classpath:org-servlet.xml" })
public class TestGetVendorService {

	@Before
	public void prepareTestData() {
		System.out.println("before");
	}
	
	@Autowired		
	protected VendorService vendorService;
	
	@Test
	public void testGetVendor() throws Exception{
		Vendor v = vendorService.get(18);
		Assert.assertTrue(v.getId()!=null);
	}
	
	
	@After
	public void after() {
		System.out.println("after");
	}
	
	
}
