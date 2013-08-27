package org.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import org.json.JSONException;
import org.models.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;
import org.web.message.MessageResolver;
import org.web.results.RedirectResult;
import org.web.security.AuthMng;
import org.web.session.SessionProvider;

/**
 * The Class AdminContextInterceptor.
 */
public class AdminContextInterceptor extends HandlerInterceptorAdapter {

	private static final String currentuser = "currentuser";

	private static final String adminIndexUrl = "/admin/index";
	private String privilegePassPages[];

	private  String[] privilegePassPagesReg;
	
	private Boolean auth;
	private String loginUrl;
	private String returnUrl;

	private boolean validatePrivilege;
	private List<String> excludePrivilegeUrls;
	private List<String> excludeUrls;
	@Resource(name = "sessionProvider")
	protected SessionProvider session;
	private String processUrl;
	private AuthMng authMng;

	public Boolean getAuth() {
		return auth;
	}

	@Autowired
	public void setAuthMng(AuthMng authMng) {
		this.authMng = authMng;
	}

	public void setAuth(Boolean auth) {
		this.auth = auth;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	private String getLoginUrl(HttpServletRequest request)
			throws UnsupportedEncodingException {
		String returnUrl = request.getParameter(Constants.RETURNURL);
		if (StringUtils.isEmpty(returnUrl)) {
			String url = request.getRequestURI();
			String queryString = request.getQueryString();
			if (StringUtils.isNotBlank(queryString)) {
				url = url + "?" + queryString;
				url = URLEncoder.encode(url, Constants.UTF8);
			} else
				url = StringUtils.EMPTY;
			String strUrl = request.getContextPath() + loginUrl + "?"
					+ Constants.MESSAGE + "=" + "&" + Constants.RETURNURL + "="
					+ url;
			return strUrl;
		} else {
			return getLoginUrlOnlyWithContextPath(request);
		}
	}

	private String getLoginUrlOnlyWithContextPath(HttpServletRequest request) {
		return request.getContextPath() + loginUrl;
	}

	private String getProcessUrl(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder();
		if (loginUrl.startsWith("/")) {
			String ctx = request.getContextPath();
			if (!StringUtils.isBlank(ctx)) {
				buff.append(ctx);
			}
		}
		buff.append(processUrl);
		return buff.toString();
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	public String getProcessUrl() {
		return processUrl;
	}

	public void setProcessUrl(String processUrl) {
		this.processUrl = processUrl;
	}

	private boolean exclude(String uri) {
		if (excludeUrls != null) {
			for (String exc : excludeUrls) {
				if (exc.contains(uri) || uri.startsWith(exc)) {
					return true;
				}
			}
		}
		return false;
	}

	private static String getURI(HttpServletRequest request)
			throws IllegalStateException {
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		String ctxPath = helper.getOriginatingContextPath(request);
		uri = uri.replace(ctxPath, StringUtils.EMPTY);
		if (uri.equals("/")) {
			return adminIndexUrl;
		}
		return uri;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		response.setHeader("P3P", "CP=CAO PSA OUR");
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String uri = getURI(request);
		if (exclude(uri)) {
			return true;
		}
		Integer id = authMng.retrieveUserIdFromSession(session, request);
		if (id == null || id == 0) {
			String requestType = request.getHeader("X-Requested-With");
			if (StringUtils.isEmpty(requestType)) {
				response.sendRedirect(this.getLoginUrl(request));
				return false;
			} else {
				String sessionTimeoutErrMsg = MessageResolver.getMessage(request, "session.timeout");
				RedirectResult rr = new RedirectResult(Constants.REDIRECT,
						getLoginUrlOnlyWithContextPath(request),
						sessionTimeoutErrMsg);
				response.setContentType("application/json;charset=UTF-8");
				PrintWriter pw = response.getWriter();
				pw.write(rr.getJsonStr());
				pw.flush();
				return false;
			}
		}
		String acitonUrl = getActionUrl(request);
		if (!dealWithPrivilege(request, response, acitonUrl))
			return false;
		return super.preHandle(request, response, handler);
	}

	private boolean dealWithPrivilege(HttpServletRequest request,
			HttpServletResponse response, String acitonUrl)
			throws UnsupportedEncodingException, IOException, JSONException {
		Operator operator = authMng.retrieveOperatorFromSession(session,
				request);
		if (validatePrivilege && operator != null) {
			boolean excludePrivilegeFlag = excludePrivilgePages(acitonUrl);
			if (!excludePrivilegeFlag) {
				if (!operator.hasFunction(acitonUrl)) {
					String requestType = request.getHeader("X-Requested-With");
					if (StringUtils.isEmpty(requestType)) {
						response.sendRedirect(request.getContextPath()
								+ "/noprivilege.htm");
					} else {
						String errMsg = MessageResolver.getMessage(request,
								"privilege.nopermission");
						RedirectResult rr = new RedirectResult(
								Constants.REDIRECT, StringUtils.EMPTY, errMsg);
						response.setContentType("application/json;charset=UTF-8");
						PrintWriter pw = response.getWriter();
						pw.write(rr.getJsonStr());
						pw.flush();
					}
					return false;
				}
			}
		}
		request.setAttribute(currentuser, operator);
		return true;
	}

	private String getActionUrl(HttpServletRequest request) {
		String uri = request.getRequestURI();
		uri = uri.replace(request.getContextPath(), StringUtils.EMPTY);
		String uriArray[] = uri.split("\\.");
		return uriArray[0];
	}

	private boolean excludePrivilgePages(String uri) {
		for (String url : privilegePassPages) {
			if (url.equals(uri))
				return true;
		}
		for(String url:privilegePassPagesReg){
			if(uri.startsWith(url)){
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
		// String url =
		// "http://localhost:8080/index.jsp?fg=12&wer=sdfdsfdsds&page=15;20";
		// url = URLEncoder.encode(url, Constants.UTF8);
		// System.out.println(url);
		// url = URLDecoder.decode(url, Constants.UTF8);
		// System.out.println(url);
		String u = "/admin/index.htm";
		String arr[] = u.split("\\.");
		for (String s : arr) {
			System.out.println(s);
		}
	}

	public List<String> getExcludePrivilegeUrls() {
		return excludePrivilegeUrls;
	}

	public void setExcludePrivilegeUrls(List<String> excludePrivilegeUrls) {
		this.excludePrivilegeUrls = excludePrivilegeUrls;
	}
	
	public boolean isValidatePrivilege() {
		return validatePrivilege;
	}

	public void setValidatePrivilege(boolean validatePrivilege) {
		this.validatePrivilege = validatePrivilege;
	}

	public String[] getPrivilegePassPagesReg() {
		return privilegePassPagesReg;
	}

	public void setPrivilegePassPagesReg(String[] privilegePassPagesReg) {
		this.privilegePassPagesReg = privilegePassPagesReg;
	}

	public String[] getPrivilegePassPages() {
		return privilegePassPages;
	}

	public void setPrivilegePassPages(String[] privilegePassPages) {
		this.privilegePassPages = privilegePassPages;
	}

	
	
}
