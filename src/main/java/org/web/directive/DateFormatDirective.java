package org.web.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class DateFormatDirective implements TemplateDirectiveModel {

	private static final String PARAM="value";
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] arg2,
			TemplateDirectiveBody arg3) throws TemplateException, IOException {
		Date d= DirectiveUtils.getDate(PARAM, params);
		Calendar c=Calendar.getInstance();
		c.setTime(d);
		String s = DateFormatUtils.ISO_DATE_FORMAT.format(c.getTime()) + " " + DateFormatUtils.ISO_TIME_NO_T_FORMAT.format(c.getTime());
		Writer out = env.getOut();
		out.append(s);
	}

}
