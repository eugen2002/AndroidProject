package ua.newproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ua.newproject.model.ItemModel;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

public class Util {

	private MyTask			mTask	= null;
	private final String	LOG_TAG	= getClass().getSimpleName();

	public void runTask(String url) {
		mTask = new MyTask();
		mTask.execute(url);
	}

	public Object getResponseObject() {

		if (mTask == null) {
			Log.d(LOG_TAG, "mTask is null");
			return null;
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

	public Object getItemModelArray(Context context) {
		ArrayList<ItemModel> itemModelArray = new ArrayList<ItemModel>();
		runTask(Constants.BASE_URL);
		Object responseObject = getResponseObject();
		String responseString = responseObject.toString();
		if (responseString.equals(Constants.NETWORK_CONNECTION_ERROR)) {
			Log.d(LOG_TAG, "responseString = " + responseString);
			return responseObject;
		} else {
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = parser.createJsonObject(responseString);
			JSONArray jsonArray = parser.getJSONArrayFromJSONObject(jsonObject,
					Constants.JSON_ARRAY_TAG);
			ArrayList<JSONObject> jsonObjectArray = parser.getJSONObjectArray(jsonArray);

			itemModelArray = new ArrayList<ItemModel>();

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
	}
	private ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();

	public ArrayList<Bitmap> getBitmapArray() {
		return bitmapArray;
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

	public Object getStringHashMap(int position) {
		String url = Constants.BASE_URL + "?id=" + position;
		runTask(url);
		Object responseObject = getResponseObject();
		String responseString = responseObject.toString();
		if (responseString.equals(Constants.NETWORK_CONNECTION_ERROR)) {
			return responseObject;
		} else {
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = parser.createJsonObject(responseString);
			JSONObject jsonObjectIn = parser.getJSONObjectFromJSONObject(jsonObject, "data");
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
	}
}