package org.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.common.RootPathUtils;
import org.common.SpringBeanUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerExecutionChain;

/**
 * The Class VehicleServlet.
 */
public class OrgServlet extends org.springframework.web.servlet.DispatcherServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(OrgServlet.class);   
	
    /**
     * Instantiates a new vehicle servlet.
     *
     * @see HttpServlet#HttpServlet()
     */
    public OrgServlet() {
        super();
    }

	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		logger.info("The realPath inital is finished,realPath:"+RootPathUtils.getRootDir());
		super.init(config);
		WebApplicationContext ctx =getWebApplicationContext();// WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		SpringBeanUtils.setCtx(ctx);
	}

	@Override
	protected HandlerExecutionChain getHandler(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		return super.getHandler(request);
	}
	
	
	
	
		
}

