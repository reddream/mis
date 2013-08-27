package org.mis.dao.vendor;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.common.hibernate3.Finder;
import org.common.hibernate3.HibernateBaseDao;
import org.common.page.Pagination;
import org.hibernate.Query;
import org.models.Vendor;
import org.models.constants.ShowType;
import org.models.vendor.VendorRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class VendorDaoImpl extends HibernateBaseDao<Vendor,Integer> implements VendorDao {

	@Override
	protected Class<Vendor> getEntityClass() {
		return Vendor.class;
	}

	@Override
	public Vendor save(Vendor vendor) {
		if(vendor.getId()==null || vendor.getId()==0){
			getSession().save(vendor);
			vendor.setMessage("vendor.success.message");
		}
		else{
			getSession().update(vendor);
			vendor.setMessage("vendor.updated.message");
		}
		return vendor;
	}

	@Override
	public Pagination search(VendorRequest vreq, int userId, int pageNo,
			int pageSize) {
		Finder f = generateVendorListQuery(vreq);
		Pagination p =  find(f, pageNo, pageSize);
		List<Vendor> vendors = (List<Vendor>) p.getList();
		for(Vendor vendor:vendors){
			destoryLazy(vendor);
		}	
		return p;
	}
	
	public List<Vendor> search(VendorRequest vreq){
		Finder f = generateVendorListQuery(vreq);
		return find(f);
	}

	private Finder generateVendorListQuery(VendorRequest vreq) {
		Finder f = Finder.create("select bean from Vendor bean where bean.id>0 ");
		if(StringUtils.isNotBlank(vreq.getVendorName())){
			f.append("and bean.name like '%"+vreq.getVendorName().replace("'", StringUtils.EMPTY)+"%'");
		}
		if(vreq.getAreaid() != null && vreq.getAreaid() > 0){
			f.append("and bean.areaId  = "+vreq.getAreaid());
		}
		if(vreq.getLevelid() != null && vreq.getLevelid() > 0){
			f.append("and bean.levelId  = "+vreq.getLevelid());
		}
		if(vreq.getPayConditionid() != null && vreq.getPayConditionid() > 0){
			f.append("and bean.payConditionId  = "+vreq.getPayConditionid());
		}
		if (vreq.getShowType() != null) {
			switch (vreq.getShowType()) {
			case ShowType.All:
				break;
			case ShowType.Valid:
				f.append("and bean.inactive  = 0");
				break;
			case ShowType.InValid:
				f.append("and bean.inactive  = 1");
				break;
			}
		}
		if(StringUtils.isNotBlank(vreq.getQueryOrderBy())){
			String[] orderBy = vreq.getQueryOrderBy().split("\\.");
			String order;
			if(orderBy[1].equals("0")){
				order="asc";
			}else
				order = "desc";
			f.append(" order by bean."+orderBy[0]+" "+order);
		}else{
			f.append(" order by bean.updatedTime desc");
		}
		return f;
	}
	
	@Override
	public Boolean isExist(String name){
		Finder f = Finder.create("from Vendor where name=:vendorname ");
		f.setParam("vendorname", name);
		return find(f).size() > 0 ;
	}
	
	private void destoryLazy(Vendor vendor) {
		vendor.getArea().getName();
		vendor.getLevel().getName();
		vendor.getPayCondition().getName();
		vendor.getOperator().getName();
	}

	@Override
	public void deleteByIds(Integer[] ids) {	
		if(ids!=null){
			String idsStr = convertIdsToString(ids);
			String hql = "update Vendor set inactive=1,updatedTime=:updatedTime where inactive=0 and id in ("+idsStr+")";
			Query query = getSession().createQuery(hql);
			query.setParameter("updatedTime", Calendar.getInstance());
			query.executeUpdate();
		}	
	}

	@Override
	public void recoverByIds(Integer[] ids) {
		if(ids!=null){
			String idsStr = convertIdsToString(ids);
			String hql = "update Vendor set inactive=0,updatedTime=:updatedTime where inactive=1 and id in ("+idsStr+")";
			Query query = getSession().createQuery(hql);
			query.setParameter("updatedTime", Calendar.getInstance());
			query.executeUpdate();
		}			
	}

	@Override
	public Vendor findById(Integer id) {
		return this.get(id);
	}

	@Override
	public Boolean exists(Integer id, String name) {		
		Finder f = Finder.create("from Vendor where name=:vendorname and id <>:vendorid");
		f.setParam("vendorname", name);
		f.setParam("vendorid", id);
		return find(f).size() > 0 ;
	}
}
