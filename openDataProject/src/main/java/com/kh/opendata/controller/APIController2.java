package com.kh.opendata.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class APIController2 {

	private static final String serviceKey = "VSgXc34Vs94ijfKVTpeJr85n70S9Xq9bY8PLyw9wCrGAzraqE4Gj6ecSZ88ZSiuSvN3JC1aXQZ0NVZDGuuugoA%3D%3D";

	@ResponseBody
	@RequestMapping(value = "air.do", produces ="application/json; charset=utf-8")
	public String airPollution(String location) throws IOException {
		String url = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + serviceKey;
		url += "&sidoName=" + URLEncoder.encode(location,"utf-8");
		url += "&returnType=json";
		
		URL requestUrl = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		urlConnection.setRequestMethod("GET");
		
		BufferedReader br = new BufferedReader(new InputStreamReader( urlConnection.getInputStream()));
		
		String responseText = "";
		String line;
		while((line=br.readLine()) != null) {
			responseText += line;
		}
		
		br.close();
		urlConnection.disconnect();

		return responseText;

	}
}
