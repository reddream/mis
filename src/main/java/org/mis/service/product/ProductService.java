package org.mis.service.product;

import java.util.List;

import org.common.page.Pagination;
import org.models.Product;
import org.models.product.ProductRequest;

public interface ProductService {
	public Product save(Product product);
	public Product get(Integer id);
	public Pagination search(ProductRequest preq,int userId,int pageNo, int pageSize);
	public List<Product> search(ProductRequest preq);
	public void deleteByIds(Integer[] ids);	
	public void recoverByIds(Integer[] ids);
}
