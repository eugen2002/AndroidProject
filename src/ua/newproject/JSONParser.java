package ua.newproject;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

	private final String			LOG_TAG	= getClass().getSimpleName();
	private String					info	= "";
	private ArrayList<JSONObject>	jsArray	= new ArrayList<JSONObject>();

	public JSONParser() {
	}

	public JSONParser(JSONObject jsonObject, JSONArray jsonArray, String data) {
		this.info = data;
	}

	public JSONObject getJSONObjectFromJSONObject(JSONObject jsonObject, String data) {
		if (jsonObject == null) {
			Log.d(LOG_TAG, "getJSONObjectFromJSONObject : " + Constants.JSON_OBJECT_ERROR);
			return null;
		}
		if (data == null) {
			Log.d(LOG_TAG, "getJSONObjectFromJSONObject : " + Constants.STRING_ERROR);
			return null;
		}
		try {
			jsonObject = jsonObject.getJSONObject(data);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	public JSONObject createJsonObject(String data) {
		if (data == null) {
			Log.d(LOG_TAG, "getJsonObjectFromString : " + Constants.STRING_ERROR);
			return null;
		}
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(data);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	public JSONArray getJSONArrayFromJSONObject(JSONObject jsonObject, String data) {
		if (jsonObject == null) {
			Log.d(LOG_TAG, "getJSONArrayFromJSONObject : " + Constants.JSON_OBJECT_ERROR);
			return null;
		}
		if (data == null) {
			Log.d(LOG_TAG, "getJSONArrayFromJSONObject : " + Constants.STRING_ERROR);
			return null;
		}
		JSONArray jsonArray = new JSONArray();
		try {
			jsonArray = jsonObject.getJSONArray(data);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}

	public String getString(JSONObject jsonObject, String data) {
		if (jsonObject == null) {
			Log.d(LOG_TAG, "getString : " + Constants.JSON_OBJECT_ERROR);
			return null;
		}
		if (data == null) {
			Log.d(LOG_TAG, "getString : " + Constants.STRING_ERROR);
			return null;
		}
		try {
			info = jsonObject.getString(data);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return info;
	}

	public ArrayList<JSONObject> getJSONObjectArray(JSONArray jsonArray) {
		if (jsonArray == null) {
			Log.d(LOG_TAG, "getJSONObjectArray : " + Constants.JSON_ARRAY_ERROR);
			return null;
		}
		try {
			for (int i = 0; i < jsonArray.length(); i++) {
				jsArray.add(jsonArray.getJSONObject(i));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsArray;
	}

}