package org.web.test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mis.service.reminder.ReminderBackgroundService;
import org.mis.service.reminder.ReminderService;
import org.models.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ContextConfiguration(locations = { "classpath:org-servlet.xml" })
public class TestReminderService {

	@Before
	public void prepareTestData() {
		System.out.println("before");
	}
	
	@Autowired
	private ReminderService reminderService;
	
	@Autowired
	private ReminderBackgroundService reminderBackgroundService;
	
	@Test
	public void testReminders(){
		System.out.println("testReminders starts");
		List<Reminder> reminders = reminderBackgroundService.searchAvialiableReminders();
		System.out.println("testReminders ends");
	}
	
	@Test
	public void TestReadReminderNotifications(){
		System.out.println("testReminders starts");
		reminderBackgroundService.getNotifications().add(1);
		reminderBackgroundService.changetoread(1);
		System.out.println("testReminders ends");
	}
	
	
	@After
	public void after() {
		System.out.println("after");
	}
	
}
