package org.mis.service.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.lf5.util.StreamUtils;
import org.common.RootPathUtils;
import org.springframework.stereotype.Service;

@Service
public class FileServiceV2Impl implements FileService {

	private String directory;

	@Override
	public String addTmpFile(byte[] bytes) throws Exception {
		remove24hoursTmpFiles();
		String id = this.generateFileName();
		String filename = getDirectory() + id;
		FileOutputStream fos = new FileOutputStream(new File(filename));
		fos.write(bytes);
		fos.close();
		return id;
	}

	@Override
	public byte[] findFile(String id) throws Exception {
		File f = new File(getDirectory() + id);
		FileInputStream fis = new FileInputStream(f);
		byte bytes[] = StreamUtils.getBytes(fis);
		fis.close();
		return bytes;
	}

	@Override
	public void remove(String id) {
		File f = new File(getDirectory() + id);
		if (f.exists())
			f.delete();
	}

	private String getDirectory() {
		if (StringUtils.isEmpty(directory)) {
			directory = RootPathUtils.getRootDir() + "WEB-INF/tmpfiles/";
		}
		return directory;
	}

	private String generateFileName() {
		Calendar c = Calendar.getInstance();
		return String.format("%s", c.getTimeInMillis());
	}
	
	private void remove24hoursTmpFiles()
	{
		 Calendar c =  Calendar.getInstance();
		 c.add(Calendar.DATE,-1);
		 String dir = getDirectory();
		 File dirInfo  = new File(dir);
		 File[] files = dirInfo.listFiles();
		 long theday = c.getTimeInMillis();
		 for(File f:files){
			long last = f.lastModified();
			if(last<=theday){
				try{
					f.delete();
				}catch(Exception ex){
					
				}
			}
		 }
	}

}
