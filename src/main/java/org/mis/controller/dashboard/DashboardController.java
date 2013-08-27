package org.mis.controller.dashboard;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.common.Constants;
import org.exception.OrgRuntimeException;
import org.mis.controller.BaseController;
import org.mis.dao.operator.OperatorDao;
import org.mis.dao.operator.OperatorDaoImpl;
import org.mis.service.operator.OperatorService;
import org.mis.service.operator.OperatorServiceImpl;
import org.models.Operator;
import org.models.password.PasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.web.config.PasswordConfiguration;

@Controller
@RequestMapping("/admin/dashboard")
public class DashboardController extends BaseController {
	private static final String dashboardPath = "dashboard/";
	
	@Autowired
	private PasswordConfiguration passConfig;
	
	
	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String left(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		return dashboardPath+"left";
	}
	
	@RequestMapping(value = "/right", method = RequestMethod.GET)
	public String right(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		return dashboardPath+"right";
	}
	
	@RequestMapping(value = "/changepassword", method = RequestMethod.GET)
	public String changepassword(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		return dashboardPath+"changepassword";
	}
	
	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	public String changepass(
			PasswordRequest pr,			
			HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		
		Integer userId=this.getUserid(request);
		String returnPath = dashboardPath+"changepassword";
		String message=StringUtils.EMPTY;
		//TODO: merge js to here
		if(passConfig.getMinilength() > pr.getNewPass().trim().length()){
			message = "�����볤��������"+passConfig.getMinilength();
			modelMap.put(Constants.MESSAGE, message);
			return returnPath;
		}
		
		if(passConfig.getDefaultPassword().equalsIgnoreCase(pr.getNewPass().trim())){
			message="�����벻���޸�Ϊ:"+passConfig.getDefaultPassword();
			modelMap.put(Constants.MESSAGE, message);
			return returnPath;
		}
		
		if(!authMng.exists(userId, pr.getOldPass().trim())){
			message="�����벻��ȷ";
			modelMap.put(Constants.MESSAGE, message);
			return returnPath;
		}
		
		Pattern pattern = Pattern.compile("[a-z]+");
		Matcher matcher = pattern.matcher(pr.getNewPass());
		
		if(!matcher.find()) {
			message="������Ҫ����һ��Сд��ĸ!";
			modelMap.put(Constants.MESSAGE, message);
			return returnPath;
		}
		pattern = Pattern.compile("\\d+");
		matcher = pattern.matcher(pr.getNewPass());
		if(!matcher.find()) {
			message="������Ҫ����һ������!";
			modelMap.put(Constants.MESSAGE, message);
			return returnPath;
		}
		
		pattern = Pattern.compile("[A-Z]+");
		matcher = pattern.matcher(pr.getNewPass());
		if(!matcher.find()) {
			message="�����ٰ���һ����д��ĸ!";
			modelMap.put(Constants.MESSAGE, message);
			return returnPath;
		}
		
		authMng.changePass(userId, pr.getNewPass().trim());
		message = "��������ɹ���";	
		modelMap.put(Constants.MESSAGE, message);
		return returnPath;
	}	
}
