package org.mis.dao.reminder;

import java.util.Calendar;
import java.util.List;



import org.apache.commons.lang.StringUtils;
import org.common.hibernate3.Finder;
import org.common.hibernate3.HibernateBaseDao;
import org.common.page.Pagination;
import org.hibernate.Query;
import org.models.Reminder;
import org.models.Vendor;
import org.models.constants.ShowType;
import org.models.vendor.VendorRequest;
import org.springframework.stereotype.Repository;

@Repository
public class ReminderDaoImpl extends HibernateBaseDao<Reminder,Integer> implements ReminderDao {

	@Override
	public Reminder save(Reminder reminder) {
		if(this.get(reminder.getId())!=null){
			getSession().clear();
			getSession().update(reminder);
		}
		else 
			getSession().save(reminder);
		return reminder;
	}

	@Override
	public Reminder findById(Integer id) {
		return this.get(id);
	}

	@Override
	protected Class<Reminder> getEntityClass() {
		return Reminder.class;
	}

	@Override
	public List<Reminder> searchAvialiableReminders() {
		Calendar c = Calendar.getInstance();
		Integer hour = c.get(Calendar.HOUR_OF_DAY);
		Integer minute = c.get(Calendar.MINUTE);
		String hql="select r from Reminder r,Operator o where r.id=o.id and  HOUR(r.reminderTime)*100+MINUTE(r.reminderTime)<=:hourSpan*100+:minuteSpan " +
				" and HOUR(r.readTime)*100+MINUTE(r.readTime) <= HOUR(r.reminderTime)*100+MINUTE(r.reminderTime) and o.inactive=0";
		Finder f = Finder.create(hql);

		f.setParam("hourSpan",hour);
		f.setParam("minuteSpan", minute);
		return  find(f);
	}	

	@Override
	public void changetoread(Integer id) {
		String hql = "update Reminder set readTime=:current where id=:id";
		Query q = this.getSession().createQuery(hql);
		q.setParameter("current", Calendar.getInstance());
		q.setParameter("id", id);
		getSession().clear();
		q.executeUpdate();	
	}

}
