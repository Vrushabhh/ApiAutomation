package com.methods;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;
import com.util.ExcelHandler;

import com.util.RestPostMethod;

public class CommonMethod {
	public HttpResponse validate(int rowNumber) throws Exception {
		ExcelHandler excel = new ExcelHandler("./src/main/resources/TestData/TestData.csv");

		ArrayList<String> headersURL = excel.getRowData("URL", 1);
		System.out.println(headersURL);
		ArrayList<String> inputDataURL = excel.getRowData("URL", 2);
		System.out.println(inputDataURL);

		ArrayList<String> headersTestCases = excel.getRowData("TestCases", 1);
		System.out.println(headersTestCases);
		ArrayList<String> inputDataTestCases = excel.getRowData("TestCases", rowNumber);
		System.out.println(inputDataTestCases);

		HashMap<String, String> dataSetURL = new HashMap<String, String>();
		HashMap<String, String> dataSetTestCases = new HashMap<String, String>();

//		// map all input data value with headers for URL sheet
		for (int j = 0; j < headersURL.size(); j++) {
			dataSetURL.put(headersURL.get(j), inputDataURL.get(j));
		}

		// map all input data value with headers for TestCases sheet
		for (int j = 0; j < headersTestCases.size(); j++) {
			dataSetTestCases.put(headersTestCases.get(j), inputDataTestCases.get(j));
		}

		String URL = dataSetURL.get("URL");
		System.out.println(URL);
		String productId = dataSetTestCases.get("productId");
		String productVersion = dataSetTestCases.get("productVersion");
		String requestId = dataSetTestCases.get("requestId");
		String Method = dataSetTestCases.get("Method");
		String passcode = dataSetTestCases.get("passcode");
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("productId", productId);
		headerMap.put("productVersion", productVersion);

		RestPostMethod post = new RestPostMethod();
		JsonObject body = new JsonObject();

		body.addProperty("passcode", passcode);
		String body1 = body.toString();
		System.out.println("Body");
		System.out.println(body1);
		// Params
		URIBuilder builder = new URIBuilder(URL);
		builder.setParameter("requestId", requestId);

		URI uri = builder.build();
		System.out.println(uri);
		HttpResponse response = RestPostMethod.PostMethod(uri.toString(), headerMap, body1 , false, 1, 1);
		HttpEntity HttpResponse1 = response.getEntity();
		String finalResponse = EntityUtils.toString(HttpResponse1);
		System.out.println(response.getStatusLine().getStatusCode());
		System.out.println("Final Output");
		System.out.println(finalResponse);
		return response;

	}

}
