package org.web.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.common.DoubleUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class DoubleDirective implements TemplateDirectiveModel {

	private static final String PARAM="value";
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] arg2,
			TemplateDirectiveBody arg3) throws TemplateException, IOException {
		@SuppressWarnings("unchecked")
		String s = DirectiveUtils.getString(PARAM, params);
		if(StringUtils.isNotBlank(s)){
			Double d = Double.parseDouble(s);
			String str = DoubleUtils.getFourToFive(d);
			Writer out = env.getOut();
			out.append(s);
		}else{
			Writer out = env.getOut();
			out.append(s);			
		}	
	}

}
