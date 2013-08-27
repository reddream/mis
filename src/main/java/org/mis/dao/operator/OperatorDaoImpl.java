package org.mis.dao.operator;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.common.hibernate3.Finder;
import org.common.hibernate3.HibernateBaseDao;
import org.common.page.Pagination;
import org.hibernate.Query;
import org.models.Operator;
import org.models.Role;
import org.models.Vendor;
import org.models.constants.ShowType;
import org.models.operator.OperatorRequest;
import org.models.vendor.VendorRequest;
import org.springframework.stereotype.Repository;

@Repository
// @Transactional
public class OperatorDaoImpl extends HibernateBaseDao<Operator, Integer>
		implements OperatorDao {

	@Override
	public Operator save(Operator operator) throws Exception {
		HashSet<Role> roles = new HashSet<Role>();
		if (operator.getRoleids() != null) {
			for (Integer roleId : operator.getRoleids()) {
				Role r = new Role();
				r.setId(roleId);
				roles.add(r);
			}
			operator.setRoles(roles);
		}
		if (operator.getId() == null)
			operator.setCreatedTime(Calendar.getInstance());
		operator.setUpdatedTime(Calendar.getInstance());
		if(operator.getId()==null){
			getSession().save(operator);
			operator.setMessage("用户保存成功.");
		}
		else{
			getSession().update(operator);
			operator.setMessage("用户已更新成功.");
		}
			
		Finder finder=Finder.create("from Role");
		List<Role> tmpRoles = find(finder);
		for(Role r:tmpRoles){
			for(Role tmpR:roles){
				if(tmpR.getId().equals(r.getId())){
					String name = r.getName();
					tmpR.setName(name);
				}
			}
		}
		operator.setOK(true);
		return operator;
	}

	@Override
	protected Class<Operator> getEntityClass() {
		return Operator.class;
	}

	@Override
	public List<Role> listRoles() {
		String hql = "from Role";
		return find(hql);
	}

	@Override
	public Pagination search(OperatorRequest oreq, int userId, int pageNo,
			int pageSize) {
		Finder f = generateVendorListQuery(oreq);
		Pagination p = find(f, pageNo, pageSize);
		List<Operator> operators = (List<Operator>) p.getList();
		for (Operator operator : operators) {
			destoryLazy(operator);
		}
		return p;
	}

	private Finder generateVendorListQuery(OperatorRequest oreq) {
		Finder f;
		if (oreq.getRoleId() != null && oreq.getRoleId() > 0) {
			f = Finder
					.create("select bean from Operator bean , Role r where r.id in elements(bean.roles) and r.id = "
							+ oreq.getRoleId() + " and bean.id>0 ");
		} else {
			f = Finder
					.create("select bean from Operator bean where bean.id>0 ");
		}

		if (StringUtils.isNotBlank(oreq.getOperatorName())) {
			f.append("and bean.name like '%"
					+ oreq.getOperatorName().replace("'", StringUtils.EMPTY)
					+ "%'");
		}
		if (StringUtils.isNotBlank(oreq.getLoginName())) {
			f.append("and bean.login like '%"
					+ oreq.getLoginName().replace("'", StringUtils.EMPTY)
					+ "%'");
		}
		if (oreq.getShowType() != null) {
			switch (oreq.getShowType()) {
			case ShowType.All:
				break;
			case ShowType.Valid:
				f.append("and bean.inactive  = 0");
				break;
			case ShowType.InValid:
				f.append("and bean.inactive  = 1");
				break;
			}
		}
		if (StringUtils.isNotBlank(oreq.getQueryOrderBy())) {
			String[] orderBy = oreq.getQueryOrderBy().split("\\.");
			String order;
			if (orderBy[1].equals("0")) {
				order = "asc";
			} else
				order = "desc";
			f.append(" order by bean." + orderBy[0] + " " + order);
		}
		return f;
	}

	private void destoryLazy(Operator operator) {
		operator.getName();
		operator.getLogin();
	}

	@Override
	public void deleteByIds(Integer[] ids) {
		if (ids != null) {
			String idsStr = convertIdsToString(ids);
			String hql = "update Operator set inactive=1,updatedTime=:updatedTime where inactive=0 and id in ("
					+ idsStr + ")";
			Query query = getSession().createQuery(hql);
			query.setParameter("updatedTime", Calendar.getInstance());
			query.executeUpdate();
		}
	}

	@Override
	public void recoverByIds(Integer[] ids) {
		if (ids != null) {
			String idsStr = convertIdsToString(ids);
			String hql = "update Operator set inactive=0,updatedTime=:updatedTime where inactive=1 and id in ("
					+ idsStr + ")";
			Query query = getSession().createQuery(hql);
			query.setParameter("updatedTime", Calendar.getInstance());
			query.executeUpdate();
		}
	}

	@Override
	public Operator findById(Integer id) {
		Operator op = this.get(id);
		op.getRoleids();
		return op;
	}

	@Override
	public boolean exists(String login, Integer id) {
		if (id == null || id == 0) {
			String hql = "from Operator where login=:login";
			Finder f = Finder.create(hql);
			f.setParam("login", login);
			List list = find(f);
			return list.size() > 0;
		} else {
			String hql = "from Operator where login=:login and id<>:id";
			Finder f = Finder.create(hql);
			f.setParam("login", login);
			f.setParam("id", id);
			List list = find(f);
			return list.size() > 0;
		}
	}

}