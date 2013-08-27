package org.models;

import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

public abstract class ActionResult extends BaseModel {
	
	@Transient
	protected String message;
	@Transient
	protected boolean isOK;
	
	public String getJSON() throws Exception{
		return StringUtils.EMPTY;
	}
		
	public ActionResult(){
		message=StringUtils.EMPTY;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isOK() {
		return isOK;
	}
	public void setOK(boolean isOK) {
		this.isOK = isOK;
	}
	
}
