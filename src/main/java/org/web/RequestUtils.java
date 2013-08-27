package org.web;
import static org.web.Constants.POST;
import static org.web.Constants.UTF8;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UrlPathHelper;


/**
 * The Class RequestUtils.
 */
public class RequestUtils {
	
	/** The Constant log. */
	private static final Logger log = LoggerFactory
	.getLogger(RequestUtils.class);

/**
 * Gets the query param.
 *
 * @param request the request
 * @param name the name
 * @return the query param
 */
public static String getQueryParam(HttpServletRequest request, String name) {
if (StringUtils.isBlank(name)) {
	return null;
}
if (request.getMethod().equalsIgnoreCase(POST)) {
	return request.getParameter(name);
}
String s = request.getQueryString();
if (StringUtils.isBlank(s)) {
	return null;
}
try {
	s = URLDecoder.decode(s, UTF8);
} catch (UnsupportedEncodingException e) {
	log.error("encoding " + UTF8 + " not support?", e);
}
String[] values = parseQueryString(s).get(name);
if (values != null && values.length > 0) {
	return values[values.length - 1];
} else {
	return null;
}
}

/**
 * Gets the query params.
 *
 * @param request the request
 * @return the query params
 */
@SuppressWarnings("unchecked")
public static Map<String, Object> getQueryParams(HttpServletRequest request) {
Map<String, String[]> map;
if (request.getMethod().equalsIgnoreCase(POST)) {
	map = request.getParameterMap();
} else {
	String s = request.getQueryString();
	if (StringUtils.isBlank(s)) {
		return new HashMap<String, Object>();
	}
	try {
		s = URLDecoder.decode(s, UTF8);
	} catch (UnsupportedEncodingException e) {
		log.error("encoding " + UTF8 + " not support?", e);
	}
	map = parseQueryString(s);
}

Map<String, Object> params = new HashMap<String, Object>(map.size());
int len;
for (Map.Entry<String, String[]> entry : map.entrySet()) {
	len = entry.getValue().length;
	if (len == 1) {
		params.put(entry.getKey(), entry.getValue()[0]);
	} else if (len > 1) {
		params.put(entry.getKey(), entry.getValue());
	}
}
return params;
}

/**
 * Parses a query string passed from the client to the server and builds a
 * <code>HashTable</code> object with key-value pairs. The query string
 * should be in the form of a string packaged by the GET or POST method,
 * that is, it should have key-value pairs in the form <i>key=value</i>,
 * with each pair separated from the next by a &amp; character.
 * 
 * <p>
 * A key can appear more than once in the query string with different
 * values. However, the key appears only once in the hashtable, with its
 * value being an array of strings containing the multiple values sent by
 * the query string.
 * 
 * <p>
 * The keys and values in the hashtable are stored in their decoded form, so
 * any + characters are converted to spaces, and characters sent in
 * hexadecimal notation (like <i>%xx</i>) are converted to ASCII characters.
 *
 * @param s a string containing the query to be parsed
 * @return a <code>HashTable</code> object built from the parsed key-value
 * pairs
 */
public static Map<String, String[]> parseQueryString(String s) {
String valArray[] = null;
if (s == null) {
	throw new IllegalArgumentException();
}
Map<String, String[]> ht = new HashMap<String, String[]>();
StringTokenizer st = new StringTokenizer(s, "&");
while (st.hasMoreTokens()) {
	String pair = (String) st.nextToken();
	int pos = pair.indexOf('=');
	if (pos == -1) {
		continue;
	}
	String key = pair.substring(0, pos);
	String val = pair.substring(pos + 1, pair.length());
	if (ht.containsKey(key)) {
		String oldVals[] = (String[]) ht.get(key);
		valArray = new String[oldVals.length + 1];
		for (int i = 0; i < oldVals.length; i++) {
			valArray[i] = oldVals[i];
		}
		valArray[oldVals.length] = val;
	} else {
		valArray = new String[1];
		valArray[0] = val;
	}
	ht.put(key, valArray);
}
return ht;
}

/**
 * Gets the request map.
 *
 * @param request the request
 * @param prefix the prefix
 * @return the request map
 */
public static Map<String, String> getRequestMap(HttpServletRequest request,
	String prefix) {
return getRequestMap(request, prefix, false);
}

/**
 * Gets the request map with prefix.
 *
 * @param request the request
 * @param prefix the prefix
 * @return the request map with prefix
 */
public static Map<String, String> getRequestMapWithPrefix(
	HttpServletRequest request, String prefix) {
return getRequestMap(request, prefix, true);
}

/**
 * Gets the request map.
 *
 * @param request the request
 * @param prefix the prefix
 * @param nameWithPrefix the name with prefix
 * @return the request map
 */
@SuppressWarnings("unchecked")
private static Map<String, String> getRequestMap(
	HttpServletRequest request, String prefix, boolean nameWithPrefix) {
Map<String, String> map = new HashMap<String, String>();
Enumeration<String> names = request.getParameterNames();
String name, key, value;
while (names.hasMoreElements()) {
	name = names.nextElement();
	if (name.startsWith(prefix)) {
		key = nameWithPrefix ? name : name.substring(prefix.length());
		value = StringUtils.join(request.getParameterValues(name), ',');
		map.put(key, value);
	}
}
return map;
}

/**
 * Gets the ip addr.
 *
 * @param request the request
 * @return the ip addr
 */
public static String getIpAddr(HttpServletRequest request) {
String ip = request.getHeader("X-Real-IP");
if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
	return ip;
}
ip = request.getHeader("X-Forwarded-For");
if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
	int index = ip.indexOf(',');
	if (index != -1) {
		return ip.substring(0, index);
	} else {
		return ip;
	}
} else {
	return request.getRemoteAddr();
}
}

/**
 * HttpServletRequest.getRequestURL+"?"+HttpServletRequest.getQueryString
 *
 * @param request the request
 * @return the location
 */
public static String getLocation(HttpServletRequest request) {
UrlPathHelper helper = new UrlPathHelper();
StringBuffer buff = request.getRequestURL();
String uri = request.getRequestURI();
String origUri = helper.getOriginatingRequestUri(request);
buff.replace(buff.length() - uri.length(), buff.length(), origUri);
String queryString = helper.getOriginatingQueryString(request);
if (queryString != null) {
	buff.append("?").append(queryString);
}
return buff.toString();
}

/**
 * Gets the redirect url.
 *
 * @param name the name
 * @return the redirect url
 * @see HttpServletRequest#getRequestedSessionId()
 */
//public static String getRequestedSessionId(HttpServletRequest request) {
//String sid = request.getRequestedSessionId();
//String ctx = request.getContextPath();
//if (request.isRequestedSessionIdFromURL() || StringUtils.isBlank(ctx)) {
//	return sid;
//} else {
//	Cookie cookie = CookieUtils.getCookie(request,
//			Constants.JSESSION_COOKIE);
//	if (cookie != null) {
//		return cookie.getValue();
//	} else {
//		return null;
//	}
//}
//
//}

public static String getRedirectUrl(String name){
return String.format("redirect:%s.%s",name, Constants.PAGE_EXT);
}

public static String getRedirectUrl(String name,String ex){
return String.format("redirect:%s.%s",name, ex);
}


/**
 * The main method.
 *
 * @param args the arguments
 */
public static void main(String[] args) {
}
}
