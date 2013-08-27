package org.common;

import java.text.DateFormat;
import java.util.Date;

/**
 * The Class DateFormatUtils.
 */
public class DateFormatUtils extends org.apache.commons.lang.time.DateFormatUtils{
	
	/**
	 * Instantiates a new date format utils.
	 */
	private DateFormatUtils(){}
	
	
	
	
	/**
	 * Format date.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static String formatDate(Date date){
		return DateFormat.getDateInstance().format(date);
	}
}