package org.mis.controller.file;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.common.ZipCompressor;
import org.mis.service.file.FileService;
import org.models.batch.BatchParam;
import org.models.vendor.VendorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin/file")
public class FileController {

	@Autowired
	private FileService fileService;
	
	@RequestMapping(value = "/{id}.xls")
	public void exportExcel(
			@PathVariable("id") String id,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) throws Exception{
		byte[] bytes = fileService.findFile(id);
		fileService.remove(id);
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		FileCopyUtils.copy(bytes, response.getOutputStream());
	}
	
	
	@RequestMapping(value = "/{id}.xml")
	public void exportXml(
			@PathVariable("id") String id,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) throws Exception{
		byte[] bytes = fileService.findFile(id);
		fileService.remove(id);
		response.setContentType("application/octet-stream");
		FileCopyUtils.copy(bytes, response.getOutputStream());
	}
	
	
	@RequestMapping(value = "/xml/{id}.zip")
	public void exportZip(
			@PathVariable("id") String id,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) throws Exception{
		byte[] bytes = fileService.findFile(id);		
		fileService.remove(id);
		byte[] newBytes = ZipCompressor.compress(bytes, id+".xml");
		response.setContentType("application/x-zip-compressed");
		FileCopyUtils.copy(newBytes, response.getOutputStream());
	}
	
	
}
