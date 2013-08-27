package org.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import freemarker.ext.beans.MapModel;

public class LanguageUtils {

	public static void setLan(HttpServletRequest request,Map modelMap){
		String language = request.getLocale().getLanguage();
		String country = request.getLocale().getCountry();
		if(country.equals("CN") || country.equals("TW"))
			modelMap.put("lan", language+"_"+country);
		else
			modelMap.put("lan", language);
	}
	
}
