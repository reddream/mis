package org.web.directive;


import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang.StringUtils;




import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class EncodingDirective implements TemplateDirectiveModel {

	public static final String PARAM_T = "text";
	public static final String PARAM_C = "encode";
	
	public void execute(Environment env, Map params, TemplateModel[] tm,
			TemplateDirectiveBody tdb) throws TemplateException, IOException {
		@SuppressWarnings("unchecked")
		String s = DirectiveUtils.getString(PARAM_T, params);
		@SuppressWarnings("unchecked")
		String code = DirectiveUtils.getString(PARAM_C, params);
		Writer out = env.getOut();
		out.append(s);
//		if(StringUtils.isBlank(code))
//			code = "UTF8";
//		if (s != null) {
//			String str = new String(s.getBytes("ISO-8859-1"), code);
//			Writer out = env.getOut();
//			out.append(str);
//		}
		
	}

}
