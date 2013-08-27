package org.mis.controller.reminder;

import java.util.Calendar;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.common.CookieUtils;
import org.common.page.Pagination;
import org.json.JSONObject;
import org.mis.controller.BaseController;
import org.mis.service.reminder.ReminderBackgroundService;
import org.mis.service.reminder.ReminderService;
import org.models.Reminder;
import org.models.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/reminder")
public class ReminderController extends BaseController {
	
	@Autowired
	private ReminderService reminderService;
	
	@Autowired
	private ReminderBackgroundService reminderBackgroundService;
	
	private static final String path="reminder/";
	
	@RequestMapping(value = "/reminder")
	public String reminder(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Integer userId = this.getUserid(request);
	
		Reminder bean = reminderService.get(userId);
		if(bean == null){
			bean = new Reminder();
			bean.setId(0);
		}else
			reminderBackgroundService.changetoread(userId);		
		modelMap.put("bean", bean);	
		return path+"detail";
	}
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		Integer userId = this.getUserid(request);
		Reminder bean = reminderService.get(userId);
		if(bean == null){
			bean = new Reminder();
			bean.setId(0);
			bean.setReminderTime(Calendar.getInstance());
		}
		modelMap.put("bean", bean);
		return path+"settings";
	}
	
	@RequestMapping(value = "/settings",method = RequestMethod.POST)
	public String settings(
			Reminder reminder,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) throws Exception{
		Integer userId = this.getUserid(request);
		Reminder rem = reminderService.save(reminder,userId);
		modelMap.put("bean", rem);
		modelMap.put("message",rem.getMessage());
		return path+"settings";

	}
	
	@RequestMapping(value = "/unread",method = RequestMethod.POST)
	public @ResponseBody String unread(	HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Integer userId = this.getUserid(request);
		HashSet<Integer> hash = reminderBackgroundService.getNotifications();
		JSONObject jsonObj = new JSONObject();
		if(hash.contains(userId)){
			jsonObj.put("unread", 1);
		}else{
			jsonObj.put("unread", 0);
		}
		return jsonObj.toString();
	}
	
	
	
}
