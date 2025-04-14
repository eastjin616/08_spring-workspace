package com.kh.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.member.model.service.MemberServiceImpl;
import com.kh.spring.member.model.vo.Member;

@Controller //Controller 타입의 어노테이션을 붙여주면 빈 스캐능을 통해 자동으로 빈 등록
public class MemberController {
	
	@Autowired //DI(Dependency Injection) 특징
	//MemberSeriveImpl이 필요할때 생성해달라는 뜻의 어노테이션
	private MemberServiceImpl mService;

//	@RequestMapping(value="login.me")//RequestMapping 타입의 어노테이션을 붙여줌으로써 HandlerMapping 등록
//	public void loginMember() {
//		
//	}
//===================================	
//	@RequestMapping(value="insert.me")
//	public void insertMember() {
//			
//	}
//===================================			
//	public void updateMember() {
//			
//	}
	
	
	/*
	 * 파라미터(요청시 전달값)를 받는 방법(요청시 전달되는 값들 처리하는 방법)
	 * 
	 * 1. HttpServletReqeust를 이용해서 전달받기 (기존의 jsp/servlet때의 방식)
	 * 	  해당 메소드의 매개변수로 HttpServletRequest를 작성해두면
	 * 	  스프링 컨테이너가 해당 메소드 호출 시 (실행시) 자동으로 객체를 생성해서 인자로 주입해줌
	 * 
	 */
	
	/*
	@RequestMapping("login.me")//속성값이 value 이고 값이 1개만 온다? 그러면 value 를 생략해도 된다. 
	public String loginMember(HttpServletRequest request) {

	        String userId = request.getParameter("id");
	        String userPwd = request.getParameter("pwd");

	        System.out.println("id : " + userId);
	        System.out.println("pwd : " + userPwd);

	        return "main";
	    }
	*/
	
//================================================================================================	
	
	/*
	 * 2. @RequestPram 어노테이션을 이요하는 방법
	 * request.getParameter("키") : 벨류의 역할을 대신해주는 어노테이션
	 */
	
	/*
	@RequestMapping("login.me")//속성값이 value 이고 값이 1개만 온다? 그러면 value 를 생략해도 된다. 
	public String loginMember(@RequestParam(value="id", defaultValue ="aaa") String userId,
							  @RequestParam(value="pwd") String userPwd) {


        System.out.println("id : " + userId);
        System.out.println("pwd : " + userPwd);

	        return "main";
	    }
	    */
	
//================================================================================================		
	/*
	 * 3. @RequestPram 어노테이션을 생략하는 방법
	 * 	  단 매개변수명은 jsp상의 name값과 동일하게 셋팅해야지만 자동으로 값이 주입됨
	 */
	
	/*
	@RequestMapping("login.me")
	public String loginMember(String id, String pwd) {
		
//      System.out.println("id : " +id);
//      System.out.println("pwd : " + pwd);
      
      Member m = new Member();
      m.setUserId(id);
      m.setUserId(pwd);

//      Service 쪽 메소드에 m을 전달하면서 조회
		
		return "main";
	}
*/
	
//================================================================================================		
	/*
	 * 4. 커맨드 객체 방식
	 *    해당 메소드의 매개변수로
	 *    요청시 전달값을 담고자 하는 vo클래스 타입을 세팅 후
	 *    요청시 전달값의 키값(jsp의 name값)을 vo클래스에 담고자하는 필드명으로 작성
	 *    
	 *    스프링 컨테이너가 해당 객체를 기본 생성자로 생성 한 후 setter메소드 찾아서 
	 *    요청시 전달값을 해당 필드에 담아주는 내부적인 원리
	 *    
	 *    ** 반드시 name속성값(키값)과 담고자하는 필드명 동일해야함.
	 */	

//	@RequestMapping("login.me")
//	public String loginMember(Member m) {
//		
//		Member loginUser = mService.loginMember(m);
//		
//		if(loginUser == null) {//로그인 실패 => 에러문구를 requestScope에 담고 에러페이지로 포월딩
//			System.out.println("로실");
//		}else { //로그인 성공 => loginUser를 sessionScope에 담고 메인페이지로 url 재요청
//			System.out.println("로성");
//			
//		}
//		
//		return "main";
//	}
	
	
//	---------------------------------------------------------------------------
	
	/*
	 * 요청 처리 후 응답페이지로 포워딩 또는 url재요청, 응답데이터 담는 방법
	 * 
	 * 1. 스프링에서 제공하는 Model 객체를 사용하는 방법
	 *    포워딩할 뷰로 전달하고자 하는 데이터를 맵형식(key-value)으로 담을 수 있는 영역
	 * 	  Model 객체는 requestScope 이다.
	 * 	  단, setAttribute()가 아닌 addAttribute() 메소드 이용
	 * 
	 * 
	 */
//============================로그인=============================================	
	@RequestMapping("login.me")
	public String loginMember(Member m, Model model, HttpSession session) {
		
		Member loginUser = mService.loginMember(m);
		
		if(loginUser == null) {
			//로그인 실패 => 에러문구를 requestScope에 담고 에러페이지(/WEB-INF/views/common/errorPage.jsp)로 포월딩
			model.addAttribute("errorMsg","로그인 실패");
			return "common/errorPage";
		}else { 
			//로그인 성공 => loginUser를 sessionScope에 담고 메인페이지로 url 재요청
			session.setAttribute("loginUser", loginUser);
			return "redirect:/";//redirect : url재요청
		}
	}
//===================================================================================	
	
	
	
}
