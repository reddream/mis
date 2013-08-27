package org.mis.service.batch;

import java.io.InputStream;

import org.models.batch.BatchParam;

public interface BatchService {

	public byte[] importProducts(InputStream is,BatchParam param) throws Exception;	
	public byte[] importVendors(InputStream is,BatchParam param) throws Exception;
}
