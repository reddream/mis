package org.web.test;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mis.service.CommonService;
import org.mis.service.operator.OperatorService;
import org.mis.service.product.ProductService;
import org.mis.service.vendor.VendorService;
import org.models.Area;
import org.models.Brand;
import org.models.Level;
import org.models.Model;
import org.models.OperatingSystem;
import org.models.Operator;
import org.models.PayCondition;
import org.models.Product;
import org.models.Role;
import org.models.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
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
public class TestService {

	@Before
	public void prepareTestData() {
		System.out.println("before");
	}
	
	/** The auth mng. */
	@Autowired
	protected AuthMng authMng;
	
	@Autowired
	protected SessionProvider sessionProvider;
	
	@Autowired
	protected CommonService commonService;
	
	@Autowired		
	protected VendorService vendorService;
	
	@Autowired
	protected ProductService productService;
	
	@Autowired
	protected OperatorService operatorService;
	
	@Test
	public void testCommonService() throws Exception{
		List<Area> areas = commonService.listAreas();
		for(Area a:areas){
			Assert.assertTrue(StringUtils.isNotEmpty(a.getName()));
		}		
		List<Brand> brands = commonService.listBrands();
		for(Brand b:brands){
			Assert.assertTrue(StringUtils.isNotEmpty(b.getName()));
			List<Model> modelsOfBrand = commonService.listModelsByBrand(b.getId());
			for(Model m:modelsOfBrand){
				Assert.assertTrue(StringUtils.isNotEmpty(m.getName()));
			}
		}
		List<Level> levels = commonService.listLevels();
		for(Level l:levels){
			Assert.assertTrue(StringUtils.isNotEmpty(l.getName()));			
		}
		List<Model> models = commonService.listModels();
		for(Model m:models){
			Assert.assertTrue(StringUtils.isNotEmpty(m.getName()));
		}
		List<PayCondition> payments = commonService.listPayConditions();
		for(PayCondition pay:payments){
			Assert.assertTrue(StringUtils.isNotEmpty(pay.getName()));
		}
		List<OperatingSystem> systems = commonService.listOperatingSystems();
		for(OperatingSystem os:systems){
			Assert.assertTrue(StringUtils.isNotEmpty(os.getName()));
		}		
	}
	
	@Test
	public void testVendorService() throws Exception{
		System.out.println("testVendorService starts");
		Vendor vendor = vendorService.get(1);
		vendor.setName("苏宁成都总部1");
		vendorService.save(vendor);
		Vendor newVendor = new Vendor();
		newVendor.setAreaId(vendor.getAreaId());
		newVendor.setName(vendor.getName()+"New");
		newVendor.setCreatedTime(Calendar.getInstance());
		newVendor.setUpdatedTime(Calendar.getInstance());
		newVendor.setInactive(0);
		newVendor.setLevelId(vendor.getLevelId());
		newVendor.setParentKey(StringUtils.EMPTY);
		newVendor.setParentId(null);
		newVendor.setPayConditionId(vendor.getPayConditionId());
		newVendor.setUpdatedTime(Calendar.getInstance());
		newVendor.setOperatorId(vendor.getOperatorId());
		vendorService.save(newVendor);
		System.out.println("testVendorService ends");
	}
	
	@Test
	public void testProductService() throws Exception{
		System.out.println("testProductService starts");
		Product p = productService.get(1);
		p.setName("戴尔笔记本1235");
		productService.save(p);
		Product newProduct = new Product();
		newProduct.setBrandId(p.getBrandId());
		newProduct.setCode(String.format("%s",Calendar.getInstance().getTimeInMillis()));
		newProduct.setCreatedTime(Calendar.getInstance());
		newProduct.setDeleted(0);
		newProduct.setFeatures("My features");
		newProduct.setModelId(p.getModelId());
		newProduct.setName(p.getName()+Calendar.getInstance().getTimeInMillis());
		newProduct.setOperatorId(p.getOperatorId());
		newProduct.setOsId(p.getOsId());
		newProduct.setPrice(18.5);
		newProduct.setRemark("Some comments here");
		newProduct.setUpdatedTime(Calendar.getInstance());
		newProduct.setVendorId(p.getVendorId());
		productService.save(newProduct);
		System.out.println("testProductService ends");
	}
	
	@Test
	public void testOperatorService() throws Exception{
		System.out.println("testOperatorService starts");
		Operator user = operatorService.get(1);
		user.setName("reddream"+Calendar.getInstance().getTimeInMillis());
		operatorService.save(user);
		Operator operator = new Operator();
		operator.setName(user.getName());
		operator.setCreatedTime(Calendar.getInstance());
		operator.setUpdatedTime(Calendar.getInstance());
		operator.setInactive(0);
		operator.setPassword("123");
		operator.setLogin("reddream");
		HashSet<Role> set = new HashSet<Role>();
		Role role = new Role();
		role.setId(1);
		set.add(role);
		operator.setRoles(set);
		operatorService.save(operator);
		System.out.println("testOperatorService ends");
	}
	
	
	@After
	public void after() {
		System.out.println("after");
	}
}
