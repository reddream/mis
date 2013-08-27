package org.mis.controller.operator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.common.Constants;
import org.common.CookieUtils;
import org.common.page.Pagination;
import org.mis.controller.BaseController;
import org.mis.service.operator.OperatorService;
import org.models.Area;
import org.models.Level;
import org.models.Operator;
import org.models.PayCondition;
import org.models.Role;
import org.models.Vendor;
import org.models.operator.OperatorRequest;
import org.models.vendor.VendorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.web.config.PasswordConfiguration;
import org.web.security.AuthMng;

@Controller
@RequestMapping("/admin/operator")
public class OperatorController extends BaseController {
	
	@Autowired
    private OperatorService operatorService;
	
	@Autowired
	private PasswordConfiguration passwordConfig;
	
	private static final String path="operator/";

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		return path+"main";
	}
	
	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public String left(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		return path+"left";
	}
	
	@RequestMapping(value = "/right", method = RequestMethod.GET)
	public String right(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		return path+"right";
	}
	
	private void initSelect(ModelMap modelMap){
		List<Role> roles = operatorService.listRoles();
		Role role = new Role();
		role.setId(0);
		role.setName("");
		roles.add(0,role);
		modelMap.put("roles", roles);
	}

	@RequestMapping(value = "/list")
	public String list(
			OperatorRequest oreq,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) throws Exception{
		if(oreq==null)
			oreq = new OperatorRequest();
		initSelect(modelMap);
		Integer userId = this.getUserid(request);
		Integer pageNo = cpn(oreq.getPageNo());
		Pagination p = operatorService.search(oreq, userId, pageNo, CookieUtils.getPageSize(request));
		modelMap.put("pagination", p);
		modelMap.put("or",oreq);
		return path+"list";
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		return path+"detail";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String save(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		return path+"detail";
	}
	
	@RequestMapping(value = "/delete",method=RequestMethod.POST)
	public String delete(
			Integer ids[],
			Integer idparam,
			OperatorRequest oreq,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap	
	) throws Exception{
		if(idparam!=null && idparam >0){
			operatorService.deleteByIds(new Integer[]{idparam});
		}else{
			operatorService.deleteByIds(ids);
		}
		oreq.addList(ids);
		return list(oreq,request,response,modelMap);
	}
	
	@RequestMapping(value = "/recover",method=RequestMethod.POST)
	public String recover(
			Integer ids[],
			Integer idparam,
			OperatorRequest oreq,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap	
	) throws Exception{
		if(idparam!=null && idparam >0){
			operatorService.recoverByIds(new Integer[]{idparam});
		}else{
			operatorService.recoverByIds(ids);
		}
		oreq.addList(ids);
		return list(oreq,request,response,modelMap);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		return path+"detail";
	}
	
	@RequestMapping(value = "/reset",method=RequestMethod.POST)
	public String reset(
			Integer[] ids,
			Integer idparam,
			OperatorRequest oreq,
			HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Integer userId = this.getUserid(request);
		if(userId == idparam){
			String message = "���������Լ�������Ϊ:"+passwordConfig.getDefaultPassword();
			modelMap.put(Constants.MESSAGE, message);
		}else {
			if(idparam!=null && idparam >0){
				authMng.changePass(idparam,passwordConfig.getDefaultPassword());
				String resetName = operatorService.get(idparam).getName();
				String message = "�Ѿ������û� �� "+ resetName + " ��������";
				modelMap.put(Constants.MESSAGE, message);
				oreq.addList(ids);
				oreq.getIdlist().remove(idparam);
			}
		}
		return list(oreq,request,response,modelMap);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) throws Exception{
		Operator bean;
		if(id==null){
			bean = new Operator();
			bean.setId(0);
			bean.setInactive(0);
		}else
			bean = operatorService.get(id);
		modelMap.put("bean", bean);
		initDropdowns(modelMap);
		return path+"edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String save(
			Operator bean,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) throws Exception{
		bean = operatorService.save(bean);
		modelMap.put(Constants.MESSAGE, bean.getMessage());
		if(bean.getId()==null)
			bean.setId(0);
		modelMap.put("bean", bean);
		initDropdowns(modelMap);		
		Operator user= getUser(request);
		if(bean.isOK() && bean.getId().equals(user.getId()) ){			
			user.setName(bean.getName());
			user.setRoles(bean.getRoles());
			user.setRoleids(bean.getRoleids());
		}
		return path+"edit";
	}
	
	//resetinedit
	@RequestMapping(value = "/resetinedit", method = RequestMethod.POST)
	public String resetinedit(Operator bean,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) throws Exception{
		Integer userId = this.getUserid(request);
		Integer resetId = bean.getId();
		if(resetId != 0){
			if(userId.equals(resetId)){
				modelMap.put(Constants.MESSAGE, "���ܽ��Լ�����������Ϊ:"+passwordConfig.getDefaultPassword());
			}else{
				authMng.changePass(resetId, passwordConfig.getDefaultPassword());
				modelMap.put(Constants.MESSAGE, "��������ɹ���.");
			}
		} else {
			modelMap.put(Constants.MESSAGE, "���ܶ�������û�������������.");
		}
		modelMap.put("bean", bean);
		initDropdowns(modelMap);	
		return path+"edit";
	}
	
	private void initDropdowns(ModelMap modelMap){
		List<Role> roles=commonService.listRole();
		modelMap.put("roles", roles);
	}
	
}
