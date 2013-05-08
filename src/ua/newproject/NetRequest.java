package ua.newproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class NetRequest {

	final String LOG_TAG = getClass().getSimpleName();
    
    public NetRequest() {
    }

    public String getResponseString(String URL) {

	String result = "";
	HttpClient client = new DefaultHttpClient();
	HttpGet request = new HttpGet(URL);
	try {
	    HttpResponse response = client.execute(request);
	    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity()
		    .getContent()));

	    String line = "";
	    while ((line = reader.readLine()) != null) {
		result += line;
	    }
	} catch (ClientProtocolException e1) {
	    e1.printStackTrace();
	} catch (IOException e1) {
	    e1.printStackTrace();
	}
	if (result.isEmpty()) {
	    return null;
	}
	return result;
    }
}