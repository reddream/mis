package org.web.viewresolver;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.common.LanguageUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.web.config.ConfigurationManager;

/**
 * The Class RichFreeMarkerView.
 */
public class RichFreeMarkerView  extends FreeMarkerView {
	
	/** The Constant CONTEXT_PATH. */
	public static final String CONTEXT_PATH = "base";
	public static final String CURRENT_TIME="curtime";
	public static final String CURRENT_LAN="lan";
	public static final String REQUEST_URI="requesturi";
	public static final String VIRTUAL_PATH="virtualPath";

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.view.freemarker.FreeMarkerView#exposeHelpers(java.util.Map, javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings("unchecked")
	protected void exposeHelpers(Map model, HttpServletRequest request)
			throws Exception {
		super.exposeHelpers(model, request);
		model.put(CONTEXT_PATH, request.getContextPath());
		model.put(CURRENT_TIME, Calendar.getInstance().getTimeInMillis());
		String uri = request.getRequestURI();
		model.put(REQUEST_URI, uri);
		String vp = uri.substring(0, uri.lastIndexOf("/"));
		model.put(VIRTUAL_PATH, vp);
		LanguageUtils.setLan(request, model);
		ConfigurationManager.addConfigurations(model);
	}
}
