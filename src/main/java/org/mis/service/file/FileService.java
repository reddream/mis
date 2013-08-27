package org.mis.service.file;

import java.io.UnsupportedEncodingException;

public interface FileService {
	public String addTmpFile(byte[] bytes) throws Exception;
	public byte[] findFile(String id)  throws Exception;
	public void remove(String id);
}
