package com.kh.opendata.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller

public class test {
	public static final String serviceKey = "VSgXc34Vs94ijfKVTpeJr85n70S9Xq9bY8PLyw9wCrGAzraqE4Gj6ecSZ88ZSiuSvN3JC1aXQZ0NVZDGuuugoA%3D%3D";
	
	@ResponseBody
//	@RequestMapping(value="zizn.do", produces="text/xml; charset=utf-8")
	@RequestMapping(value="zizn.do", produces="application/json; charset=utf-8")
	public String testzizin() throws IOException {
		
		String url = "https://apis.data.go.kr/1741000/EarthquakeIndoors3/getEarthquakeIndoors3List";
		url += "?serviceKey=" + serviceKey;
		url += "&numOfRows=2";
//		url += "&returnType=xml";
		url += "&returnType=json";
		
		System.out.println(url);
		
		URL requestUrl = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		urlConnection.setRequestMethod("GET");
		
		BufferedReader br = new BufferedReader(new InputStreamReader( urlConnection.getInputStream()));
		
		String responseText="";
		String line;
		while((line = br.readLine())!= null) {
			responseText += line;
		}
	
		br.close();
		urlConnection.disconnect();
		
		return responseText;
	}
	
	
}
