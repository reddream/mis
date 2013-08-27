package org.mis.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.common.CookieUtils;
import org.mis.service.CommonService;
import org.models.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.ui.ModelMap;
import org.web.Constants;
import org.web.message.MessageResolver;
import org.web.security.AuthMng;
import org.web.session.SessionProvider;



/**
 * The Class BaseAction.
 */
public abstract class BaseController {
	
	/** The session. */
	@Resource(name = "sessionProvider")
	protected SessionProvider session;

	protected Operator getUser(HttpServletRequest request) {
		return (Operator)session.getAttribute(request, AuthMng.AUTH_USER);
	}
	
	protected Integer getUserid(HttpServletRequest request){
		return (Integer)session.getAttribute(request, AuthMng.AUTH_KEY);
	}
	

	// @Resource(name = "authMng")
	/** The auth mng. */
	@Autowired
	protected AuthMng authMng;

	@Autowired
	protected CommonService commonService;
	/**
	 * Sets the auth mng.
	 *
	 * @param authMng the new auth mng
	 */
	public void setAuthMng(AuthMng authMng) {
		this.authMng = authMng;
	}


	/**
	 * Adds the error messageand redirect url.
	 *
	 * @param modelMap the model map
	 * @param message the message
	 * @param url the url
	 */
	protected void addErrorMessageandRedirectUrl(ModelMap modelMap,
			String message, String url) {
		if (StringUtils.isNotBlank(message))
			modelMap.put(Constants.MESSAGE, message);
		if (StringUtils.isNotBlank(url))
			modelMap.put(Constants.REDIRECT_URL, url);
	}
	
	protected Integer cpn(Integer n){
		return n==null?0:n;
	}
	
	protected String replaceContent(String content, List<String> keys,
			HttpServletRequest request) throws UnsupportedEncodingException,
			NoSuchMessageException, IllegalStateException {
		for (String key : keys) {
			String str = MessageResolver.getMessage(request, key);
			content = content.replace(key, str);
		}
		return content;
	}
	
	protected void addDefaultRole(Integer roleid, HttpServletRequest request,
			HttpServletResponse response) {
		String strUserRoleCookie =String.format("ur%s",this.getUserid(request));
    	CookieUtils.addCookie(request, response, strUserRoleCookie, roleid.toString(), 0, "/");
	}
	
	protected Integer getDefaultRoleId(HttpServletRequest request){
		String strUserRoleCookie =String.format("ur%s",this.getUserid(request));
		Cookie c = CookieUtils.getCookie(request,strUserRoleCookie );
		if(c!=null && StringUtils.isNotBlank(c.getValue())){
			return Integer.parseInt(c.getValue());
		}
		return null;
	}

}
