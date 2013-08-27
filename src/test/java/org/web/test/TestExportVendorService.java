package org.web.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mis.service.batch.BatchOutputServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ContextConfiguration(locations = { "classpath:org-servlet.xml" })
public class TestExportVendorService {
	
	@Autowired
	protected BatchOutputServiceImpl batchOutputServiceImpl;
	
	@Before
	public void prepareTestData() {
		System.out.println("before TestExportVendorService");
	}
	
	@Test
//	public void testImportVendor() throws Exception {
////		String str = batchOutputServiceImpl.exportVendorsCSV();
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
//		String fileName = "D:\\ExportVendor" + formatter.format(new Date()) + ".csv";
//	    File file=new File(fileName);
//	    if(!file.exists()){
//	    	file.createNewFile();
//	    }
//		byte[] bs = str.getBytes();
//		FileOutputStream fos = new FileOutputStream(file);
//		fos.write(bs);
//		fos.flush();
//		fos.close();
//		System.out.println(str);
//	}
	
	
	@After
	public void after() {
		System.out.println("after TestExportVendorService");
	}
	
}
