package org.mis.service.role;

import java.util.List;

import org.mis.dao.role.RoleDao;
import org.mis.service.BaseServiceImpl;
import org.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseServiceImpl implements RoleService  {
	@Autowired
	private RoleDao roleDao;

	@Override
	public List<Role> list() {
		return roleDao.list();
	}	
}
