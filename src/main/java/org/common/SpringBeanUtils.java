package org.common;

import org.springframework.web.context.WebApplicationContext;

/**
 * The Class SpringBeanUtils.
 */
public class SpringBeanUtils {
	
	/** The ctx. */
	private static WebApplicationContext ctx;
	
	/** The is init. */
	public static boolean isInit=false;
	
	/**
	 * Sets the ctx.
	 *
	 * @param context the new ctx
	 */
	public static void setCtx(WebApplicationContext context) {
		ctx = context;
		isInit=true;
	}
	
	/**
	 * Gets the bean.
	 *
	 * @param <T> the generic type
	 * @param name the name
	 * @return the bean
	 */
	public static <T extends Object> T getBean(String name){
		return (T)ctx.getBean(name);	
	}
	
	public static <T extends Object> T getConfigurationBean(Class c){
		String name = c.getSimpleName();
		name = name.substring(0,1).toLowerCase()+name.substring(1,name.length())+"Configuration";
		return getBean(name);
	}
	
}
