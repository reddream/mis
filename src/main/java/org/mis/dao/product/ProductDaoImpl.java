package org.mis.dao.product;

import org.hibernate.Query;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.common.hibernate3.Finder;
import org.common.hibernate3.HibernateBaseDao;
import org.common.page.Pagination;
import org.models.Brand;
import org.models.Model;
import org.models.OperatingSystem;
import org.models.Product;
import org.models.Vendor;
import org.models.constants.ShowType;
import org.models.product.ProductRequest;
import org.models.vendor.VendorRequest;
import org.springframework.stereotype.Repository;

@Repository
//@Transactional
public class ProductDaoImpl extends HibernateBaseDao<Product,Integer> implements ProductDao {

	@Override
	protected Class<Product> getEntityClass() {
		return Product.class;
	}

	@Override
	public Product save(Product product) {
		if(product.getId()==null || product.getId()==0){
			getSession().save(product);
			product.setMessage("product.success.message");
		}
		else{
			getSession().update(product);
			product.setMessage("product.updated.message");
		}
		return product;
	}

	@Override
	public boolean productExists(String codeStr) {
		Product p = new Product();
		Finder f= Finder.create("from Product where code=:mycode");
		f.setParam("mycode",codeStr);
		List<Product> products = find(f);
		return products.size()>0;	
	}

	private Finder generateProductListQuery(ProductRequest preq) {
		Finder f = Finder.create("select bean from Product bean where bean.id>0 ");
		if(StringUtils.isNotBlank(preq.getProductName())){
			f.append("and bean.name like '%"+preq.getProductName().replace("'", StringUtils.EMPTY)+"%'");
		}
		if(preq.getOsid() != null && preq.getOsid() > 0){
			f.append("and bean.osId  = "+preq.getOsid());
		}
		if(preq.getModelid() != null && preq.getModelid() > 0){
			f.append("and bean.modelId  = "+preq.getModelid());
		}
		if(preq.getBrandid() != null && preq.getBrandid() > 0){
			f.append("and bean.brandId  = "+preq.getBrandid());
		}
		if(preq.getVendorid() != null && preq.getVendorid() > 0){
			f.append("and bean.vendorId = " + preq.getVendorid());
		}
		if(StringUtils.isNotBlank(preq.getProductCode())){
			f.append("and bean.code like '%"+preq.getProductCode().replace("'", StringUtils.EMPTY)+"%'");
		}
		if(StringUtils.isNotBlank(preq.getProductFeatures())){
			f.append("and bean.features like '%"+preq.getProductFeatures().replace("'", StringUtils.EMPTY)+"%'");
		}
		switch (preq.getShowType()) {
		case ShowType.All:
			break;
		case ShowType.Valid:
			f.append("and bean.deleted  = 0");
			break;
		case ShowType.InValid:
			f.append("and bean.deleted  = 1");
			break;
		}
		if(StringUtils.isNotBlank(preq.getQueryOrderBy())){
			String[] orderBy = preq.getQueryOrderBy().split("\\.");
			String order;
			if(orderBy[1].equals("0")){
				order="asc";
			}else
				order = "desc";
			f.append(" order by bean."+orderBy[0]+" "+order);
		} else {
			f.append(" order by bean.updatedTime desc");
		}
		return f;
	}
	
	@Override
	public Pagination search(ProductRequest preq, int userId, int pageNo,
			int pageSize) {
		Finder f = generateProductListQuery(preq);
		Pagination p =  find(f, pageNo, pageSize);
		List<Product> products = (List<Product>) p.getList();
		for(Product product:products){
			destoryLazy(product);
		}	
		return p;
	}
	
	@Override
	public List<OperatingSystem> listOperatingSystems() {
		String hql= "from OperatingSystem";
		return find(hql);
	}

	@Override
	public List<Brand> listBrands() {
		String hql= "from Brand";
		return find(hql);
	}

	@Override
	public List<Model> listModels() {
		String hql= "from Model";
		return find(hql);
	}
	
	@Override
	public List<Vendor> listVendors(){
		String hql = "from Vendor";
		return find(hql);
	}
	
	private void destoryLazy(Product product) {
		product.getOs().getName();
		product.getBrand().getName();
		product.getModel().getName();
		product.getVendor().getName();
		product.getOperator().getName();	
	}

	@Override
	public List<Product> listProducts() {
		String hql = "from Product";
		Finder f = Finder.create(hql);
		List<Product> products = find(f);
		for(Product p:products){
			this.destoryLazy(p);
		}
		return products;
	}

	@Override
	public List<Product> search(ProductRequest preq) {
		Finder f = generateProductListQuery(preq);
		return find(f);
	}

	@Override
	public void deleteByIds(Integer[] ids) {
		if(ids!=null){
			String idsStr = convertIdsToString(ids);
			String hql = "update Product set deleted=1,updatedTime=:updatedTime where deleted=0 and id in ("+idsStr+")";
			Query query = getSession().createQuery(hql);
			query.setParameter("updatedTime", Calendar.getInstance());
			query.executeUpdate();
		}	
		
	}

	@Override
	public void recoverByIds(Integer[] ids) {
		if(ids!=null){
			String idsStr = convertIdsToString(ids);
			String hql = "update Product set deleted=0,updatedTime=:updatedTime where deleted=1 and id in ("+idsStr+")";
			Query query = getSession().createQuery(hql);
			query.setParameter("updatedTime", Calendar.getInstance());
			query.executeUpdate();
		}	
		
	}

	@Override
	public Product findById(Integer id) {
		return this.get(id);
	}

}
