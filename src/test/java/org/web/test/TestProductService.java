package org.web.test;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mis.service.product.ProductService;
import org.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ContextConfiguration(locations = { "classpath:org-servlet.xml" })
public class TestProductService {

	@Autowired
	private ProductService ps;
	

	@Before
	public void prepareTestData() {
		System.out.println("before");
	}
	
	
//	@Test
//	public void TestPS() throws Exception{
//		List<Product> products = ps.listProducts();
//		for(Product p:products){
//			Assert.assertTrue(StringUtils.isNotBlank(p.getVendor().getName()));
//		}
//	}
//	
	
	
	@After
	public void after() {
		System.out.println("after");
	}
	
	
}
