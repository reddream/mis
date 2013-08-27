package org.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.models.Operator;
import org.web.session.SessionProvider;



/**
 * The Interface AuthMng.
 */
public interface AuthMng {
	
	/** The Constant AUTH_KEY. */
	public static final String AUTH_KEY = "auth_key";
	
	public static final String AUTH_USER="auth_user";
	

	public Integer retrieveUserIdFromSession(SessionProvider session,HttpServletRequest request);

	public Operator retrieveOperatorFromSession(SessionProvider session,HttpServletRequest request);
	
	public Operator login(String username, String password,Integer roleId,
			HttpServletRequest request, HttpServletResponse response, SessionProvider session) throws Exception;
	
	public void refreshRole(Integer roleId,HttpServletRequest request, HttpServletResponse response, SessionProvider session) throws Exception;
	
	public boolean exists(Integer id,String password);
	
	public boolean changePass(Integer id,String password);
	
}