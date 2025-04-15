package com.kh.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.service.MemberServiceImpl;
import com.kh.spring.member.model.vo.Member;

@Controller //Controller 타입의 어노테이션을 붙여주면 빈 스캐능을 통해 자동으로 빈 등록
public class MemberController {
	
	
	//new MemberServiceImpl 대신 Spring이 관리하게 하려면
	//memberServiceImple이 Spring의 빈으로 등록되어있어야함(xml or 어노테이션)
	//Autowired타입으로 어노테이션 등록
	@Autowired 
	private MemberServiceImpl mService;
	@Autowired 
	private BCryptPasswordEncoder bcryptPasswordEncoder;

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
//	@RequestMapping("login.me")
//	public String loginMember(Member m, Model model, HttpSession session) {
//		
//		Member loginUser = mService.loginMember(m);
//		
//		if(loginUser == null) {
//			//로그인 실패 => 에러문구를 requestScope에 담고 에러페이지(/WEB-INF/views/common/errorPage.jsp)로 포월딩
//			model.addAttribute("errorMsg","로그인 실패");
//			return "common/errorPage";
//		}else { 
//			//로그인 성공 => loginUser를 sessionScope에 담고 메인페이지로 url 재요청
//			session.setAttribute("loginUser", loginUser);
//			return "redirect:/";//redirect : url재요청
//		}
//	}
//===================================================================================	
	
	/*
	 * 스프링에서 제공하는 ModelAndView 객체를 이용하는 공간
	 * 
	 * Model은 데이터를 key-value 세트로 담을 수 있는 공간이라고 한다면
	 * View는 응답뷰에 대한 정보를 담을 수 있는 공간
	 */
	@RequestMapping("login.me")
	public ModelAndView loginMember(Member m, HttpSession session, ModelAndView mv) {
		
		/*
		Member loginUser = mService.loginMember(m);
		
		if(loginUser == null) {
			//로그인 실패 => 에러문구를 requestScope에 담고 에러페이지(/WEB-INF/views/common/errorPage.jsp)로 포월딩
			mv.addObject("errorMsg", "로그인 실패");
			mv.setViewName("common/errorPage");
		}else { 
			//로그인 성공 => loginUser를 sessionScope에 담고 메인페이지로 url 재요청
			session.setAttribute("loginUser", loginUser);
			mv.setViewName("redirect:/");
		}
		
		return mv;
		=========================================================================================
		*/
		//암호화 작업 후에 해야하는 과정
		//Member m userId 필드 : 사용자가 입력한 아이디
		//         userPwd 필드 : 사용자가 입력한 비번(평문)
		Member loginUser = mService.loginMember(m);
		// loginUser : 오로지 아이디만을 가지고 조회한 회원
		// loginUserPwd 필드 : 암호화된 비번 
		
		if(loginUser != null && bcryptPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd())) {
			//로그인 성공
			session.setAttribute("loginUser", loginUser);
			mv.setViewName("redirect:/");
			
		}else {
			//로그인 실패
			mv.addObject("errorMsg", "로그인 실패");
			mv.setViewName("common/errorPage");
		}
		return mv;
	}
//===================로그아웃========================================================	
	
	@RequestMapping("logout.me")
	public String logoutMember(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
//===================회원가입폼========================================================	
	@RequestMapping("enrollForm.me")
	public String enrollForm() {
		// WEB-INF/views/ member.memberEnrollFrom .jsp
		return "member/memberEnrollForm";
	}
//===================회원가입========================================================	
	@RequestMapping("insert.me")
	public String insertMember(Member m, Model model, HttpSession session) {
		System.out.println(m);
		//1. 한글이 깨짐 = 스프링에서 제공하는 인코딩필터 등록
		//2. 나이를 입력하지 않았을 경우 "" 와 같은 빈문자열이 넘어오는데 int형 필드에 담을 수 없음. 400에러가 발생
		//	 => Member 클래스의 age필드를 int형 --> String형으로 변경
		//3. 비밀번호 사용자가 입력한 있는 그대로의 평문
		// => Bcrypt 방식의 암호화를 통해서 암호문으로 변경
		// => 1) 스프링 시큐리티 모듈에서 제공 (라이브러리 추가 pom.xml)
		// => 2) BcryptPasswordEncoder 라는 클래스를 빈으로 등록(xml파일에)
		// => 3) web.xml에 추가해서 서버가 켜지면 읽게(spring-security.xml) 해줬음
		
		//암호화 작업(암호문을 만들어내는 과정)
		String encPwd = bcryptPasswordEncoder.encode( m.getUserPwd());
		
		m.setUserPwd(encPwd);//Member 객체의 userPwd에 평문이 아닌 암호문으로 변경
		
		int result = mService.insertMember(m);
		
		if(result > 0) { //성공 => 메인페이지 url 재요청
			
			session.setAttribute("alertMsg", "성공적으로 회원가입 되었습니다.");
			return "redirect:/";
			
		}else { //실패 => 에러문고 담아서 에러페이지
			model.addAttribute("errorMsg","회원가입 실패");
			return "common/errorPage";
		}
	}
	
//===================마이페이지========================================================	
	
	@RequestMapping("myPage.me")
	public String myPage() {
		return "member/myPage";
	}
}









































