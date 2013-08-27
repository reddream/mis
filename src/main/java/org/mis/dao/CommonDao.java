package org.mis.dao;

import java.util.List;

import org.models.*;
public interface CommonDao {
	List<OperatingSystem> listOperatingSystems();
	List<Area> listAreas();
	List<Level> listLevels();
	List<Model> listModels();
	List<Model> listModelsByBrand(Integer brandId);
	List<Brand> listBrands();
	List<PayCondition> listPayConditions();
	List<Vendor> listActiveVendors();
	List<Role> listRole();
}
