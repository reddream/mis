package org.mis.service;

import java.util.List;

import org.models.Area;
import org.models.Brand;
import org.models.Level;
import org.models.Model;
import org.models.OperatingSystem;
import org.models.PayCondition;
import org.models.Role;
import org.models.Vendor;

public interface CommonService {
	List<OperatingSystem> listOperatingSystems();
	List<Area> listAreas();
	List<Level> listLevels();
	List<Model> listModels();
	List<Brand> listBrands();
	List<PayCondition> listPayConditions();
	List<Model> listModelsByBrand(Integer brandId);
	List<Vendor> listActiveVendors();
	List<Role> listRole();
}
