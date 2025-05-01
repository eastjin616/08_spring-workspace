package com.kh.opendata.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.opendata.model.vo.AirVo;

public class AirPollutionJava {

	public static final String servicekey = "VSgXc34Vs94ijfKVTpeJr85n70S9Xq9bY8PLyw9wCrGAzraqE4Gj6ecSZ88ZSiuSvN3JC1aXQZ0NVZDGuuugoA%3D%3D";
	public static void main(String[] args) throws IOException {
		
		String url = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?servicekey=" + servicekey;
		url += "&sidoName="+ URLEncoder.encode("서울", "UTF-8");
		url += "&returnType=json";
		
		URL requestUrl = new URL(url);
		
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		
		urlConnection.setRequestMethod("GET");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String responseText = "";
		String line;
		while((line = br.readLine())!=null) {
			responseText += line;
		}
		System.out.println("응답 데이터 : " + responseText);
		
		JsonObject totalObj = JsonParser.parseString(responseText).getAsJsonObject();
		JsonObject responseObj = totalObj.getAsJsonObject("response");
		JsonObject bodyObj = responseObj.getAsJsonObject("body");
		
		int totalCount = bodyObj.get("totalCount").getAsInt();
		JsonArray itemaArr = bodyObj.getAsJsonArray("items");
		
		ArrayList<AirVo> list = new ArrayList<AirVo>();
		
		for(int i=0; i<itemaArr.size(); i++) {
			JsonObject item = itemaArr.get(i).getAsJsonObject();
			
			AirVo air = new AirVo();
			air.setStationName(item.get("stationName").getAsString());
			  air.setDataTime(item.get("dataTime").getAsString());
			  air.setKhaiValue(item.get("khaiValue").getAsString());
			  air.setPm10Value(item.get("pm10Value").getAsString());
			  air.setSo2Value(item.get("so2Value").getAsString());
			  air.setCoValue(item.get("coValue").getAsString());
			  air.setNo2Value(item.get("no2Value").getAsString());
			  air.setO3Value(item.get("o3Value").getAsString());
			  
			  list.add(air);
		}
	
		for(AirVo a : list) {
			System.out.println(a);
		}
		br.close();
		urlConnection.disconnect();
	
	
	}

}
