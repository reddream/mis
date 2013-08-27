package org.mis.controller.vendor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.common.CookieUtils;
import org.common.page.Pagination;
import org.mis.controller.BaseController;
import org.mis.service.CommonService;
import org.mis.service.batch.BatchOutputService;
import org.mis.service.file.FileService;
import org.mis.service.vendor.VendorService;
import org.models.Area;
import org.models.Level;
import org.models.PayCondition;
import org.models.Vendor;
import org.models.batch.BatchParam;
import org.models.vendor.VendorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.web.RequestUtils;
import org.web.config.VendorConfiguration;

@Controller
@RequestMapping("/admin/vendor")
public class VendorController extends BaseController {
	private static final String path="vendor/";

	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private BatchOutputService batchOutputService;
	
	@Autowired
	private VendorConfiguration vendorConfiguration; 
	

	@Autowired
	private FileService fileService;
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		return path+"main";
	}
	
	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String left(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		return path+"left";
	}
	
	@RequestMapping(value = "/right", method = RequestMethod.GET)
	public String right(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		return path+"right";
	}
	
	
	@RequestMapping(value = "/exportExcel")
	public String exportExcel(
			VendorRequest vreq,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) throws Exception{
		if(vreq==null)
			vreq = new VendorRequest();
		initSelect(modelMap);
		BatchParam bp = new BatchParam();
		bp.setCharset(request.getCharacterEncoding());
		bp.setRequest(request);
		byte[] bytes  = batchOutputService.exportVendorsExcel(vendorService.search(vreq),bp);
		String id =fileService.addTmpFile(bytes);
		return RequestUtils.getRedirectUrl("/admin/file/"+id,"xls");
	}
	
	@RequestMapping(value = "/exportXML")
	public String exportXML(
			VendorRequest vreq,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) throws Exception{
		if(vreq==null)
			vreq = new VendorRequest();
		initSelect(modelMap);
		BatchParam bp = new BatchParam();
		bp.setCharset(request.getCharacterEncoding());
		bp.setRequest(request);
		byte[] bytes = batchOutputService.exportVendorsXML(vendorService.search(vreq),bp);
		//FileCopyUtils.copy(bytes, response.getOutputStream());
		String id =fileService.addTmpFile(bytes);
		return RequestUtils.getRedirectUrl("/admin/file/xml/"+id,"zip");
	}
	
	
	@RequestMapping(value = "/export" ,method=RequestMethod.POST)
	public String showExportCSV(
			VendorRequest vreq,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) throws Exception{
		if(vreq==null)
			vreq = new VendorRequest();
		List<Vendor> vendorList = vendorService.search(vreq);
		if(vendorList.size() <= 0){
			modelMap.put("message", "vendor.exportNull.message");
		}else{
			modelMap.put("isexportCSV", true);
			modelMap.put("isexportXML", true);
		}
		return list(vreq,request,response,modelMap);
	}

	@RequestMapping(value = "/list")
	public String list(
			VendorRequest vreq,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) throws Exception{
		if(vreq==null)
			vreq = new VendorRequest();
		initSelect(modelMap);
		Integer userId = this.getUserid(request);
		Integer pageNo = cpn(vreq.getPageNo());
		Pagination p = vendorService.search(vreq, userId, pageNo, CookieUtils.getPageSize(request));
		modelMap.put("pagination", p);
		modelMap.put("vr",vreq);
		return path+"list";
	}
	
	@RequestMapping(value = "/delete",method=RequestMethod.POST)
	public String delete(
			Integer ids[],
			Integer idparam,
			VendorRequest vreq,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap	
	) throws Exception{
		if(idparam!=null && idparam >0){
			vendorService.deleteByIds(new Integer[]{idparam});
		}else{
			vendorService.deleteByIds(ids);
		}
		vreq.addList(ids);
		return list(vreq,request,response,modelMap);
	}
	
	@RequestMapping(value = "/recover",method=RequestMethod.POST)
	public String recover(
			Integer ids[],
			Integer idparam,
			VendorRequest vreq,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap	
	) throws Exception{
		if(idparam!=null && idparam >0){
			vendorService.recoverByIds(new Integer[]{idparam});
		}else{
			vendorService.recoverByIds(ids);
		}
		vreq.addList(ids);
		return list(vreq,request,response,modelMap);
	}
	
	
	
	private void initSelect(ModelMap modelMap){
		List<Area> areas=commonService.listAreas();
		Area area = new Area();
		area.setId(0);
		area.setName("");
		areas.add(0,area);
		modelMap.put("areas", areas);
		List<Level> levels=commonService.listLevels();
		Level level  = new Level();
		level.setId(0);
		level.setName("");
		levels.add(0,level);
		modelMap.put("levels", levels);
		List<PayCondition> payConditions=commonService.listPayConditions();
		PayCondition payCondition = new PayCondition();
		payCondition.setId(0);
		payCondition.setName("");
		payConditions.add(0,payCondition);
		modelMap.put("payConditions", payConditions);
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		return path+"detail";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) throws Exception{
		Vendor bean;
		if(id==null){
			bean = new Vendor();
			bean.setOperator(this.getUser(request));
		}else
			bean = vendorService.get(id);
		modelMap.put("bean", bean);
		initDropdowns(modelMap);
		return path+"edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String save(
			Vendor vendor,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) throws Exception{
		Integer userId = this.getUserid(request);
		vendor.setOperatorId(userId);
		vendor.setOperator(this.getUser(request));
		Vendor bean = vendorService.save(vendor);
		modelMap.put("bean", bean);
		modelMap.put("message",bean.getMessage());
		initDropdowns(modelMap);	
		return path+"edit";
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(	
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) throws Exception{
		Vendor bean = vendorService.get(id);
		modelMap.put("bean", bean);
		return path+"view";
	}
		
	private void initDropdowns(ModelMap modelMap){
		List<Area> areas=commonService.listAreas();
		modelMap.put("areas", areas);
		List<Level> levels=commonService.listLevels();
		modelMap.put("levels", levels);
		List<PayCondition> payConditions=commonService.listPayConditions();
		modelMap.put("payConditions", payConditions);
	}
	
}
