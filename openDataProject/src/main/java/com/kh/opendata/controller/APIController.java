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
public class APIController {
	
	private static final String serviceKey = "0ex1ZeD6su2reN7ppo8aFlatOv2pWDaeEZ6pVOzJb%2BBBbjqpJs4kR1OkwAokxC2yitaLs7ziCVZzLV3PdZxZZA%3D%3D";
	
	/*
	@ResponseBody
	@RequestMapping(value = "air.do", produces ="application/json; charset=utf-8") //내가 보내는거 json타입이고 utf-8로 보내줄게
	public String airPollution(String location) throws IOException {
		String url = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + serviceKey;
		url += "&sidoName=" + URLEncoder.encode(location,"utf-8");
		url += "&returnType=json";
		url += "&numOfRows=50";
		
		URL requestUrl = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		urlConnection.setRequestMethod("GET");
		
		BufferedReader br = new BufferedReader(new InputStreamReader( urlConnection.getInputStream()));
		//버퍼리더가 문자기반 스트림인데 url커넥션 겟 인풋스트림은 바이트기반이라서 젠더(InputStreamReader)을 달어서 변환해준거임
		
		String responseText = "";
		String line;
		while((line=br.readLine()) != null) {
			responseText += line;
		}
		
		br.close();
		urlConnection.disconnect();

		return responseText;

	}
	*/
	
	@ResponseBody
	@RequestMapping(value = "air.do", produces ="text/xml; charset=utf-8") //내가 보내는거 xml타입이고 utf-8로 보내줄게
	public String airPollution(String location) throws IOException {
		String url = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + serviceKey;
		url += "&sidoName=" + URLEncoder.encode(location,"utf-8");
		url += "&returnType=xml";
		url += "&numOfRows=50";
		
		URL requestUrl = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		urlConnection.setRequestMethod("GET");
		
		BufferedReader br = new BufferedReader(new InputStreamReader( urlConnection.getInputStream()));
		//버퍼리더가 문자기반 스트림인데 url커넥션 겟 인풋스트림은 바이트기반이라서 젠더(InputStreamReader)을 달어서 변환해준거임
		
		String responseText = "";
		String line;
		while((line=br.readLine()) != null) {
			responseText += line;
		}
		
		br.close();
		urlConnection.disconnect();

		return responseText;

	}
	
//===================정당===============================================================
//	@ResponseBody
//	@RequestMapping(value="jd.do", produces="application/json; charset=utf-8")
//	public String selectJd() throws IOException {
//		
//		String url = "https://apis.data.go.kr/9760000/CommonCodeService/getCommonPartyCodeList";
//		url+="?serviceKey=" + serviceKey;
//		url+="&sgId=20231011";
//		url+="&numOfRows=10";
//		url+="&resultType=json";
//		
//		URL requestUrl = new URL(url);
//		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
//		urlConnection.setRequestMethod("GET");
//		
//		BufferedReader br = new BufferedReader(new InputStreamReader( urlConnection.getInputStream()));
//		
//		String responseText="";
//		String line;
//		while((line = br.readLine())!= null) {
//			responseText += line;
//		}
//	
//		br.close();
//		urlConnection.disconnect();
//		
//		return responseText;
//
//	}
	
	@ResponseBody
	@RequestMapping(value="jd.do", produces="text/xml; charset=utf-8")
	public String selectJd() throws IOException {
		
		String url = "https://apis.data.go.kr/9760000/CommonCodeService/getCommonPartyCodeList";
		url+="?serviceKey=" + serviceKey;
		url+="&sgId=20231011";
		url+="&numOfRows=10";
		url+="&resultType=xml";
		
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
