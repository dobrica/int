package com.example.restclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class ServiceHandler {

	public final static int GET = 1;
	public final static int POST = 2;

	public ServiceHandler() {

	}

	/* Making service call
	 * url to make request
	 * method - http request method*/
	
	public String makeServiceCall(String url, int method) {
		String response = null;
		try {
			// http client
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpEntity httpEntity = null;
			HttpResponse httpResponse = null;

			// Checking http request method type
			if (method == POST) {

				HttpPost httpPost = new HttpPost(url);
				httpResponse = httpClient.execute(httpPost);

				httpEntity = httpResponse.getEntity();
				response = EntityUtils.toString(httpEntity);

			} else if (method == GET) {

				HttpGet httpGet = new HttpGet(url);

				httpResponse = httpClient.execute(httpGet);

				httpEntity = httpResponse.getEntity();
				response = EntityUtils.toString(httpEntity);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;

	}

}