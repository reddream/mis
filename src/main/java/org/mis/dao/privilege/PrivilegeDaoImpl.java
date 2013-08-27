package org.mis.dao.privilege;

import java.util.List;

import org.common.hibernate3.HibernateBaseDao;
import org.models.Privilege;
import org.springframework.stereotype.Repository;


public class PrivilegeDaoImpl extends HibernateBaseDao<Privilege,Integer> implements PrivilegeDao  {

	
	@Override
	public List<Privilege> getAllPrivileges(Integer operatorId) {
		return null;
	}

	@Override
	protected Class<Privilege> getEntityClass() {
		return Privilege.class;
	}

	
	
}
