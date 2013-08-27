package org.mis.service.vendor;

import java.util.List;

import org.common.page.Pagination;
import org.models.Vendor;
import org.models.vendor.VendorRequest;

public interface VendorService {
	public Vendor save(Vendor vendor);
	public Vendor get(Integer id);	
	public Pagination search(VendorRequest vreq,int userId,int pageNo, int pageSize);
	public List<Vendor> search(VendorRequest vreq);
	public void deleteByIds(Integer[] ids);	
	public void recoverByIds(Integer[] ids);
}
