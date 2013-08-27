package org.web.results;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The Class RedirectResult.
 */
public class RedirectResult {
	
	/** The type. */
	private String type;
	
	/** The url. */
	private String url;
	
	/** The message. */
	private String message;
	
	/**
	 * Instantiates a new redirect result.
	 *
	 * @param type the type
	 * @param url the url
	 * @param message the message
	 */
	public RedirectResult(String type, String url,String message) {
		super();
		this.type = type;
		this.url = url;
		this.message = message;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Gets the json str.
	 *
	 * @return the json str
	 * @throws JSONException the jSON exception
	 */
	public String getJsonStr() throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("type", type);
		jsonObj.put("url", url);
		jsonObj.put("message",message);
		return jsonObj.toString();
	}	
}
