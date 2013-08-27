package org.mis.service.batch;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.exception.OrgRuntimeException;
import org.mis.dao.CommonDao;
import org.mis.dao.product.ProductDao;
import org.mis.dao.vendor.VendorDao;
import org.models.ActionResult;
import org.models.Area;
import org.models.Brand;
import org.models.Level;
import org.models.Model;
import org.models.OperatingSystem;
import org.models.PayCondition;
import org.models.Product;
import org.models.Vendor;
import org.models.batch.BatchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.web.config.BatchConfiguration;
import org.web.message.MessageResolver;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public abstract class BaseBatchServiceImpl {

	@Autowired
	protected CommonDao commonDao;
	@Autowired
	protected VendorDao vendorDao;

	@Autowired
	protected BatchConfiguration batchConf;
	@Autowired
	protected ProductDao productDao;

	// Vendor
	protected final static int POS_AreaName = 0;
	protected final static int POS_LevelName = 1;
	protected final static int POS_PayConditionName = 2;
	protected final static int POS_VendorName = 3;
	// Product
	protected final static int POS_CODE = 0;
	protected final static int POS_NAME = 1;
	protected final static int POS_OSNAME = 2;
	protected final static int POS_MODELName = 3;
	protected final static int POS_VENDORNAME = 4;
	protected final static int POS_BrandName = 5;
	protected final static int POS_PRICE = 6;
	protected final static int POS_FEATURE = 7;
	protected final static int POS_REMARK = 8;

	public byte[] importProducts(InputStream is, BatchParam param)
			throws Exception {

		List<String[]> list = retriveProductList(is);
		if(list.size() > 101) {
			throw new OrgRuntimeException("一次最多导入100条数据");
		}
		if(list.size() <= 1){
			throw new OrgRuntimeException("导入为空数据文件,请检查");
		}
		validateProducts(list, param);
		String[] prod = list.get(0);
		if (prod.length == 9) {
			return importProducts(param, list);
		} else {
			return null;
		}
	}

	protected List<String[]> retriveProductList(InputStream is)
			throws Exception {
		Workbook wb = WorkbookFactory.create(is);
		Sheet sheet = wb.getSheetAt(0);
		List<String[]> list = new ArrayList<String[]>();
		int num = sheet.getLastRowNum();
		for (int i = 0; i <= num; i++) {
			Row r = sheet.getRow(i);
			//int cellNum = r.getLastCellNum();
			String arr[] = new String[9];
			for (int j = 0; j < 9; j++) {
				Cell c = r.getCell(j);
				String value = StringUtils.EMPTY;
				if (c != null && c.getCellType() == Cell.CELL_TYPE_STRING)
					value = c.getStringCellValue();
				else if (c != null && c.getCellType() == Cell.CELL_TYPE_NUMERIC)
					value = String.format("%s", c.getNumericCellValue());
				arr[j] = value;
			}
			for(int j=0; j<arr.length; j++) {
				if(arr[j]!="" && arr[j]!= null) {
					list.add(arr);
					break;
				}
			}
		}
		is.close();
		return list;
	}
	
	protected void validateProducts(List<String[]> list, BatchParam param) throws Exception{
		
	}

	protected List<String[]> retriveVendorList(InputStream is) throws Exception {
		Workbook wb = WorkbookFactory.create(is);
		Sheet sheet = wb.getSheetAt(0);
		List<String[]> list = new ArrayList<String[]>();
		int num = sheet.getLastRowNum();
		for (int i = 0; i <= num; i++) {
			Row r = sheet.getRow(i);
			//int cellNum = r.getLastCellNum();
			String arr[] = new String[4];
			for (int j = 0; j < 4; j++) {
				Cell c = r.getCell(j);
				String value = StringUtils.EMPTY;
				if (c!=null && c.getCellType() == Cell.CELL_TYPE_STRING)
					value = c.getStringCellValue();
				else if (c!=null && c.getCellType() == Cell.CELL_TYPE_NUMERIC)
					value = String.format("%s", c.getNumericCellValue());
				arr[j] = value;
			}
			for(int j=0; j<arr.length; j++) {
				if(arr[j]!="" && arr[j]!= null) {
					list.add(arr);
					break;
				}
			}
		}
		is.close();
		return list;
	}

	protected byte[] importProducts(BatchParam param, List<String[]> list)
			throws UnsupportedEncodingException, IOException {
		List<Model> modelsList = commonDao.listModels();
		List<Brand> brandsList = commonDao.listBrands();
		List<OperatingSystem> osList = commonDao.listOperatingSystems();
		List<Vendor> vendorList = commonDao.listActiveVendors();
		List<ActionResult> products = new ArrayList<ActionResult>();
		for (int i = 1; i < list.size(); i++) {
			String[] row = list.get(i);
			Product product = new Product();
			product.setOperatorId(param.getOperatorId());
			fillProduct(product, row, modelsList, brandsList, osList,
					vendorList);
			if (product.isOK()) {
				productDao.save(product);
				product.setMessage("import.success");
			}
			products.add(product);
		}

		String header[] = list.get(0);
		ByteArrayOutputStream baos = outputExcelOutputStream(header, param,
				list, products);
		return baos.toByteArray();
	}

	protected void fillProduct(Product product, String[] row,
			List<Model> models, List<Brand> brands, List<OperatingSystem> oSs,
			List<Vendor> vendors) {
		product.setOK(true);
		validateCode(product, row);
		if (!product.isOK())
			return;
		validateOS(product, row, oSs);
		if (!product.isOK())
			return;
		validateBrand(product, row, brands);
		if (!product.isOK())
			return;
		validateModel(product, row, models);
		if (!product.isOK())
			return;
		validateVendor(product, row, vendors);
		if (!product.isOK())
			return;
		validatePrice(product, row);
		if (!product.isOK())
			return;
		validateName(product, row);
		if (!product.isOK())
			return;
		valodateFeatures(product, row);
		if (!product.isOK())
			return;
		product.setRemark(row[POS_REMARK].trim());
		Calendar cal = Calendar.getInstance();
		product.setCreatedTime(cal);
		product.setUpdatedTime(cal);
		product.setDeleted(0);

	}
	private void valodateFeatures(Product product, String[] row){
		String features = row[POS_FEATURE].trim();
		if (StringUtils.isBlank(features)) {
			product.setOK(false);
			product.setMessage("import.productFeaturesNull");
			return;
		}else{
			product.setFeatures(features);
			return;
		}
	}
	
	private void validateName(Product product, String[] row){
		String name = row[POS_NAME].trim();
		if (StringUtils.isBlank(name)) {
			product.setOK(false);
			product.setMessage("import.productNameNull");
			return;
		}else{
			product.setName(name);
			return;
		}
	}

	private void validatePrice(Product product, String[] row) {
		String price = row[POS_PRICE].trim();
		if (StringUtils.isBlank(price)) {
			product.setOK(false);
			product.setMessage("import.productPriceNull");
			return;
		}
		Pattern pattern = Pattern
				.compile("^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
		Matcher matcher = pattern.matcher(price);
		if (matcher.find()) {
			product.setPrice(Double.parseDouble(matcher.group(1)));
			return;
		} else {
			product.setOK(false);
			product.setMessage("import.productPriceInValid");
		}
	}

	private void validateVendor(Product product, String[] row,
			List<Vendor> vendors) {
		String vendorName = row[POS_VENDORNAME].trim();
		if (StringUtils.isBlank(vendorName)) {
			product.setOK(false);
			product.setMessage("import.productVendorNameNull");
			return;
		}
		for (Vendor vendor : vendors) {
			if (vendor.getName().equals(vendorName)) {
				product.setVendorId(vendor.getId());
				return;
			}
		}
		if (product.getVendorId() == null) {
			product.setOK(false);
			product.setMessage("import.productVendorInValid");
		}
	}

	private void validateOS(Product product, String[] row,
			List<OperatingSystem> operatingSystems) {
		String osName = row[POS_OSNAME].trim();
		if (StringUtils.isBlank(osName)) {
			product.setOK(false);
			product.setMessage("import.productOSNameNull");
			return;
		}
		for (OperatingSystem os : operatingSystems) {
			if (os.getName().equals(osName)) {
				product.setOsId(os.getId());
				return;
			}
		}
		if (product.getOsId() == null) {
			product.setOK(false);
			product.setMessage("import.productOperatingSystemInValid");
		}
	}

	private void validateCode(Product product, String[] row) {
		String code = row[POS_CODE].trim();
		if (StringUtils.isBlank(code)) {
			product.setOK(false);
			product.setMessage("import.productCodeNull");
			return;
		}
		if (productDao.productExists(code)) {
			product.setOK(false);
			product.setMessage("import.productCodeInValid");
			return;
		} else {
			product.setCode(code);
		}
	}

	private void validateModel(Product product, String[] row, List<Model> models) {
		String modelName = row[POS_MODELName].trim();
		if (StringUtils.isBlank(modelName)) {
			product.setOK(false);
			product.setMessage("import.productModelNameNull");
			return;
		}
		for (Model model : models) {
			if (model.getName().equals(modelName)
					&& model.getBrandId().equals(product.getBrandId())) {
				product.setModelId(model.getId());
				return;
			}
		}
		if (product.getModelId() == null) {
			product.setOK(false);
			product.setMessage("import.productModelNameInValid");
		}
	}

	private void validateBrand(Product product, String[] row, List<Brand> brands) {
		String brandName = row[POS_BrandName].trim();
		if (StringUtils.isBlank(brandName)) {
			product.setOK(false);
			product.setMessage("import.productBrandNameNull");
			return;
		}
		for (Brand brand : brands) {
			if (brand.getName().equals(brandName)) {
				product.setBrandId(brand.getId());
				return;
			}
		}
		if (product.getBrandId() == null) {
			product.setOK(false);
			product.setMessage("import.productBrandNameInValid");
		}
	}

	// Vendors
	private void fillVendor(Vendor vendor, String[] row, List<Area> areas,
			List<PayCondition> payConditions, List<Level> levels) {
		vendor.setOK(true);
		validateArea(vendor, row, areas);
		if (!vendor.isOK())
			return;
		validateLevel(vendor, row, levels);
		if (!vendor.isOK())
			return;
		validatepayCondition(vendor, row, payConditions);
		if (!vendor.isOK())
			return;
		validateVendorName(vendor, row);
		if (!vendor.isOK())
			return;
		Calendar cal = Calendar.getInstance();
		vendor.setCreatedTime(cal);
		vendor.setUpdatedTime(cal);
		vendor.setInactive(0);

	}

	private void validateArea(Vendor vendor, String[] row, List<Area> areas) {
		String areaName = row[POS_AreaName];
		if (StringUtils.isBlank(areaName)) {
			vendor.setOK(false);
			vendor.setMessage("import.vendorAreaNameNull");
			return;
		}
		for (Area area : areas) {
			if (area.getName().equals(areaName)) {
				vendor.setAreaId(area.getId());
				return;
			}
		}
		if (vendor.getAreaId() == null) {
			vendor.setOK(false);
			vendor.setMessage("import.vendorAreaNameInValid");
		}
	}

	private void validateLevel(Vendor vendor, String[] row, List<Level> levels) {
		String levelName = row[POS_LevelName];
		if (StringUtils.isBlank(levelName)) {
			vendor.setOK(false);
			vendor.setMessage("import.vendorLevelNameNull");
			return;
		}
		for (Level level : levels) {
			if (level.getName().equals(levelName)) {
				vendor.setLevelId(level.getId());
				return;
			}
		}
		if (vendor.getLevelId() == null) {
			vendor.setOK(false);
			vendor.setMessage("import.vendorLevelNameInValid");
		}
	}

	private void validatepayCondition(Vendor vendor, String[] row,
			List<PayCondition> payConditions) {
		String payConditionName = row[POS_PayConditionName];
		if (StringUtils.isBlank(payConditionName)) {
			vendor.setOK(false);
			vendor.setMessage("import.vendorPayConditionNameNull");
			return;
		}
		for (PayCondition payCondition : payConditions) {
			if (payCondition.getName().equals(payConditionName)) {
				vendor.setPayConditionId(payCondition.getId());
				return;
			}
		}
		if (vendor.getPayConditionId() == null) {
			vendor.setOK(false);
			vendor.setMessage("import.vendorPayConditionNameInValid");
		}
	}

	private void validateVendorName(Vendor vendor, String[] row) {

		String vendorName = row[POS_VendorName];
		if (vendorDao.isExist(vendorName)) {
			vendor.setOK(false);
			vendor.setMessage("import.vendorNameAlreadyExist");
		}
		if (StringUtils.isBlank(vendorName)) {
			vendor.setOK(false);
			vendor.setMessage("import.vendorNameNull");
			return;
		} else {
			vendor.setName(vendorName);
		}
		if (vendor.getName() == null) {
			vendor.setOK(false);
			vendor.setMessage("import.vendorNameInValid");
		}
	}

	public byte[] importVendors(InputStream is, BatchParam param)
			throws Exception {
		List<String[]> list = retriveVendorList(is);
		if(list.size() > 101) {
			throw new OrgRuntimeException("一次最多导入100条数据");
		}
		if(list.size() <= 1){
			throw new OrgRuntimeException("导入为空数据文件,请检查");
		}
		validateVendors(list,param);
		String[] vend = list.get(1);
		if (vend.length == 4) {
			return importVendors(param, list);
		} else {
			return null;
		}
	}
	
	protected void validateVendors(List<String[]> list,BatchParam param ) throws Exception{
		
	}

	protected byte[] importVendors(BatchParam param, List<String[]> list)
			throws UnsupportedEncodingException, IOException {
		List<Area> areasList = commonDao.listAreas();
		List<Level> levelsList = commonDao.listLevels();
		List<PayCondition> payConditionsList = commonDao.listPayConditions();

		List<ActionResult> vendors = new ArrayList<ActionResult>();
		for (int i = 1; i < list.size(); i++) {
			String[] row = list.get(i);
			Vendor vendor = new Vendor();
			vendor.setOperatorId(param.getOperatorId());
			fillVendor(vendor, row, areasList, payConditionsList, levelsList);
			if (vendor.isOK()) {
				vendorDao.save(vendor);
				vendor.setMessage("import.success");
			}
			vendors.add(vendor);
		}
		String[] header = list.get(0);
		ByteArrayOutputStream baos = outputExcelOutputStream(header, param,
				list, vendors);
		return baos.toByteArray();
	}

	protected ByteArrayOutputStream outputExcelOutputStream(String header[],
			BatchParam param, List<String[]> list, List<ActionResult> ars)
			throws UnsupportedEncodingException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String newHeader[] = new String[header.length + 1];
		header = this.getLanOfArray(header, param);
		System.arraycopy(header, 0, newHeader, 0, header.length);
		newHeader[header.length] = getMessage(param, "import.status");
		List<String[]> newList = new ArrayList<String[]>();
		newList.add(newHeader);
		for (int i = 1; i < list.size(); i++) {
			String[] dataStr = list.get(i);
			String message = ars.get(i - 1).getMessage();
			String[] pStr = new String[dataStr.length + 1];
			System.arraycopy(dataStr, 0, pStr, 0, dataStr.length);
			pStr[dataStr.length] = getMessage(param, message);
			newList.add(pStr);
		}

		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet();
		Row row = null;
		Cell cell = null;
		for (int i = 0; i < newList.size(); i++) {
			String[] data = newList.get(i);
			row = sheet.createRow(i);
			for (int j = 0; j < data.length; j++) {
				cell = row.createCell(j);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(data[j]);
			}
		}
		wb.write(baos);
		baos.flush();
		baos.close();
		return baos;
	}

	protected String getMessage(BatchParam param, String code)
			throws UnsupportedEncodingException {
		try {
			return MessageResolver.getMessage(param.getRequest(), code);
		} catch (Exception ex) {
			return code;
		}
	}

	protected ByteArrayOutputStream outputExcelOutputStream(BatchParam param, List<String[]> list)
			throws UnsupportedEncodingException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet();
		Row row = null;
		Cell cell = null;
		for (int i = 0; i < list.size(); i++) {
			String[] data = list.get(i);
			row = sheet.createRow(i);
			for (int j = 0; j < data.length; j++) {
				cell = row.createCell(j);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(data[j]);
			}
		}
		wb.write(baos);
		baos.flush();
		baos.close();
		return baos;
	}
	
	public String[] getLanOfArray(String array[],BatchParam param) throws UnsupportedEncodingException{
		String[] arr=new String[array.length];
		for(int i=0;i<array.length;i++){
			arr[i]=getMessage(param,array[i]);
		}
		return arr;
	}
	
	
	
}
