package org.mis.dao.auth;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.common.hibernate3.Finder;
import org.common.hibernate3.HibernateBaseDao;
import org.hibernate.Query;
import org.models.Operator;
import org.models.Privilege;
import org.models.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
//@Transactional
public class AuthDaoImpl extends HibernateBaseDao<Operator,Integer>  implements AuthDao {
//

	@Override
	protected Class<Operator> getEntityClass() {
		return Operator.class;
	}

	@Override
	public Operator login(String username, String password, Integer roleid)
			throws Exception {
		String hql = "from Operator where login=:username and password=:password";
		Finder f = Finder.create(hql);
		f.setParam("username",username).setParam("password",password);
		List<Operator> list = find(f);
		System.out.println("list:"+list.size());
	    if(list.size() > 0){
	    	Operator operator = list.get(0);	
	    	for(Role r:operator.getRoles()){
	    		if(roleid == null || r.getId().equals(roleid)){
	    			operator.setCurrentRole(r);
	    			String actionUrls=StringUtils.EMPTY;
	    			for(Privilege p:r.getPrivileges()){
	    				actionUrls += p.getFunction().getActionURL()+";";
	    			}
	    			actionUrls = ";"+actionUrls;
	    			operator.setActionUrls(actionUrls);
	    			roleid=r.getId();
	    		}
	    	}
	    	return operator;
	    }
	    Operator op = new Operator(0);
	    op.setMessage("用户名/密码错误，不能登录");
	    return op;
	}

	@Override
	public Operator refreshRole(Integer id, Integer roleid) throws Exception {
		Operator operator = this.get(id);
		for(Role r:operator.getRoles()){
    		if(r.getId().equals(roleid)){
    			operator.setCurrentRole(r);
    			String actionUrls=StringUtils.EMPTY;
    			for(Privilege p:r.getPrivileges()){
    				actionUrls += p.getFunction().getActionURL()+";";
    			}
    			actionUrls = ";"+actionUrls;
    			operator.setActionUrls(actionUrls);
    		}
    	}
    	return operator;
	}

	@Override
	public boolean exists(Integer id, String password) {
		String hql = "from Operator where id=:id and password=:password";
		Finder f = Finder.create(hql);
		f.setParam("id", id);
		f.setParam("password", password);
		return find(f).size()>0;
	}

	@Override
	public boolean changePass(Integer id, String password) {
		String hql="update Operator set password=:password,updatedTime=:updatedTime where id=:id";
		Query query = getSession().createQuery(hql);
		query.setParameter("updatedTime", Calendar.getInstance());
		query.setParameter("password", password);
		query.setParameter("id", id);
		query.executeUpdate();
		return true;
	}

}
