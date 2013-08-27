package org.common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class StringToStream {
  public static InputStream convertStringToStream(String str) throws UnsupportedEncodingException{
	  return new ByteArrayInputStream(str.getBytes("UTF-8"));
  }
  
  public static InputStream convertStringToStream(String str,String encoding) throws UnsupportedEncodingException{
	  return new ByteArrayInputStream(str.getBytes(encoding));
  }  
}