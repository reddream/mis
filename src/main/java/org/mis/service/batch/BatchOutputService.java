package org.mis.service.batch;

import java.util.List;

import org.models.Product;
import org.models.Vendor;
import org.models.batch.BatchParam;

public interface BatchOutputService {
	public byte[] exportVendorsExcel(List<Vendor> vendorList,BatchParam param) throws Exception;
	public byte[] exportVendorsXML(List<Vendor> vendorList,BatchParam param)throws Exception;
	public byte[] exportProductsExcel(List<Product> productList,BatchParam param) throws Exception;
	public byte[] exportProductsXML(List<Product> productList,BatchParam param)throws Exception;
}
