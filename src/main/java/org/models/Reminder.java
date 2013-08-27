package org.models;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="t_daily_reminder")
public class Reminder extends ActionResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6688204716592140937L;

	@Id
	@Column(name="f_operator_id")	
	private Integer id;
	
	@ManyToOne(cascade={CascadeType.REFRESH},optional=false,fetch =FetchType.LAZY)  
	@JoinColumn(name="f_operator_id",insertable = false, updatable = false)
	private Operator operator;
		
	@Column(name="f_content")
	private String content;
	
	@Column(name="f_reminder_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar reminderTime;
	
	@Column(name="f_read_time",insertable=true,updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar readTime;

	@Transient
	private Integer hour;
	@Transient
	private Integer minute;
	
	public Reminder(){

	}
	
	public Integer getHour() {
		if(hour==null)
			hour=reminderTime.get(Calendar.HOUR_OF_DAY);
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer getMinute() {
		if(minute==null)
			minute=reminderTime.get(Calendar.MINUTE);
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Calendar getReminderTime() {
		return reminderTime;
	}

	public void setReminderTime(Calendar reminderTime) {
		this.reminderTime = reminderTime;
	}
	
	public Calendar getReadTime() {
		return readTime;
	}

	public void setReadTime(Calendar readTime) {
		this.readTime = readTime;
	}
		
}
