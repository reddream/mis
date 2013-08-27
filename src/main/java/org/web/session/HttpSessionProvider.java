package org.web.session;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The Class HttpSessionProvider.
 */
public class HttpSessionProvider implements SessionProvider {

	/* (non-Javadoc)
	 * @see vehicle.web.session.SessionProvider#getAttribute(javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
	public Serializable getAttribute(HttpServletRequest request, String name) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			return (Serializable) session.getAttribute(name);
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see vehicle.web.session.SessionProvider#setAttribute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String, java.io.Serializable)
	 */
	public void setAttribute(HttpServletRequest request,
			HttpServletResponse response, String name, Serializable value) {
		HttpSession session = request.getSession();
		session.setAttribute(name, value);
	}

	/* (non-Javadoc)
	 * @see vehicle.web.session.SessionProvider#getSessionId(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public String getSessionId(HttpServletRequest request,
			HttpServletResponse response) {
		return request.getSession().getId();
	}

	/* (non-Javadoc)
	 * @see vehicle.web.session.SessionProvider#logout(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}
}
