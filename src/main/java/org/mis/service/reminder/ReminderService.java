package org.mis.service.reminder;

import org.models.Reminder;

public interface ReminderService {

	public Reminder save(Reminder reminder,Integer userId);
	public Reminder get(Integer id);
}
