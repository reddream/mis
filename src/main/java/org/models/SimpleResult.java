package org.models;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

public class SimpleResult extends ActionResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4513179021242251031L;

	public SimpleResult(){
		super();
		redirctUrl=StringUtils.EMPTY;
	}
		
	public SimpleResult(String redirctUrl) {
		super();
		this.redirctUrl = redirctUrl;
	}


	private String redirctUrl;
	
	public String getRedirctUrl() {
		return redirctUrl;
	}

	public void setRedirctUrl(String redirctUrl) {
		this.redirctUrl = redirctUrl;
	}
	
	@Override
	public String getJSON() throws Exception {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("isOK", this.isOK);
		jsonObj.put("message", this.message);
		jsonObj.put("redirectUrl", this.redirctUrl);
		return jsonObj.toString();
	}

}
