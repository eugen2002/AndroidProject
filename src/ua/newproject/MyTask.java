package ua.newproject;

import android.os.AsyncTask;

public class MyTask extends AsyncTask<String, Void, Object> {
	@Override
	protected Object doInBackground(String... urls) {
		Object responseObject = null;
		for (String url : urls) {
			responseObject = new NetRequest().getResponseString(url);
		}
		return responseObject;
	}
}