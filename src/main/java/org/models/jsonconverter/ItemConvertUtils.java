package org.models.jsonconverter;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.models.Item;

public class ItemConvertUtils {
	
	public static String convertObjectListToJSON(List<Item> items) throws JSONException{
		String strJSON=StringUtils.EMPTY;
		JSONArray array = new JSONArray();
		for(Item i:items){
			
			JSONObject jsonObj = new JSONObject();
			array.put(jsonObj);
			jsonObj.put("id", i.getId().toString());
			jsonObj.put("name", i.getName());
		}
		strJSON = array.toString();
		return strJSON;
	}
	
}
