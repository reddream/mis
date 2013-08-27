package org.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mis.dao.auth.AuthDao;
import org.models.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web.config.AuthConfiguration;
import org.web.message.MessageResolver;
import org.web.session.SessionProvider;


/**
 * The Class AuthMngImpl.
 */
@Service
public class AuthMngImpl implements AuthMng {

	/** The user dao. */
	@Autowired
	private AuthDao userDao;
	
	@Autowired
	private AuthConfiguration authConfig;
	
	public Integer retrieveUserIdFromSession(SessionProvider session,
			HttpServletRequest request) {
		Integer id =(Integer)session.getAttribute(request, AUTH_KEY);
		return id;
	}

	public Operator login(String username, String password, Integer roleId,
			HttpServletRequest request, HttpServletResponse response,
			SessionProvider session) throws Exception {
		
		Operator user = userDao.login(username, password,roleId);
		if(user.getId()!=null && user.getId()>0 && user.getInactive() == 1){
			user.setMessage("用户已经失效，无法使用该系统.");
			return user;
		}
		if(user.getId()!=null && user.getId()>0 && user.getCurrentRole()!=null ){
			session.setAttribute(request, response, AUTH_KEY, user.getId());
			session.setAttribute(request, response, AUTH_USER, user);
			user.setOK(true);
			return user;
		}
		if(user.getId()!=null && user.getId()>0 && user.getCurrentRole()==null){
			user.setMessage("用户在系统中没有角色,建议联系管理员分配角色.");
			return user;
		}
		Operator lguser =new Operator();
		try{
		lguser.setMessage(MessageResolver.getMessage(request,"login.failedtext"));
		}catch(Exception ex){}
		return lguser;
	}

	@Override
	public Operator retrieveOperatorFromSession(SessionProvider session,
			HttpServletRequest request) {
		return (Operator)session.getAttribute(request, AUTH_USER);
	}

	@Override
	public void refreshRole(Integer roleId,HttpServletRequest request, HttpServletResponse response, SessionProvider session) throws Exception {
		Integer userId = retrieveUserIdFromSession(session,request);
		Operator oper = userDao.refreshRole(userId, roleId);
		session.setAttribute(request, response, AUTH_KEY, oper.getId());
		session.setAttribute(request, response, AUTH_USER, oper);
	}

	@Override
	public boolean exists(Integer id, String password) {
		return userDao.exists(id, password);
	}

	@Override
	public boolean changePass(Integer id, String password) {
		return userDao.changePass(id, password);
	}
}
