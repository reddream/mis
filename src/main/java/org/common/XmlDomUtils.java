package org.common;

import java.io.IOException;
import java.io.InputStream;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

public class XmlDomUtils {
	public static Element findContentElement(String xml) throws JDOMException,
			IOException {
		SAXBuilder builder = new SAXBuilder();
		InputStream is = StringToStream.convertStringToStream(xml);
		Document doc = builder.build(is);
		is.close();
		Element root = doc.getRootElement();
		Element body = root.getChildren().get(0);
		Element contentElement = body.getChildren().get(0);
		return contentElement;
	}

}
