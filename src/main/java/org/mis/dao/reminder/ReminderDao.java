package org.mis.dao.reminder;

import java.util.List;

import org.common.page.Pagination;
import org.models.Reminder;

public interface ReminderDao {
	public Reminder save(Reminder reminder);
	public Reminder findById(Integer id);	
	
	public List<Reminder> searchAvialiableReminders();
	public void changetoread(Integer id);
	
}
