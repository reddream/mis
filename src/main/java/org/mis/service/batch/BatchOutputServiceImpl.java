package org.mis.service.batch;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.common.StringToOutputStream;
import org.jdom2.CDATA;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.mis.dao.CommonDao;
import org.models.Product;
import org.models.Vendor;
import org.models.batch.BatchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web.config.BatchConfiguration;
import org.web.config.ProductConfiguration;
import org.web.config.VendorConfiguration;

import au.com.bytecode.opencsv.CSVWriter;

@Service
public class BatchOutputServiceImpl extends BaseBatchServiceImpl implements BatchOutputService {

	@Autowired
	protected CommonDao commonDao;
	
	@Autowired
	private VendorConfiguration vendorConfiguration;
	
	@Autowired
	private ProductConfiguration productConfiguration;
	
	@Autowired
	private BatchConfiguration batchConfiguration;
	
	private final static int POS_AreaName=0;
	private final static int POS_LevelName=1;
	private final static int POS_PayConditionName=2;
	private final static int POS_VendorName=3;
	private final static int POS_OperatorName=4;
	private final static int POS_CreateTime=5;
	private final static int POS_UpdateTime=6;
	
	private final static int PRO_CodeName=0;
	private final static int PRO_ProductName=1;
	private final static int PRO_OsName=2;
	private final static int PRO_ModelName=3;
	private final static int PRO_VendorName=4;
	private final static int PRO_BrandName=5;
	private final static int PRO_Price=6;
	private final static int PRO_Feature=7;
	private final static int PRO_Remark=8;
	private final static int PRO_CreateTime=9;
	private final static int PRO_UpdateTime=10;
	
	 
	
	@Override
	public byte[] exportVendorsExcel(List<Vendor> vendorList,BatchParam param) throws Exception {
		List<String[]> newList = new ArrayList<String[]>();
		String arrBatch[] = batchConfiguration.getVendorColumnNames();
		String arrVendor[] = vendorConfiguration.getVendorColumnNames();
		String header[] = new String[arrBatch.length + arrVendor.length];
		System.arraycopy(arrBatch, 0, header, 0, arrBatch.length);
		System.arraycopy(arrVendor, 0, header, arrBatch.length,arrVendor.length);
		String newHeader[] = this.getLanOfArray(header, param);
		newList.add(newHeader);
		for(int i=0; i < vendorList.size(); i++){
			Vendor v = vendorList.get(i);
			String data[] = new String[newHeader.length];
			data[POS_AreaName] = v.getArea().getName();
			data[POS_LevelName] = v.getLevel().getName();
			data[POS_PayConditionName] = v.getPayCondition().getName();
			data[POS_VendorName] =v.getName();
			data[POS_OperatorName] = v.getOperator().getName();
			data[POS_CreateTime] = DateFormatUtils.ISO_DATE_FORMAT.format(v.getCreatedTime().getTime()) + " " + DateFormatUtils.ISO_TIME_NO_T_FORMAT.format(v.getCreatedTime().getTime());
			data[POS_UpdateTime] = DateFormatUtils.ISO_DATE_FORMAT.format(v.getUpdatedTime().getTime()) + " " + DateFormatUtils.ISO_TIME_NO_T_FORMAT.format(v.getUpdatedTime().getTime());
			newList.add(data);
			
		}		
		ByteArrayOutputStream baos = this.outputExcelOutputStream(param, newList);
		return baos.toByteArray();		
	}

	@Override
	public byte[] exportVendorsXML(List<Vendor> vendorList,BatchParam param) throws Exception{
		Element root = new Element("Vendors");
		Document document = new Document(root);
		for(int i = 0; i< vendorList.size(); i++){
			Element eVendor = new Element("Vendor");
			root.addContent(eVendor);
			Vendor v = vendorList.get(i);
			Element area =new Element("Area");
			CDATA cdArea=new CDATA(v.getArea().getName());
			area.addContent(cdArea);
			eVendor.addContent(area);
			Element level =new Element("Level");
			CDATA cdLevel=new CDATA(v.getLevel().getName());
			level.addContent(cdLevel);
			eVendor.addContent(level);
			Element payCondition =new Element("PayCondition");
			CDATA cdPayCondition=new CDATA(v.getPayCondition().getName());
			payCondition.addContent(cdPayCondition);
			eVendor.addContent(payCondition);
			Element name =new Element("Name");
			CDATA cdName=new CDATA(v.getName());
			name.addContent(cdName);
			eVendor.addContent(name);
			Element operator =new Element("Operator");
			CDATA cdOperator=new CDATA(v.getOperator().getName());
			operator.addContent(cdOperator);
			eVendor.addContent(operator);
			Element createdTime =new Element("CreatedTime");
			String cTime = DateFormatUtils.ISO_DATE_FORMAT.format(v.getCreatedTime().getTime()) + " " + DateFormatUtils.ISO_TIME_NO_T_FORMAT.format(v.getCreatedTime().getTime());
			CDATA cdCreatedTime=new CDATA(cTime);
			createdTime.addContent(cdCreatedTime);
			eVendor.addContent(createdTime);
			Element updatedTime =new Element("UpdatedTime");
			String uTime = DateFormatUtils.ISO_DATE_FORMAT.format(v.getUpdatedTime().getTime()) + " " + DateFormatUtils.ISO_TIME_NO_T_FORMAT.format(v.getUpdatedTime().getTime());
			CDATA cdUpdatedTime=new CDATA(uTime);
			updatedTime.addContent(cdUpdatedTime);
			eVendor.addContent(updatedTime);
		}
		XMLOutputter XMLOut = new XMLOutputter();
		Format format = Format.getPrettyFormat();
		format.setEncoding(param.getCharset());
		format.setIndent(" "); 
		XMLOut.setFormat(format);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XMLOut.output(document, baos);
		return baos.toByteArray();
	}

	@Override
	public byte[] exportProductsExcel(List<Product> productList, BatchParam param)
			throws Exception {
		List<String[]> newList = new ArrayList<String[]>();
		String arrBatch[] = batchConfiguration.getProductColumnNames();
		String arrProduct[] = productConfiguration.getProductColumnNames();
		String header[] = new String[arrBatch.length + arrProduct.length];
		System.arraycopy(arrBatch, 0, header, 0, arrBatch.length);
		System.arraycopy(arrProduct, 0, header, arrBatch.length,arrProduct.length);
		String newHeader[] = this.getLanOfArray(header, param);
		newList.add(newHeader);
		for(int i=0; i < productList.size(); i++){
			Product p = productList.get(i);
			String data[] = new String[header.length];
			data[PRO_CodeName] = p.getCode();
			data[PRO_ProductName] = p.getName();
			data[PRO_OsName] = p.getOs().getName();
			data[PRO_ModelName] =p.getModel().getName();
			data[PRO_VendorName] = p.getVendor().getName();
			data[PRO_BrandName] = p.getBrand().getName();
			data[PRO_Price] = String.valueOf(p.getPrice());
			data[PRO_Feature] = p.getFeatures();
			data[PRO_Remark] = p.getRemark();
			data[PRO_CreateTime] = DateFormatUtils.ISO_DATE_FORMAT.format(p.getCreatedTime().getTime()) + " " + DateFormatUtils.ISO_TIME_NO_T_FORMAT.format(p.getCreatedTime().getTime());
			data[PRO_UpdateTime] = DateFormatUtils.ISO_DATE_FORMAT.format(p.getUpdatedTime().getTime()) + " " + DateFormatUtils.ISO_TIME_NO_T_FORMAT.format(p.getUpdatedTime().getTime());
			newList.add(data);
			
		}		
		ByteArrayOutputStream baos = this.outputExcelOutputStream(param,newList);
		return baos.toByteArray();
	}

	@Override
	public byte[] exportProductsXML(List<Product> productList, BatchParam param)
			throws Exception {
		Element root = new Element("Products");
		Document document = new Document(root);
		for(int i = 0; i< productList.size(); i++){
			Element eProduct = new Element("product");
			root.addContent(eProduct);
			Product p = productList.get(i);
			Element code =new Element("Code");
			CDATA cdCode=new CDATA(p.getCode());
			code.addContent(cdCode);
			eProduct.addContent(code);
			Element productName =new Element("Name");
			CDATA cdProductName=new CDATA(p.getName());
			productName.addContent(cdProductName);
			eProduct.addContent(productName);
			Element os =new Element("OperatingSystem");
			CDATA cdOs=new CDATA(p.getOs().getName());
			os.addContent(cdOs);
			eProduct.addContent(os);
			Element model =new Element("Model");
			CDATA cdModel=new CDATA(p.getModel().getName());
			model.addContent(cdModel);
			eProduct.addContent(model);
			Element vendorName =new Element("Vendor");
			CDATA cdVendorName=new CDATA(p.getVendor().getName());
			vendorName.addContent(cdVendorName);
			eProduct.addContent(vendorName);
			Element brand =new Element("Brand");
			CDATA cdBrand=new CDATA(p.getBrand().getName());
			brand.addContent(cdBrand);
			eProduct.addContent(brand);
			Element price =new Element("Price");
			CDATA cdPrice=new CDATA(String.valueOf(p.getPrice()));
			price.addContent(cdPrice);
			eProduct.addContent(price);
			Element feature =new Element("Feature");
			CDATA cdFeature=new CDATA(p.getFeatures());
			feature.addContent(cdFeature);
			eProduct.addContent(feature);
			Element remark =new Element("Remark");
			CDATA cdRemark=new CDATA(p.getRemark());
			remark.addContent(cdRemark);
			eProduct.addContent(remark);
			Element createdTime =new Element("CreatedTime");
			String cTime = DateFormatUtils.ISO_DATE_FORMAT.format(p.getCreatedTime().getTime()) + " " + DateFormatUtils.ISO_TIME_NO_T_FORMAT.format(p.getCreatedTime().getTime());
			CDATA cdCreatedTime=new CDATA(cTime);
			createdTime.addContent(cdCreatedTime);
			eProduct.addContent(createdTime);
			Element updatedTime =new Element("UpdatedTime");
			String uTime = DateFormatUtils.ISO_DATE_FORMAT.format(p.getUpdatedTime().getTime()) + " " + DateFormatUtils.ISO_TIME_NO_T_FORMAT.format(p.getUpdatedTime().getTime());
			CDATA cdUpdatedTime=new CDATA(uTime);
			updatedTime.addContent(cdUpdatedTime);
			eProduct.addContent(updatedTime);
		}
		XMLOutputter XMLOut = new XMLOutputter();
		Format format = Format.getPrettyFormat();
		format.setEncoding(param.getCharset());
		format.setIndent(" "); 
		XMLOut.setFormat(format);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XMLOut.output(document, baos);
		return baos.toByteArray();
	}

}
