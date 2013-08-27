package org.web.message;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.NoSuchMessageException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

public class MessageResolver {

	public static String getMessage(HttpServletRequest request, String code,
			Object... args) throws UnsupportedEncodingException, NoSuchMessageException, IllegalStateException {
		WebApplicationContext messageSource = RequestContextUtils
				.getWebApplicationContext(request);
		if (messageSource == null) {
			throw new IllegalStateException("WebApplicationContext not found!");
		}
		LocaleResolver localeResolver = RequestContextUtils
				.getLocaleResolver(request);
		Locale locale;
		if (localeResolver != null) {
			locale = localeResolver.resolveLocale(request);
		} else {
			locale = request.getLocale();
		}
		String s =  messageSource.getMessage(code, args, locale);
//		String str = new String(s.getBytes("ISO-8859-1"), "UTF8");
//		return str;
		return s;
	}
	
	
}
