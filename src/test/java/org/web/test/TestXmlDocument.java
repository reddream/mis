package org.web.test;

import org.common.StringToOutputStream;
import org.jdom2.CDATA;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class TestXmlDocument {
	
	public static void main(String args[]) throws Exception{	
		Element root = new Element("Products");
		Document document =new Document(root);
		for(int i=0;i<10;i++){
			Element eproduct = new Element("Product");
			root.addContent(eproduct);
			
			Element code = new Element("Code");
			code.setText("xxx"+i);
			eproduct.addContent(code);
			
			Element name =new Element("Name");
			eproduct.addContent(name);
			CDATA cd=new CDATA("Name"+(i+1));
			name.addContent(cd);
		}
		XMLOutputter XMLOut = new XMLOutputter();
		Format format = Format.getPrettyFormat();
		format.setEncoding("UTF-8");
		format.setIndent(" "); 
		XMLOut.setFormat(format);
		StringToOutputStream output=new StringToOutputStream();
		XMLOut.output(document,output);
		System.out.println(output.toString());
	}
	
}
