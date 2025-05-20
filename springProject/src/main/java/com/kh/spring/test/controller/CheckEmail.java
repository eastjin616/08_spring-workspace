package com.kh.spring.test.controller;



import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;



import com.kh.spring.test.model.MemberDAO;



@Controller

public class CheckEmail{

	@ResponseBody

	@RequestMapping(value = "/CheckEmail", produces = "text/html; charset=utf-8")

	public String CheckEmail(String email) {

	MemberDAO dao = new MemberDAO();

	boolean result = dao.isEmailExist(email);



	if(result) {

	// 7. DAO 로 부터 반환받은 값이 true 라면 클라이언트에게 "이미 사용중인 email 입니다." 라고 클라이언트에게 반환한다.

	return "이미 사용중인 email입니다.";



	}else {

	// 8. DAO 로 부터 반환받은 값이 false 라면 클라이언트에게 "사용 가능한 email 입니다." 라고 클라이언트에게 반환한다.



	return "사용 가능한 email입니다.";



	}

	}

}