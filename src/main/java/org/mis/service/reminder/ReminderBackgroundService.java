package org.mis.service.reminder;

import java.util.HashSet;
import java.util.List;

import org.models.Reminder;

public interface ReminderBackgroundService {

	public HashSet<Integer> getNotifications();
	public void changetoread(Integer id);
	public List<Reminder> searchAvialiableReminders();
	public void run() throws Exception;
		
}
