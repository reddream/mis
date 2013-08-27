package org.mis.controller.batch;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mis.controller.BaseController;
import org.mis.service.batch.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.web.config.BatchConfiguration;
import org.web.message.MessageResolver;

//@Controller
//@RequestMapping("/admin/batch")
public class BatchController extends BaseController {
//
//	@Autowired
//	private BatchService batchService;
//
//	@Autowired
//	private BatchConfiguration batchConfig;
//
//	private String replaceContent(String content, List<String> keys,
//			HttpServletRequest request) throws UnsupportedEncodingException,
//			NoSuchMessageException, IllegalStateException {
//		for (String key : keys) {
//			String str = MessageResolver.getMessage(request, key);
//			content = content.replace(key, str);
//		}
//		return content;
//	}
//
//	@RequestMapping(value = "/importVendors", method = RequestMethod.POST)
//	public String importVendors(MultipartHttpServletRequest request,
//			HttpServletResponse response, ModelMap modelMap) throws Exception {
//		Integer userId = this.getUserid(request);
//		MultipartFile importFile = request.getFile("file");
//		String content = batchService.importVendors(
//				importFile.getInputStream(), userId);
//		content = replaceContent(content,batchConfig.getKeywords(),request);
//		
//		return "import/panel";
//	}

}
