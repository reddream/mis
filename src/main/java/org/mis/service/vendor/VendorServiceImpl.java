package org.mis.service.vendor;


import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.common.page.Pagination;
import org.mis.dao.vendor.VendorDao;
import org.models.Vendor;
import org.models.vendor.VendorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mis.service.BaseServiceImpl;

@Service
public class VendorServiceImpl extends BaseServiceImpl implements VendorService {

	@Autowired
	private VendorDao vendorDao;
	
	@Override
	public Vendor save(Vendor vendor) {
		if(vendor.getId()==null){
			vendor.setCreatedTime(Calendar.getInstance());
		}
		vendor.setUpdatedTime(Calendar.getInstance());
		if(StringUtils.isBlank(vendor.getName())){
			vendor.setMessage("vendor.name.message");
			return vendor;
		}
		if(vendor.getAreaId()==null){
			vendor.setMessage("vendor.area.message");
			return vendor;
		}
		if(vendor.getPayConditionId() == null){
			vendor.setMessage("vendor.payCondition.message");
			return vendor;
		}
		if(vendor.getLevelId() == null){
			vendor.setMessage("vendor.level.message");
			return vendor;
		}
		if(vendor.getId() == null && vendorDao.isExist(vendor.getName())){
			vendor.setMessage("vendor.uniqueName.message");
			return vendor;
		}
		
		if(vendor.getId() != null && vendorDao.exists(vendor.getId(), vendor.getName())){
			vendor.setMessage("vendor.uniqueName.message");
			return vendor;
		}
		
		Vendor v = vendorDao.save(vendor);
		return v;
	}

	@Override
	public Vendor get(Integer id) {
		return vendorDao.findById(id);
	}

	@Override
	public Pagination search(VendorRequest vreq, int userId, int pageNo,
			int pageSize) {
		Pagination p = vendorDao.search(vreq, userId, pageNo, pageSize);
		return p;
	}

	@Override
	public List<Vendor> search(VendorRequest vreq) {
		List<Vendor> vendors = vendorDao.search(vreq);
		return vendors;
	}

	@Override
	public void deleteByIds(Integer[] ids) {
		vendorDao.deleteByIds(ids);
	}

	@Override
	public void recoverByIds(Integer[] ids) {
		vendorDao.recoverByIds(ids);	
	}
}
