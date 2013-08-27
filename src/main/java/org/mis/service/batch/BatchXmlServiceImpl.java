package org.mis.service.batch;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import org.common.XPathUtils;
import org.exception.OrgRuntimeException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.models.batch.BatchParam;
import org.springframework.stereotype.Service;



@Service
public class BatchXmlServiceImpl extends BaseBatchServiceImpl implements BatchXmlService {
	
	@Override
	protected List<String[]> retriveProductList(InputStream is) throws Exception {
		List<String[]> productStr = new ArrayList<String[]>();
		List<Element> products = new ArrayList<Element>();
		String[] title = batchConf.getProductColumnNames();
		String[] rowName = batchConf.getProductColumnCodes();		
		productStr.add(title);
			SAXBuilder builder = new SAXBuilder();
			try{
				Document doc = builder.build(is);
				products = XPathUtils.selectNodes(doc,"//product");	
			}catch(Exception ex){
				throw new OrgRuntimeException("fileContentIsIncorrect");
			}
			
			if(products.isEmpty() || !products.get(0).getParentElement().isRootElement()){
				throw new OrgRuntimeException("fileContentIsIncorrect");
			}
			for(Element e:products){
				String[] row = new String[title.length];
				for(int i=0; i<title.length; i++) {
					row[i] = e.getChildTextTrim(rowName[i]);
				}
				for(int i=0; i<row.length; i++) {
					if(row[i]!="" && row[i]!= null) {
						productStr.add(row);
						break;
					}
				}
				
			}
		return productStr;
	}

	@Override
	protected List<String[]> retriveVendorList(InputStream is) throws Exception {
		List<String[]> vendorStr = new ArrayList<String[]>();
		List<Element> vendors =  new ArrayList<Element>();
		String[] title = batchConf.getVendorColumnNames();	
		String[] rowName = batchConf.getVendorColumnCodes();
		vendorStr.add(title);
		SAXBuilder builder = new SAXBuilder();
		try{
			Document doc = builder.build(is);
			vendors = XPathUtils.selectNodes(doc,"//Vendor");	
		}catch(Exception ex){
			throw new OrgRuntimeException("fileContentIsIncorrect");
		}
		if(vendors.isEmpty() || !vendors.get(0).getParentElement().isRootElement()){
			throw new OrgRuntimeException("fileContentIsIncorrect");
		}
		for(Element e:vendors){
			String[] row = new String[title.length];
			for(int i=0; i<title.length; i++) {
				row[i] = e.getChildTextTrim(rowName[i]);
			}
			for(int i=0; i<row.length; i++) {
				if(row[i]!="" && row[i]!= null) {
					vendorStr.add(row);
					break;
				}
			}
		}		
		return vendorStr;
	}
	
}
