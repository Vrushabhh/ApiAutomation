package com.util;



import java.net.URI;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class RestPostMethod {
	public static HttpResponse PostMethod(String url,
			Map<String, String> headerMap, String body, boolean timeout,
			int conTimeOut, int soTimeOut) throws Exception {
		HttpClient client = null;
//		ReportLib.getLogger().info("testq1");
		if (timeout) {
			RequestConfig config = RequestConfig.custom()
					.setConnectTimeout(conTimeOut * 1000)
					.setSocketTimeout(soTimeOut * 1000)
					.setConnectionRequestTimeout(conTimeOut * 1000).build();
			client = HttpClientBuilder.create().setDefaultRequestConfig(config)
					.build();

		} else {
			client = HttpClientBuilder.create().build();
//			ReportLib.getLogger().info("testq2");
		}
//		ReportLib.getLogger().info(url);
		URI uri = new URIBuilder(url).build();
		
		HttpPost request = new HttpPost(uri);
		for (String key : headerMap.keySet()) {
			request.setHeader(key, headerMap.get(key));
		}
		
		if (isValidString(body)) {
			StringEntity entity = new StringEntity(body);
//		System.out.println(body);
			System.out.println(entity);
			request.setEntity(entity);
//			ReportLib.getLogger().info("testq2");
		}
//		ReportLib.getLogger().info("testq3");
		System.out.println(request);
		return client.execute(request);
	}
	public static boolean isValidString(String value) {
		if (value == null || value.trim().equals("")) {
//			ReportLib.getLogger().info("testq4");
			return false;
		}
		return true;
	}
}
