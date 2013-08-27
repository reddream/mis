package org.common;

import org.apache.commons.lang.StringUtils;

public class DoubleUtils {

	public static String getFourToFive(Double score_type) {
		if(score_type==null)
			return StringUtils.EMPTY;
		double bl = (Math.round(score_type / .01) * .01);
		String st = String.valueOf(bl);
		st = st.replace(".", "_");
		String[] st_arr = st.split("_");
		String temp = "";
		if (st_arr[1].length() > 2) {
			temp = st_arr[1].substring(0, 2);
		} else if (st_arr[1].length() < 2) {
			temp = st_arr[1] + "0";
		} else {
			temp = st_arr[1];
		}
		return st_arr[0] + "." + temp;
	}

}
