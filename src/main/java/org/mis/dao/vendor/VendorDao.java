package org.mis.dao.vendor;

import java.util.List;

import org.common.page.Pagination;
import org.models.Vendor;
import org.models.vendor.VendorRequest;

public interface VendorDao {
	public Vendor save(Vendor vendor);
	public Vendor findById(Integer id);	
	
	public Pagination search(VendorRequest vreq,int userId,int pageNo, int pageSize);
	
	public List<Vendor> search(VendorRequest vreq);
	
	public Boolean isExist(String name);
	
	public Boolean exists(Integer id,String name);
	
	public void deleteByIds(Integer[] ids);
	
	public void recoverByIds(Integer[] ids);
}
