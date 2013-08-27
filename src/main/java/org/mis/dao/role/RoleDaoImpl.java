package org.mis.dao.role;

import java.util.List;

import javax.management.relation.Role;

import org.common.hibernate3.HibernateBaseDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
//@Transactional
public class RoleDaoImpl extends HibernateBaseDao<Role,Integer> implements RoleDao {

	@Override
	protected Class<Role> getEntityClass() {
		return Role.class;
	}
//
	@Override
	public List<org.models.Role> list() {
		return this.find("from Role");
	}

}
