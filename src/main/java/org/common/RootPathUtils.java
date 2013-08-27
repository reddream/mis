package org.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The Class RootPathUtils.
 */
public class RootPathUtils {

	/** The root dir. */
	private static String rootDir;
	
	/**
	 * Gets the root dir.
	 *
	 * @return the root dir
	 */
	public static String getRootDir(){
		return rootDir;
	}
	

	/**
	 * Inits the.
	 *
	 * @param realPath the real path
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void init(String realPath) throws FileNotFoundException,
			IOException {
		File realFile = new File(realPath);
		File rootFile = new File(realFile.getAbsolutePath());
		rootDir = rootFile.getAbsolutePath();
		
		if(!rootDir.endsWith("\\") && !rootDir.endsWith("/")){
			rootDir=rootDir+"/";
		}	
	}
	
}
