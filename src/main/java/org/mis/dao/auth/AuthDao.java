package org.mis.dao.auth;

import org.models.Operator;

public interface AuthDao {
	public Operator login(String username, String password,Integer roleid) throws Exception;
	public Operator refreshRole(Integer id,Integer roleid) throws Exception;
	
	public boolean exists(Integer id,String password);	
	public boolean changePass(Integer id,String password);
	
}
