package ua.newproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ua.newproject.model.ItemModel;

import android.graphics.Bitmap;
import android.util.Log;

public class Util {

	private MyTask			mTask;
	final String LOG_TAG = getClass().getSimpleName();

	public Util() {
	}

	public void runTask(String url) {

		mTask = new MyTask();
		mTask.execute(url);
	}

	public Object getResponseObject() {

		if (mTask == null) {
			Log.d(LOG_TAG, "mTask is null");
		}
		Object responseObject = null;
		try {
			responseObject = mTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return responseObject;
	}

	Bitmap bitmap = null;
	
	public ArrayList<ItemModel> getItemModelArray() {
		runTask(Constants.BASE_URL);
		Object object = getResponseObject();
		String strResponse = (String) getStringFromResponseObject((String) object);
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = parser.createJsonObject(strResponse);
		JSONArray jsonArray = parser.getJSONArrayFromJSONObject(jsonObject,
				Constants.JSON_ARRAY_TAG);
		ArrayList<JSONObject> jsonObjectArray = parser.getJSONObjectArray(jsonArray);

		ArrayList<ItemModel> itemModelArray = new ArrayList<ItemModel>();

		for (JSONObject jsonObjectElement : jsonObjectArray) {
			ItemModel itemModel;
			try {
				itemModel = new ItemModel(jsonObjectElement);
				itemModelArray.add(itemModel);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return itemModelArray;
	}

	public String getString(String data, int position) {
		String url = Constants.BASE_URL + "?id=" + position;
		runTask(url);
		Object object = getResponseObject();
		// Log.d(LOG_TAG, "object = " + object.toString() + "\n"
		// + url);
		String strResponse = (String) object;
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = parser.createJsonObject(strResponse);
		JSONObject jsonObjectIn = parser.getJSONObjectFromJSONObject(jsonObject, "data");
		String str = parser.getString(jsonObjectIn, data);
		Log.d(LOG_TAG, "str= " + str);

		return str;
	}

	public HashMap<String, String> getStringHashMap(int position) {
		String url = Constants.BASE_URL + "?id=" + position;
		runTask(url);
		Object object = getResponseObject();
		String strResponse = (String) object;
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = parser.createJsonObject(strResponse);
		JSONObject jsonObjectIn = parser.getJSONObjectFromJSONObject(jsonObject, "data");
		// Log.d(LOG_TAG, "jsonObject = " + jsonObject.toString());
		HashMap<String, String> stringMap = new HashMap<String, String>();
		try {
			stringMap.put("title", jsonObjectIn.getString("title"));
			stringMap.put("body", jsonObjectIn.getString("body"));
			stringMap.put("address", jsonObjectIn.getString("adress"));
			stringMap.put("pictureURL", jsonObjectIn.getString("picture"));
			Log.d(LOG_TAG, "String = " + jsonObjectIn.getString("adress"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return stringMap;
	}

	public Object getStringFromResponseObject(Object responseObject) {
		return (String) responseObject;
	}

	public Bitmap getImageFromResponseObject(Object responseObject) {
		return (Bitmap) responseObject;
	}
}