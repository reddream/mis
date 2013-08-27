package org.mis.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.common.LanguageUtils;
import org.mis.service.role.RoleService;
import org.models.Operator;
import org.models.Role;
import org.models.SimpleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.web.Constants;
import org.web.RequestUtils;
import org.web.config.ConfigurationManager;

@Controller
public class LoginController extends BaseController {
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(
			@RequestParam(value = "returnUrl", required = false) String returnUrl,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		return showHome(returnUrl, request, response, modelMap);
	}

	/**
	 * Load.
	 * 
	 * @param returnUrl
	 *            the return url
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param modelMap
	 *            the model map
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String load(
			@RequestParam(value = "returnUrl", required = false) String returnUrl,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		return showHome(returnUrl, request, response, modelMap);
	}

	private String showHome(String returnUrl, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap)
			throws UnsupportedEncodingException {
		if (StringUtils.isNotBlank(returnUrl)) {
			returnUrl = URLDecoder.decode(returnUrl, Constants.UTF8);
		}
		modelMap.put(Constants.RETURNURL, returnUrl);
		String message = request.getParameter(Constants.MESSAGE);
		if (StringUtils.isNotBlank(message))
			modelMap.put(Constants.MESSAGE, message);
		session.logout(request, response);
		return "login";
	}

	@RequestMapping(value = "/loginAjax", method = RequestMethod.POST)
	public String submitAjax(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "returnUrl", required = false) String returnUrl,
			@RequestParam(value = "roleid", required = false) Integer roleid,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws Exception {
		if(roleid==null)
			roleid=this.getDefaultRoleId(request);
		Operator user = authMng.login(username, password, roleid, request,
				response, session);
		model.put(Constants.RETURNURL, returnUrl);
		if (user.getId()!= null && user.getId()>0 && user.isOK()) {
			this.addDefaultRole(user.getCurrentRole().getId(), request, response);
			if (StringUtils.isBlank(returnUrl)) {
				SimpleResult sr = new SimpleResult("admin/index.htm");
				sr.setOK(true);
				model.put("simpleresult", sr.getJSON());
				return "results/simpleresult";
			} else {
				SimpleResult sr = new SimpleResult(returnUrl);
				sr.setOK(true);
				model.put("simpleresult", sr.getJSON());
				return "results/simpleresult";
			}
		} else {
			SimpleResult sr = new SimpleResult();
			sr.setMessage(user.getMessage());
			model.put("simpleresult", sr.getJSON());
			return "results/simpleresult";
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		session.logout(request, response);
		return RequestUtils.getRedirectUrl("login");
	}
}
