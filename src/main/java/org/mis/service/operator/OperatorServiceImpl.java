package org.mis.service.operator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.common.page.Pagination;
import org.mis.dao.operator.OperatorDao;
import org.mis.service.BaseServiceImpl;
import org.models.Operator;
import org.models.Role;
import org.models.operator.OperatorRequest;
import org.models.vendor.VendorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web.config.PasswordConfiguration;

@Service
public class OperatorServiceImpl extends BaseServiceImpl implements OperatorService  {

	@Autowired
	private OperatorDao operatorDao;
	
	@Autowired
	private PasswordConfiguration passConfig;
	
	@Override
	public Operator save(Operator operator) throws Exception {
		if (operator.getId() == 0)
			operator.setId(null);
		if(StringUtils.isBlank(operator.getLogin())){
			operator.setMessage("登陆名不能为空.");
			return operator;
		}
		
		if(StringUtils.isBlank(operator.getName())){
			operator.setMessage("姓名不能为空.");
			return operator;
		}

		if(this.hasCh(operator.getLogin())) {
			operator.setMessage("姓名不能包含中文.");
			return operator;
		}
		
		if(operator.getRoleids()==null || operator.getRoleids().size()==0){
			operator.setMessage("必须选定角色.");
			return operator;
		}	
		if(operatorDao.exists(operator.getLogin(),operator.getId())){
			operator.setMessage("该用户名已经被占用，请另外选一个");
			return operator;
		}	
		if(operator.getId()==null)
			operator.setPassword(passConfig.getDefaultPassword());
		else{
			Operator op = operatorDao.findById(operator.getId());
			operator.setPassword(op.getPassword());	
		}
		operatorDao.save(operator);
		return operator;
	}
	
	
	
	

	@Override
	public Operator get(Integer id) {
		return operatorDao.findById(id);
	}

	@Override
	public List<Role> listRoles() {
		return operatorDao.listRoles();
	}

	@Override
	public Pagination search(OperatorRequest oreq, int userId, int pageNo,
			int pageSize) {
		Pagination p = operatorDao.search(oreq, userId, pageNo, pageSize);
		return p;
	}

	@Override
	public void deleteByIds(Integer[] ids) {
		operatorDao.deleteByIds(ids);		
	}

	@Override
	public void recoverByIds(Integer[] ids) {
		operatorDao.recoverByIds(ids);
	}
	
	private boolean hasCh(String str) {
		boolean isCh = false;
		String regex = "[\u4e00-\u9fa5]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		if(matcher.find()) {
			isCh = true;
		}		
		return isCh;
	}

}
