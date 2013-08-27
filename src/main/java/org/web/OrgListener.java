package org.web;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.common.RootPathUtils;


/**
 * The listener interface for receiving vehicle events.
 * The class that is interested in processing a vehicle
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addVehicleListener<code> method. When
 * the vehicle event occurs, that object's appropriate
 * method is invoked.
 *
 * @see VehicleEvent
 */
public class OrgListener implements ServletContextListener {

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		String realPath = arg0.getServletContext().getRealPath("");
		try {
			RootPathUtils.init(realPath);
			String rootDir =  RootPathUtils.getRootDir();
			if(rootDir.endsWith("/"))
				rootDir= rootDir.substring(0,rootDir.length()-1);
			System.setProperty("rootDir",rootDir);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
