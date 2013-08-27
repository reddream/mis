package org.mis.service.batch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.exception.OrgRuntimeException;
import org.models.batch.BatchParam;
import org.springframework.stereotype.Service;


@Service
public class BatchServiceImpl extends BaseBatchServiceImpl implements BatchService {
	
	@Override
	protected void validateVendors(List<String[]> list, BatchParam param) throws Exception{
		String[] title = list.get(0);
		String[] names = this.getLanOfArray(batchConf.getVendorColumnNames(),param);
		for(int i=0; i<names.length; i++) {
			if(!names[i].equals(title[i])){
				 throw new OrgRuntimeException("fileContentIsIncorrect");
				//throw new OrgRuntimeException("文件表头不正确");
			}
		}
	}
	
	@Override
	protected void validateProducts(List<String[]> list, BatchParam param) throws Exception{
		String[] title = list.get(0);
		String[] names = this.getLanOfArray(batchConf.getProductColumnNames(),param);
		for(int i=0; i<names.length; i++) {
			String s = names[i];
			if(!names[i].equals(title[i])){
				 throw new OrgRuntimeException("fileContentIsIncorrect");
				 //throw new OrgRuntimeException("文件表头不正确");
			}
		}
	}
}
