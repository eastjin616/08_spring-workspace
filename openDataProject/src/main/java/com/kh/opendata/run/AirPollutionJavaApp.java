package com.kh.opendata.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.opendata.model.vo.AirVo;

public class AirPollutionJavaApp {

   // 발급받은 인증키 정보 변수에 담아두기
   public static final String servicekey = "U02uX7TWYMipQZdwIgIXFCBgiweQj4IhyqFruKsW2XtPQ0JwfBtrr7SNOODQnPcZK1K8q%2BoK4htDcCcYdGhnwQ%3D%3D";
   
   public static void main(String[] args) throws IOException {

      
      // OPEN API 서버로 요청하고자 하는 URL 만들기
      String url = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
      //url += "?servicekey=서비스키"; // 서비스키가 제대로 부여되지 않았을 경우 => SERVICE_KEY_IS_NOT_REGISTERED_ERROR 오류 발생
      url += "?servicekey=" + servicekey; // 서비스키가 제대로 부여되지 않았을 경우 => SERVICE_KEY_IS_NOT_REGISTERED_ERROR 오류 발생
      url += "&sidoName=" + URLEncoder.encode("서울", "UTF-8"); // 요청시 전달값 중 한글이 있을경우 encoding 해야됨
      url += "&returnType=json";

      // * HttpURLConnection 객체를 활용해서 OPEN API 요청 절차
      // 1. 요청하고자 하는 url 전달하면서 java.net.URL 객체 생성
      URL requestUrl = new URL(url);
      
      // 2. 1번 과정으로 생성된 URL객체를 가지고 HttpURLConnection 객체 생성
      HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
      
      // 3. 요청에 필요한 Header 설정하기
      urlConnection.setRequestMethod("GET");
      
      // 4. 해당 OPEN API 서버로 요청 보낸 후 입력 스트림을 통해 응답데이터 읽어들이기
      BufferedReader br = new BufferedReader( new InputStreamReader(urlConnection.getInputStream()));
      
      String responseText = "";
      String line;
      while((line = br.readLine()) != null) {
    	  responseText += line;
      }
      
      /*
       * 데이터 형식
       * {
	"response":
		{
			"body":
			{	
				"totalCount":40,
				"items":[
					{
						"so2Grade":"1",
						"coFlag":null,
						"khaiValue":"75",
						"so2Value":"0.003",
						"coValue":"0.4",
						"pm10Flag":null,
						"o3Grade":"2",
						"pm10Value":"65",
						"khaiGrade":"2",
						"sidoName":"서울",
						"no2Flag":null,
						"no2Grade":"1",
						"o3Flag":null,
						"so2Flag":null,
						"dataTime":"2025-04-18 12:00",
						"coGrade":"1",
						"no2Value":"0.024",
						"stationName":"중구",
						"pm10Grade":"2",
						"o3Value":"0.041"
						}
       */
      
      //JsonObject, JsonArray, JsonElement 이용해서 파싱 할 수 있음 => 내가 원하는 ㄷ데이터만을 추천
      //각각의 item 정보를 => AirVO 객체에 담고 => ArrayList 차곡차곡쌓기
      
      JsonObject totalObj = JsonParser.parseString(responseText).getAsJsonObject();
      JsonObject resopnseObj = totalObj.getAsJsonObject("response");//response 속성 접근 = {} JsonObject
      JsonObject bodyObj = resopnseObj.getAsJsonObject("body");//body속성 접근

      int totalCount = bodyObj.get("totalCount").getAsInt();
      JsonArray itemArr = bodyObj.getAsJsonArray("items"); //items속성접근 => []JsonArray
      
      ArrayList<AirVo> list = new ArrayList<AirVo>();
      
      for(int i =0; i<itemArr.size(); i++) {
    	  JsonObject item = itemArr.get(i).getAsJsonObject();
    	  
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
      
      for(AirVo a: list) {
    	  System.out.println(a);
      }
      
      //5. 다 사용한 스트림 반납
      br.close();
      urlConnection.disconnect();
   }

}
