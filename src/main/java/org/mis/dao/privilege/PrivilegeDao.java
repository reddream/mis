package org.mis.dao.privilege;

import java.util.List;

import org.models.Privilege;

public interface PrivilegeDao {

	public List<Privilege> getAllPrivileges(Integer operatorId);
	
}
