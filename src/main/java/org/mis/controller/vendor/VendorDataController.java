package org.mis.controller.vendor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.common.StreamUtils;
import org.mis.controller.BaseController;
import org.mis.service.batch.BaseBatchServiceImpl;
import org.mis.service.batch.BatchService;
import org.mis.service.batch.BatchXmlService;
import org.mis.service.batch.BatchXmlServiceImpl;
import org.mis.service.file.FileService;
import org.models.batch.BatchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.web.config.BatchConfiguration;

@Controller
@RequestMapping("/admin/vendor")
public class VendorDataController extends BaseController {
	
	private static final String path="vendor/";
	
	@Autowired
	private BatchConfiguration batchConfig;
	@Autowired
	private BatchService batchService;
	@Autowired
	private BatchXmlService batchXmlService;
	@Autowired
	private FileService fileService;
	
	
	
	@RequestMapping(value = "/data")
	public String data(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		return path+"data";
	}
	
	@RequestMapping(value = "/dataUpload",method = RequestMethod.POST)
	public String dataUpload(@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		String message = "";
		String name = file.getOriginalFilename();
		String[] sp = name.split("\\.");
		String suffix = sp[sp.length-1].toLowerCase().trim();
		
		if(file.isEmpty() || file.getSize() > Integer.parseInt(batchConfig.getVendorSize())) {
			message = "pleaseCheckTheFileSize";
			modelMap.put("message", message);
			return path+"data";
		}
		
		Integer userId = this.getUserid(request);
		BatchParam param = new BatchParam();
		param.setOperatorId(userId);
		param.setRequest(request);
			
		byte result[]=null;
		message="pleaseCheckTheFileFormat";
		
		if(suffix.equalsIgnoreCase("xml")){
			try{					
			result = batchXmlService.importVendors(file.getInputStream(), param);
			message="importTheXMLFileComplete";
			}catch(Exception ex){
				message = ex.getMessage();
				modelMap.put("message", message);
				return path+"data";
			}
			
		}
		
		if(suffix.equalsIgnoreCase("xls") || suffix.equalsIgnoreCase("xlsx") ){
			try{
			result = batchService.importVendors(file.getInputStream(), param);
			message="importTheXlsxFileComplete";
			}catch(Exception ex){
				message = ex.getMessage();
				modelMap.put("message", message);
				return path+"data";
			}
		}
		
		if(result !=null){
			String filename = fileService.addTmpFile(result);
			modelMap.put("importResult",filename);
			modelMap.put("showresult", true);
		}else {
			message = "fileContentIsIncorrect";
		}
		modelMap.put("message", message);
		return path+"data";
	}
	@RequestMapping(value = "/showresults",method = RequestMethod.GET)
	public void showImportResults(
			@RequestParam(value = "id", required = false) String id,
			HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{		
		byte[] bytes =fileService.findFile(id);
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		FileCopyUtils.copy(bytes, response.getOutputStream());
	}
}
		
		
		