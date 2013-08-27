package org.web.session;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Interface SessionProvider.
 */
public interface SessionProvider {
	
	/**
	 * Gets the attribute.
	 *
	 * @param request the request
	 * @param name the name
	 * @return the attribute
	 */
	public Serializable getAttribute(HttpServletRequest request, String name);

	/**
	 * Sets the attribute.
	 *
	 * @param request the request
	 * @param response the response
	 * @param name the name
	 * @param value the value
	 */
	public void setAttribute(HttpServletRequest request,
			HttpServletResponse response, String name, Serializable value);

	/**
	 * Gets the session id.
	 *
	 * @param request the request
	 * @param response the response
	 * @return the session id
	 */
	public String getSessionId(HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * Logout.
	 *
	 * @param request the request
	 * @param response the response
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response);
}