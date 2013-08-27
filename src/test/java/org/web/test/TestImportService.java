package org.web.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mis.dao.product.ProductDao;
import org.mis.service.batch.BaseBatchServiceImpl;
import org.mis.service.batch.BatchService;
import org.mis.service.batch.BatchServiceImpl;
import org.mis.service.batch.BatchXmlService;
import org.mis.service.batch.BatchXmlServiceImpl;
import org.models.Product;
import org.models.batch.BatchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ContextConfiguration(locations = { "classpath:org-servlet.xml" })
public class TestImportService {
	
	@Autowired
	protected BatchService batchService;
	@Autowired
	protected ProductDao productDao;
	
	@Autowired
	protected BatchService batchServiceImpl;
	
	@Autowired
	protected BatchXmlService batchXmlServiceImpl;
	
	@Before
	public void prepareTestData() {
		System.out.println("before");
	}

	
	@Test
	  //import product by xlsx
	public void testImportProductsByXlsx() throws Exception{
		InputStream is = new FileInputStream(new File("C:\\is.xlsx"));
		//String s = batchServiceImpl.importProducts(is, 1);
		MockHttpServletRequest request = new MockHttpServletRequest();
		BatchParam param = new BatchParam();
		param.setOperatorId(1);
		param.setRequest(request);
		byte[] result= batchServiceImpl.importProducts(is, param);
		FileOutputStream fo = new FileOutputStream("D:\\testImportProductsByXlsx.xls");
		fo.write(result);
		fo.flush();
		fo.close();
	}

	@Test
	//import product by xml
	public void testXmlProduct() throws Exception{
		System.out.println("testXmlProduct");
		InputStream is = new FileInputStream(new File("c:\\products.xml"));
		MockHttpServletRequest request = new MockHttpServletRequest();
		BatchParam param = new BatchParam();
		param.setOperatorId(1);
		param.setRequest(request);
		byte[] result = batchXmlServiceImpl.importProducts(is, param);
		FileOutputStream fo = new FileOutputStream("D:\\testXmlProduct.xls");
		fo.write(result);
		fo.flush();
		fo.close();
	}
	
	@Test
	//import vendor by xml
	public void testXmlVendor() throws Exception{
		System.out.println("testXmlVendor");
		InputStream is = new FileInputStream(new File("c:\\vendors.xml"));
		MockHttpServletRequest request = new MockHttpServletRequest();
		BatchParam param = new BatchParam();
		param.setOperatorId(1);
		param.setRequest(request);
		byte[] result = batchXmlServiceImpl.importVendors(is, param);
		FileOutputStream fo = new FileOutputStream("D:\\testXmlVendor.xls");
		fo.write(result);
		fo.flush();
		fo.close();
	}

	
	@After
	public void after() {
		System.out.println("after");
	}
	
}
