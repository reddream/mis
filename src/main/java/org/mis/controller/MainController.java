package org.mis.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.common.CookieUtils;
import org.models.Operator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.web.RequestUtils;

@Controller
@RequestMapping("/admin") 
public class MainController extends BaseController {
		
	private boolean refreshIndex = false;
	
    @RequestMapping(value="/index",method = RequestMethod.GET)  
    public String index(HttpServletRequest request,
			HttpServletResponse response){
    	return "index";
    }
    
    @RequestMapping(value="/main",method = RequestMethod.GET)  
    public String main(HttpServletRequest request,
			HttpServletResponse response){
    	return "main";
    }
		
    @RequestMapping(value="/left",method = RequestMethod.GET)  
	public String left(	HttpServletRequest request,
			HttpServletResponse response){	
		return RequestUtils.getRedirectUrl("/admin/dashboard/left");
	}
    
    @RequestMapping(value="/right",method = RequestMethod.GET)  
    public String right(HttpServletRequest request,
			HttpServletResponse response){
    	return RequestUtils.getRedirectUrl("/admin/dashboard/right");
    }
    
    @RequestMapping(value="/top",method = RequestMethod.GET)  
    public String top(HttpServletRequest request,
			HttpServletResponse response,
			ModelMap modelMap) {
    	modelMap.put("refreshIndex", refreshIndex);
    	return "top";
    }
	
    @RequestMapping(value="/top",method = RequestMethod.POST)  
    public String topPost(
			@RequestParam(value = "roleid", required = true) Integer roleid,
    		HttpServletRequest request,
			HttpServletResponse response,
			ModelMap modelMap) throws Exception {

    	authMng.refreshRole(roleid, request, response,this.session);
    	modelMap.put("refreshIndex", true);
    	addDefaultRole(roleid, request, response);
    	return "top";
    }



}