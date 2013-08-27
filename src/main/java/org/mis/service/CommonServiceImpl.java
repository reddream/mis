package org.mis.service;

import java.util.List;

import org.mis.dao.CommonDao;
import org.models.Area;
import org.models.Brand;
import org.models.Level;
import org.models.Model;
import org.models.OperatingSystem;
import org.models.PayCondition;
import org.models.Role;
import org.models.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl extends BaseServiceImpl implements CommonService {

	@Autowired
	private CommonDao commonDao;
	
	@Override
	public List<OperatingSystem> listOperatingSystems() {
		return commonDao.listOperatingSystems();
	}

	@Override
	public List<Area> listAreas() {
		return commonDao.listAreas();
	}

	@Override
	public List<Level> listLevels() {
		return commonDao.listLevels();
	}

	@Override
	public List<Model> listModels() {
		return commonDao.listModels();
	}

	@Override
	public List<Brand> listBrands() {
		return commonDao.listBrands();
	}

	@Override
	public List<PayCondition> listPayConditions() {
		return commonDao.listPayConditions();
	}

	@Override
	public List<Model> listModelsByBrand(Integer brandId) {
		return commonDao.listModelsByBrand(brandId);
	}

	@Override
	public List<Vendor> listActiveVendors() {
		return commonDao.listActiveVendors();
	}

	@Override
	public List<Role> listRole() {
		return commonDao.listRole();
	}

}
