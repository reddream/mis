package org.web.viewresolver;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

/**
 * The Class RichFreeMarkerViewResolver.
 */
public class RichFreeMarkerViewResolver  extends AbstractTemplateViewResolver {
	
	/**
	 * Set default viewClass.
	 */
	public RichFreeMarkerViewResolver() { 
		setViewClass(RichFreeMarkerView.class);
	}

	/**
	 * if viewName start with / , then ignore prefix.
	 *
	 * @param viewName the view name
	 * @return the abstract url based view
	 * @throws Exception the exception
	 */
	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		AbstractUrlBasedView view = super.buildView(viewName);
		// start with / ignore prefix
		if (viewName.startsWith("/")) {
			view.setUrl(viewName + getSuffix());
		}
		return view;
	}
}
