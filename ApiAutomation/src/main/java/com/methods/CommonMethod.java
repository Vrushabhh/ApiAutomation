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
	public void validate() throws Exception {
		ExcelHandler excel = new ExcelHandler("./src/main/resources/TestData/TestData.xlsx");
		
		ArrayList<String> headers = excel.getRowData("Sheet1", 1);
		System.out.println(headers);
		ArrayList<String> inputData = excel.getRowData("Sheet1",2);
		System.out.println(inputData);
		HashMap<String, String> dataSet = new HashMap<String, String>();
		System.out.println(headers.get(0));
		System.out.println(inputData.get(0));
		System.out.println(headers.size());
		System.out.println(inputData.size());
		
//		// map all input data value with headers
		for (int j = 0; j < headers.size(); j++) {
			dataSet.put(headers.get(j), inputData.get(j));
//			if(inputData.get(j).equalsIgnoreCase("")){
//			dataSet.put(headers.get(j), "");
////				throw new SkipException("No Data Available");
//			}
		}
		String URL = dataSet.get("URL");
		System.out.println(URL);
		String productId = dataSet.get("productId");
		String productVersion = dataSet.get("productVersion");
		String requestId = dataSet.get("requestId");
		String Method = dataSet.get("Method");
		String passcode = dataSet.get("passcode");
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("productId",productId);
		headerMap.put("productVersion",productVersion.replace("-",""));
		
		RestPostMethod post = new RestPostMethod();
		JsonObject body = new JsonObject();
		
		body.addProperty("passcode", passcode.replace("-",""));
		String body1 = body.toString();
		System.out.println("Body");
		System.out.println(body1);
		//Params
				URIBuilder builder = new URIBuilder(URL);
				builder.setParameter("requestId",requestId);
				
				URI uri = builder.build();
		HttpResponse response = RestPostMethod.PostMethod(uri.toString(), headerMap, body1 , false, 1, 1);
		HttpEntity HttpResponse1 = response.getEntity();
		String finalResponse = EntityUtils.toString(HttpResponse1);
		System.out.println("Final Output");
		System.out.println(finalResponse);
		
		
	}

}

