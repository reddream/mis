package org.web.test;

import org.common.page.Pagination;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mis.service.vendor.VendorService;
import org.models.vendor.VendorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ContextConfiguration(locations = { "classpath:org-servlet.xml" })
public class TestVendorService {
	@Before
	public void prepareTestData() {
		System.out.println("before");
	}
	
	@Autowired
	private VendorService vendorService;
	
	@Test
	public void TestVendorSearch(){
		VendorRequest vreq = new VendorRequest();
		vreq.setVendorName("");
		Pagination p = vendorService.search(vreq, 1, 0, 20);
		Assert.assertTrue(p.getTotalCount()>0);
		Assert.assertTrue(p.getList().size()>0);
	}
	
	
	@After
	public void after() {
		System.out.println("after");
	}
}
