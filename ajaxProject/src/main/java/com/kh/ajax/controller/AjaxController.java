package com.kh.ajax.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//빈등록 스프링이 관리
@Controller
public class AjaxController {
	
	/*
	 * 1.HttpServletResponse 객체로 응답데이터 응답하기 (기존의 jsp/servlet 때 했던 Stream을 이용한 방식)
	 */
/*
	@RequestMapping("ajax1.do")
	public void ajaxMethod1(String name, int age, HttpServletResponse response) throws IOException {
		System.out.println(name);
		System.out.println(age);
		
		//요청처리를 위해 서비스 호출
		
		//요청처리가 다 됐다는 가정하에 요청한 그 페이지에 응답할 데이터가 있을경우?
		String responseData = "응답 문자열 : " + name + "는" + age +"살 입니다.";
		
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().print(responseData);//이게 success에 result로 들어감
	}
*/
	
	/*
	 * 2. 응답할 데이터를 문자열로 리턴
	 * => HttpServletResponse
	 * 단, 문자열 리턴하면 원래는 포워딩 방식 => 응답뷰로 인식해서 해당 뷰 페이지를 찾을 것
	 * 따라서 내가 리턴하는 문자열이 응답뷰가 아닌 응답데이터야~라는걸 알려주는
	 * 어노테이션 @ResponseBody를 붙여햐함
	 * 
	 */
	@ResponseBody
	@RequestMapping(value="ajax1.do",produces="text/html; charset=utf-8")
	public String ajaxMethod1(String name, int age) {
		String responseData = name + "는" + age + "살 입니다.";
		return responseData;
	}
}
