package org.mis.controller.product;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.common.CookieUtils;
import org.common.page.Pagination;
import org.mis.controller.BaseController;
import org.mis.service.CommonService;
import org.mis.service.batch.BatchOutputService;
import org.mis.service.file.FileService;
import org.mis.service.product.ProductService;
import org.models.Area;
import org.models.Brand;
import org.models.Item;
import org.models.Level;
import org.models.Model;
import org.models.OperatingSystem;
import org.models.PayCondition;
import org.models.Product;
import org.models.Vendor;
import org.models.batch.BatchParam;
import org.models.jsonconverter.ItemConvertUtils;
import org.models.product.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web.RequestUtils;
import org.web.config.ProductConfiguration;

@Controller
@RequestMapping("/admin/product")
public class ProductController extends BaseController {
	private static final String path = "product/";

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductConfiguration productConfiguration;

	@Autowired
	private BatchOutputService batchOutputService;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private FileService fileService;

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return path + "main";
	}

	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String left(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return path + "left";
	}

	@RequestMapping(value = "/right", method = RequestMethod.GET)
	public String right(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return path + "right";
	}

	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public String showExportExcel(ProductRequest preq,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		if (preq == null)
			preq = new ProductRequest();
		List<Product> productList = productService.search(preq);
		if(productList.size() <= 0){
			modelMap.put("message", "product.exportNull.message");
		}else {
			modelMap.put("isexportExcel", true);
			modelMap.put("isexportXML", true);
		}
		return list(preq, request, response, modelMap);
	}

	@RequestMapping(value = "/exportExcel")
	public String exportExcel(ProductRequest preq, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		if (preq == null)
			preq = new ProductRequest();
		BatchParam bp = new BatchParam();
		bp.setCharset(request.getCharacterEncoding());
		bp.setRequest(request);
		byte[] bytes = batchOutputService.exportProductsExcel(
				productService.search(preq), bp);
		String id =fileService.addTmpFile(bytes);
		return RequestUtils.getRedirectUrl("/admin/file/"+id,"xls");
	}

	@RequestMapping(value = "/exportXML")
	public String exportXML(ProductRequest preq, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		if (preq == null)
			preq = new ProductRequest();
		BatchParam bp = new BatchParam();
		bp.setCharset(request.getCharacterEncoding());
		bp.setRequest(request);
		byte[] bytes = batchOutputService.exportProductsXML(
				productService.search(preq), bp);
		String id =fileService.addTmpFile(bytes);
		return RequestUtils.getRedirectUrl("/admin/file/xml/"+id,"zip");
	}

	@RequestMapping(value = "/list")
	public String list(ProductRequest preq, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		if (preq == null)
			preq = new ProductRequest();
		initSelect(modelMap, preq);
		Integer userId = this.getUserid(request);
		Integer pageNo = cpn(preq.getPageNo());
		Pagination p = productService.search(preq, userId, pageNo,
				CookieUtils.getPageSize(request));
		modelMap.put("pagination", p);
		modelMap.put("pr", preq);
		return path + "list";
	}

	private void initSelect(ModelMap modelMap, ProductRequest preq) {
		List<OperatingSystem> operatingSystems = commonService
				.listOperatingSystems();
		OperatingSystem operatingSystem = new OperatingSystem();
		operatingSystem.setId(0);
		operatingSystem.setName("");
		operatingSystems.add(0, operatingSystem);
		modelMap.put("oss", operatingSystems);
		List<Brand> brands = commonService.listBrands();
		Brand brand = new Brand();
		brand.setId(0);
		brand.setName("");
		brands.add(0, brand);
		modelMap.put("brands", brands);
		List<Model> models = new ArrayList<Model>();
		if (preq.getBrandid() != null && preq.getBrandid() > 0)
			models = commonService.listModelsByBrand(preq.getBrandid());
		Model model = new Model();
		model.setId(0);
		model.setName("");
		models.add(0, model);
		modelMap.put("models", models);
		List<Vendor> vendors = commonService.listActiveVendors();
		Vendor vendor = new Vendor();
		vendor.setId(0);
		vendor.setName("");
		vendors.add(0, vendor);
		modelMap.put("vendorNames", vendors);
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return path + "detail";
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String save(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		return path + "detail";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(Integer ids[], 
			Integer idparam,
			ProductRequest preq,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		if(idparam!=null && idparam >0){
			productService.deleteByIds(new Integer[]{idparam});
		}else{
			productService.deleteByIds(ids);
		}
		preq.addList(ids);
		return list(preq, request, response, modelMap);
	}

	@RequestMapping(value = "/recover", method = RequestMethod.POST)
	public String recover(Integer ids[],
			Integer idparam,
			ProductRequest preq,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		if(idparam!=null && idparam >0){
			productService.recoverByIds(new Integer[]{idparam});
		}else{
			productService.recoverByIds(ids);
		}
		preq.addList(ids);
		return list(preq, request, response, modelMap);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(
			@RequestParam(value = "id", required = false) Integer id,
			ProductRequest preq,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) throws Exception{
		Product bean;
		if(id==null){
			bean = new Product();
			bean.setOperator(this.getUser(request));
		}else
			bean = productService.get(id);
		modelMap.put("bean", bean);
		initDropdowns(modelMap,bean);
		return path+"edit";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String save(
			Product product,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) throws Exception{
		Integer userId = this.getUserid(request);
		product.setOperatorId(userId);
		product.setOperator(this.getUser(request));
		Product bean = productService.save(product);
		modelMap.put("bean", bean);
		modelMap.put("message",bean.getMessage());
		initDropdowns(modelMap,bean);	
		return path+"edit";
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(
			@RequestParam(value = "id", required = false) Integer id,
			ProductRequest preq,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) throws Exception{
		Product bean = productService.get(id);
		modelMap.put("bean", bean);
		return path+"view";
	}
	
	
	
	private void initDropdowns(ModelMap modelMap,Product product){
		List<OperatingSystem> operatingSystems = commonService.listOperatingSystems();
		modelMap.put("oss", operatingSystems);
		List<Brand> brands = commonService.listBrands();
		modelMap.put("brands", brands);
		Integer brandId = product.getBrandId();
		if(brandId==null || brandId==0)
			brandId=brands.get(0).getId();
		List<Model> models = commonService.listModelsByBrand(brandId);
		modelMap.put("models", models);
		List<Vendor> vendors = commonService.listActiveVendors();
		modelMap.put("vendorNames", vendors);
	}

	@RequestMapping(value = "/getmodelsbybrandid", method = RequestMethod.POST)
	public @ResponseBody String getModels(
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "hasblank", required = false) Boolean hasblank,
			HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List<Model> models = new ArrayList<Model>();
		if (id != null) {
			models = commonService.listModelsByBrand(id);
		}
		if(hasblank){
			Model model = new Model();
			model.setId(0);
			model.setName("");
			models.add(0, model);
		}
		List<Item> items = new ArrayList<Item>();
		for(Model m:models){
			items.add(m);
		}
		String jsonStr = ItemConvertUtils.convertObjectListToJSON(items);
		response.setContentType("text/html;charset=UTF-8");  
	    response.getWriter().print(jsonStr);  
		return null;
	}

}
