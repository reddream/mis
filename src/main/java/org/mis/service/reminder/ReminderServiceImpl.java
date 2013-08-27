package org.mis.service.reminder;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.common.page.Pagination;
import org.mis.dao.reminder.ReminderDao;
import org.mis.service.BaseServiceImpl;
import org.models.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReminderServiceImpl  extends BaseServiceImpl implements ReminderService{

	@Autowired
	private ReminderDao reminderDao;
	
	@Override
	public Reminder save(Reminder reminder,Integer userId) {
		if(StringUtils.isBlank(reminder.getContent())){
			reminder.setMessage("±ÿ–ÎÃÓ–¥Ã·–—ƒ⁄»›.");
			return reminder;
		}
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, reminder.getHour());
		c.set(Calendar.MINUTE, reminder.getMinute());
		reminder.setReminderTime(c);
		if(reminder.getId()==null || reminder.getId()==0){
			Calendar current = Calendar.getInstance();
			current.set(Calendar.HOUR_OF_DAY, 0);
			current.set(Calendar.MINUTE, 0);
			reminder.setReadTime(c);		
		}
		reminder.setId(userId);
		Reminder r = reminderDao.save(reminder);
		r.setMessage("product.success.message");
		return r;
	}

	@Override
	public Reminder get(Integer id) {
		return reminderDao.findById(id);
	}

}
