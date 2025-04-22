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

public class test1 {

   public static final String servicekey = "U02uX7TWYMipQZdwIgIXFCBgiweQj4IhyqFruKsW2XtPQ0JwfBtrr7SNOODQnPcZK1K8q%2BoK4htDcCcYdGhnwQ%3D%3D";
   
   public static void main(String[] args) throws IOException {

      String url = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
      url += "?serviceKey="+servicekey;
      url += "&sidoName="+URLEncoder.encode("서울", "UTF-8");
      url += "&returnType=json";
      
      URL requestUrl = new URL(url);
      HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
      urlConnection.setRequestMethod("GET");
      
      BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
      String line = null;
      while((line = br.readLine()) != null) {
    	  System.out.println(line);
      }
      
      br.close();
      urlConnection.disconnect();
   }
}