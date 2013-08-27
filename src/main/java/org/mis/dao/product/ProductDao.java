package org.mis.dao.product;

import java.util.List;

import org.common.page.Pagination;
import org.models.Brand;
import org.models.Model;
import org.models.OperatingSystem;
import org.models.Product;
import org.models.Vendor;
import org.models.product.ProductRequest;

public interface ProductDao {
	public Product save(Product product);
	public Product findById(Integer id);	
	public boolean productExists(String codeStr);
	List<OperatingSystem> listOperatingSystems();
	List<Brand> listBrands();
	List<Model> listModels();
	List<Vendor> listVendors();
	List<Product> listProducts();
	public Pagination search(ProductRequest preq,int userId,int pageNo, int pageSize);
	public List<Product> search(ProductRequest preq);
    public void deleteByIds(Integer[] ids);
	public void recoverByIds(Integer[] ids);
}
