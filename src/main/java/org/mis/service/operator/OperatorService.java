package org.mis.service.operator;

import java.util.List;

import org.common.page.Pagination;
import org.models.Operator;
import org.models.Role;
import org.models.operator.OperatorRequest;

public interface OperatorService {
	public Operator save(Operator operator) throws Exception;
	public Operator get(Integer id);
	List<Role> listRoles();
	public Pagination search(OperatorRequest oreq,int userId,int pageNo, int pageSize);
	public void deleteByIds(Integer[] ids);	
	public void recoverByIds(Integer[] ids);
}
