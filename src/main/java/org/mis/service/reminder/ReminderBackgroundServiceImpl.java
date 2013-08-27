package org.mis.service.reminder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.mis.dao.reminder.ReminderDao;
import org.models.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class ReminderBackgroundServiceImpl implements ReminderBackgroundService {

	private static final Logger logger = Logger.getLogger(ReminderBackgroundServiceImpl.class);
	
	@Autowired
	private ReminderDao reminderDao;

	private HashSet<Integer> notifications = new HashSet<Integer>();

	private boolean isWorking = false;

	@Override
	public void run() throws Exception {
		if (isWorking)
			return;
		isWorking = true;
		try {
			List<Reminder> reminders = reminderDao.searchAvialiableReminders();

			for (Reminder r : reminders) {
				notifications.add(r.getId());
			}
		} catch (Exception ex) {
			logger.error(ex,ex);
		} finally {
			isWorking = false;
		}

	}

	public HashSet<Integer> getNotifications() {
		return notifications;
	}

	@Override
	public void changetoread(Integer id) {
		if (notifications.contains(id)) {
			reminderDao.changetoread(id);
			notifications.remove(id);
		}
	}

	@Override
	public List<Reminder> searchAvialiableReminders() {
		return reminderDao.searchAvialiableReminders();
	}

}
