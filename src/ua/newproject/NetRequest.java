package ua.newproject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class NetRequest {

	private final String	LOG_TAG	= getClass().getSimpleName();

	public NetRequest() {
	}
// later to be replaced by a HTTP-manager
	public String getResponseString(String URL) {
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//			connection.setConnectTimeout(Constants.TIMEOUT_LIMIT);
//			connection.setReadTimeout(Constants.TIMEOUT_LIMIT);
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream response = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(response));
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			Log.d(LOG_TAG, "ConnectTimeout : " + connection.getConnectTimeout() + "\n"
					+ "ConnectTimeout : " + connection.getReadTimeout() + "\n"
					+ "ResponseMessage : " + connection.getResponseMessage());
			br.close();
			connection.disconnect();
		} catch (Exception e) {
			Log.d(LOG_TAG, "error : " + e.getMessage());

			return Constants.NETWORK_CONNECTION_ERROR;
		}
		return sb.toString();
	}
}
