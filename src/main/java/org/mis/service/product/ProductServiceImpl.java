package org.mis.service.product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.common.page.Pagination;
import org.mis.dao.product.ProductDao;
import org.models.Product;
import org.models.Vendor;
import org.models.product.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mis.service.BaseServiceImpl;

@Service
public class ProductServiceImpl extends BaseServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	private static final int DEFAULT_SCALE = 3;//默认保留三位  
	private static final int MAX_SCALE = 9;//最大保留9位  
	private static final String INTEGER_BIT_NOT_ZERO = "(0([.](0+|[0-9]{1,3}))?)|(-?[1-9][0-9]*([.][0-9]+)?)";//不包括整数位为0,小数位超过三位的正则  
	private static final String INTEGER_BIT_IS_ZERO = "0[.]0*[1-9][0-9]{0,2}";//获取需要保留小数位的精度的正则  
		
	@Override
	public Product save(Product product) {		
			if(product.getId()==null){
				product.setCreatedTime(Calendar.getInstance());
			}
			product.setUpdatedTime(Calendar.getInstance());
			if(StringUtils.isBlank(product.getName())){
				product.setMessage("product.name.message");
				return product;
			}
			if(StringUtils.isBlank(product.getCode())){
				product.setMessage("product.code.message");
				return product;
			}
			if(product.getBrandId() == null){
				product.setMessage("product.brand.message");
				return product;
			}
			if(product.getModelId() == null){
				product.setMessage("product.model.message");
				return product;
			}
			if(product.getOsId() == null){
				product.setMessage("product.os.message");
				return product;
			}
			if(product.getVendorId() == null){
				product.setMessage("product.vendor.message");
				return product;
			}
			if(StringUtils.isBlank(String.valueOf(product.getPrice()))){
				product.setMessage("product.price.message");
				return product;
			}
			if(checkScale(product.getPrice())){
				product.setMessage("product.price.process");
				return product;
			}
			
			if(StringUtils.isBlank(product.getFeatures())){
				product.setMessage("product.feature.message");
				return product;
			}
			if(product.getId() == null && productDao.productExists(product.getCode())){
				product.setMessage("product.uniqueCode.message");
				return product;
			}
			Product p = productDao.save(product);
			return p;
	}

	@Override
	public Product get(Integer id) {
		return productDao.findById(id);
	}
	
	@Override
	public Pagination search(ProductRequest preq, int userId, int pageNo,
			int pageSize) {
		Pagination p = productDao.search(preq, userId, pageNo, pageSize);
		return p;
	}

	@Override
	public List<Product> search(ProductRequest preq) {
		return productDao.search(preq);
	}

	@Override
	public void deleteByIds(Integer[] ids) {
		productDao.deleteByIds(ids);
		
	}

	@Override
	public void recoverByIds(Integer[] ids) {
		productDao.recoverByIds(ids);
	}

	private boolean checkScale(double data) {
		String str = Double.toString(data);
		BigDecimal num = new BigDecimal(str);
		if (num.scale() > 2) {
			return true;
		}
		return false;
	}
	
}
