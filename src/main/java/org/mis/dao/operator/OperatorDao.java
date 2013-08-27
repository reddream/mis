package org.mis.dao.operator;

import java.util.List;

import org.common.page.Pagination;
import org.models.Operator;
import org.models.Role;
import org.models.Vendor;
import org.models.operator.OperatorRequest;

public interface OperatorDao {
	public Operator save(Operator operator) throws Exception;
	public Operator get(Integer id);
	List<Role> listRoles();
	public Pagination search(OperatorRequest oreq,int userId,int pageNo, int pageSize);
    public void deleteByIds(Integer[] ids);
	public void recoverByIds(Integer[] ids);
	public Operator findById(Integer id);
	
	public boolean exists(String login,Integer id);
}
