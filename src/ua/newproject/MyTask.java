package ua.newproject;

import android.os.AsyncTask;
import android.util.Log;

public class MyTask extends AsyncTask<String, Void, Object> {

	private final String	LOG_TAG	= getClass().getSimpleName();
	
	@Override
	protected Object doInBackground(String... urls) {
		
		Object responseObject = null;
		for (String url : urls) {
			responseObject = new NetRequest().getResponseString(url);
		}
		Log.d(LOG_TAG, "responseObject : " + responseObject.toString());

		return responseObject;
	}
}