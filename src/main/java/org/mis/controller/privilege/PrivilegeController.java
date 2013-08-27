package org.mis.controller.privilege;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mis.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PrivilegeController extends BaseController {
	
	private static final String path="privilege/";
	
	@RequestMapping(value = "/noprivilege", method = RequestMethod.GET)
	public String noprivilege(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		return path+"noprivilege";
	}
	
}
