package org.web.config;

import java.util.Map;
import java.util.Map.Entry;

import org.common.SpringBeanUtils;
import org.exception.OrgRuntimeException;
import org.springframework.ui.ModelMap;

public class ConfigurationManager {

	private Map<String,BaseConfiguration> configurations;
		
	public Map<String, BaseConfiguration> getConfigurations() {
		return configurations;
	}
	
	public <T extends BaseConfiguration> T getConfiguration(String name){
			if(this.configurations.containsKey(name)){
				throw new OrgRuntimeException("There is no configuration named "+name);
			}
			return (T)configurations.get(name);
	}
	
	public static  <T extends BaseConfiguration> T getConf(Class c){
		String name = c.getSimpleName();
		name = name.substring(0,1).toLowerCase()+name.substring(1,name.length())+"Configuration";
		return getConf(name);
	}
	
	public static <T extends BaseConfiguration> T getConf(String name){
		ConfigurationManager cm = SpringBeanUtils.getBean("configurationManager");
		return cm.getConfiguration(name);			
	}
	
	public void setConfigurations(Map<String, BaseConfiguration> configurations) {
		this.configurations = configurations;
	}

	public static void addConfigurations(Map modelMap){
		ConfigurationManager cm = SpringBeanUtils.getBean("configurationManager");
		for(Entry<String,BaseConfiguration> e:cm.configurations.entrySet()){
			modelMap.put(e.getKey(), e.getValue());
		}
	}
	
}
