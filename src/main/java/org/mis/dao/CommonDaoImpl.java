package org.mis.dao;

import java.util.List;

import org.common.hibernate3.Finder;
import org.common.hibernate3.HibernateSimpleDao;
import org.models.Area;
import org.models.Brand;
import org.models.Level;
import org.models.Model;
import org.models.OperatingSystem;
import org.models.PayCondition;
import org.models.Product;
import org.models.Role;
import org.models.Vendor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CommonDaoImpl extends HibernateSimpleDao implements CommonDao {

	@Override
	public List<OperatingSystem> listOperatingSystems() {		
		String hql= "from OperatingSystem";
		return this.find(hql);
	}

	@Override
	public List<Area> listAreas() {
		String hql= "from Area";
		return find(hql);
	}

	@Override
	public List<Level> listLevels() {
		String hql="from Level";
		return find(hql);
	}
	@Override
	public List<Role> listRole() {
		String hql="from Role";
		return find(hql);
	}

	@Override
	public List<Model> listModels() {
		String hql="from Model";
		return find(hql);
	}

	@Override
	public List<Brand> listBrands() {
		String hql="from Brand";
		return find(hql);
	}

	@Override
	public List<PayCondition> listPayConditions() {
		String hql="from PayCondition";
		return find(hql);
	}

	@Override
	public List<Model> listModelsByBrand(Integer brandId) {
		String hql="from Model where brandId=:brandId";
		Finder f = Finder.create(hql);
		f.setParam("brandId", brandId);
		return find(f);
	}

	@Override
	public List<Vendor> listActiveVendors() {
		String hql="from Vendor where inactive=0";
		Finder f = Finder.create(hql);
		return find(f);
	}


}
