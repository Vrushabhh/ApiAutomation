package com.util;



import java.net.URI;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

public class RestGetMethod {

	public static HttpResponse GetMethod(URI uri,
			Map<String, String> headerMap, boolean isTimeOutSet,
			int timeOutInSeconds) throws Exception {
		HttpClient client = null;
		if (isTimeOutSet) {
			RequestConfig config = RequestConfig.custom()
					.setConnectTimeout(timeOutInSeconds * 1000)
					.setSocketTimeout(timeOutInSeconds * 1000)
					.setConnectionRequestTimeout(timeOutInSeconds * 1000)
					.build();
			client = HttpClientBuilder.create().setDefaultRequestConfig(config)
					.build();

		} else {
			
			client = HttpClientBuilder.create().build();
		}
//		URI uri = new URIBuilder(builder).build();
		HttpGet request = new HttpGet(uri);
		
		for (String key : headerMap.keySet()) {
			request.setHeader(key, headerMap.get(key));
		}
		
		return client.execute(request);
	}

}
