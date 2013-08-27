package org.common;

import java.util.List;

import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

public class XPathUtils {

	public static List<Element> selectNodes(Object context,String query){
		XPathExpression<Element> xpath =
		    XPathFactory.instance().compile(query, Filters.element());
		return xpath.evaluate(context);
	}
	
	public static Element selectSingleNode(Object context,String query){
		XPathExpression<Element> xpath =
		    XPathFactory.instance().compile(query, Filters.element());
		return xpath.evaluateFirst(context);
	}
	
}
