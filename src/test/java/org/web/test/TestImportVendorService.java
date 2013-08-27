package org.web.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mis.service.batch.BatchService;
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
public class TestImportVendorService {
	
	@Autowired
	protected BatchService batchService;
	
	@Before
	public void prepareTestData() {
		System.out.println("before TestImportVendorService");
	}
	
	@Test
	public void testImportVendor() throws Exception {
		InputStream is = new FileInputStream(new File("C:\\is.xlsx"));
		//String s = batchServiceImpl.importProducts(is, 1);
		MockHttpServletRequest request = new MockHttpServletRequest();
		BatchParam param = new BatchParam();
		param.setOperatorId(1);
		param.setRequest(request);
		byte[] result= batchService.importVendors(is, param);
		FileOutputStream fo = new FileOutputStream("D:\\testImportVendor.xls");
		fo.write(result);
		fo.flush();
		fo.close();
	}
	
	
	@After
	public void after() {
		System.out.println("after TestImportVendorService");
	}
	
}
