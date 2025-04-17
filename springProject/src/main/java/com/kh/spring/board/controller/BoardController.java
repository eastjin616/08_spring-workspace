package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.board.model.service.BoardServiceImpl;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.pagination;

@Controller
public class BoardController {
	
	@Autowired
	private BoardServiceImpl bService;
	
	//메뉴바 클릭시 /list.bo (기본적으로 1페이지 요청)
	//페이징바 클릭시 /list.bo?cpage=요청하는 페이지수
	@RequestMapping("list.bo")
	public ModelAndView selectList(@RequestParam(value="cpage", defaultValue="1") int currentPage, ModelAndView mv) {
		
		int listCount = bService.selectListCount();
		
		PageInfo pi = pagination.getPageInfo(listCount, currentPage, 10, 5);
		
		ArrayList<Board> list = bService.selectList(pi);
		
		/*
		mv.addObject("pi", pi);
		mv.addObject("list", list);
		mv.setViewName("board/boardListView");
		*/
		
		  mv.addObject("pi", pi)
			.addObject("list", list)
			.setViewName("board/boardListView");
		
		
		return mv;
		
		//포워딩 할 뷰(/WEB-INF/views/board/boardListView.jsp
		
		
		//pi = PageInfo 객체
		//list = Arraylist<Board>
	}
	
//===================글쓰기=========================================================================
	
	@RequestMapping("enrollForm.bo")
	public String enrollForm() {
		//WEB-INF/views/     board/boardEnrollForm     .jsp
		return "board/boardEnrollForm";
	}
	
	@RequestMapping("insert.bo")
	public String insertBoard(Board b, MultipartFile upfile, HttpSession session, Model model) {
		
//		System.out.println(b);
//		System.out.println(upfile); //첨부파일은 선택했든 안했는 생성된 객체임(다만 filename에 원본명이 있냐 없냐, 사이즈가 0이냐 아니냐로 구분)
		
		//전달된 파일이 있을 경우 => 파일명 수정 작업 후 서버에 업로드 => 원본명, 서버에 업로드된 경로를 board b에 이어서 담기
		if(!upfile.getOriginalFilename().equals("")) {
			
			//파일명 수정 작업 후 서버에 업로드 시키기 (flower.png => "2025041709362257841.png")
			/*
			String originName = upfile.getOriginalFilename(); //"flower.png"
			
			String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			int ranNum = (int)(Math.random( )* 90000 + 10000);
			
			//원본에서 확장자 가져오기
			String ext = originName.substring(originName.lastIndexOf("."));
			
			String changeName = currentTime + ranNum + ext;
			
			//업로드 시키고자 하는 폴더의 물리적인 경로 알아내기
			String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
			
			try {
				upfile.transferTo(new File(savePath + changeName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			위에 내용 메소드 하나 파서 호출해서 쓰기
			*/
			String changeName = saveFile(upfile,session);
			
			//원본명, 서버에 업로드된 경로를 board b에 이어서 담기
			b.setOriginName(upfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/"+ changeName);
		}
		
		//넘어온 첨부파일 있을 경우 b : 제목, 작성자, 내용, 파일 원본명, 파일 저장경로
		//넘어온 첨부파일 없을 경우 b : 제목, 작성자, 내용
		int result = bService.insertBoard(b);
		
		if(result>0) { //성공 => 게시글 리스트페이지 (list.bo url재요청)
			session.setAttribute("alertMsg", "성공적으로 게시글 등록~");
			return "redirect:list.bo";
		}else {//실패 => 에러페이지 포워딩
			model.addAttribute("errorMsg", "게시글 등록 실패~");
			return "common/errorPage";
		}
		
	}
	
	/*
	 * 현재 넘언온 첨부파일 그 자체를 서버의 폴더에 저장시키는 역할
	 */
	public String saveFile(MultipartFile upfile, HttpSession session) {
		String originName = upfile.getOriginalFilename(); //"flower.png"
		
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		int ranNum = (int)(Math.random( )* 90000 + 10000);
		
		//원본에서 확장자 가져오기
		String ext = originName.substring(originName.lastIndexOf("."));
		
		String changeName = currentTime + ranNum + ext;
		
		//업로드 시키고자 하는 폴더의 물리적인 경로 알아내기
		String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
		
		try {
			upfile.transferTo(new File(savePath + changeName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return changeName;
	}

//==================글 상세 조회=====================================================
	
	//** 만약 다중파일 업로드 기능시?
	//   여러개의 input type="file" 요소에 다 동일한 키값으로 부여 (ex-> upfile)
	//   MultipartFile[] upfile로 받으면 됨니다 (0번 인덱스 부터 차곡차곡 첨부파일 담겨있음)
	
	
	@RequestMapping("detail.bo")
	public ModelAndView selectBoard(int bno, ModelAndView mv) {
		//bno에는 상세조회 하고자 하는 게시글 번호 담겨있음
		
		//해당 게시글 조회수 증가용 서비스 호출 결과 받기
		int result = bService.increaseCount(bno);
		
		//>>성공적으로 조회수 증가
		//		>>boardDetailView.jsp 상에 필요한 데이터 조회(게시글 상세정보 조회용 서비스 호출)
		
		//>>조회수 증가 실패
		//		>> 에러문구 담아서 에러페이지로 포워딩
		
		if(result>0) {
			Board b = bService.selectBoard(bno);
			mv.addObject("b",b).setViewName("board/boardDetailView");
		}else {
			mv.addObject("errorMsg","조회수 증가 실패~").setViewName("common/errorPage");
		}	
		return mv;
	}
	
//==================글 상세 조회(삭제)=====================================================

	@RequestMapping("delete.bo")
	public String deleeteBoard(int bno, String filePath, HttpSession session, Model model) { //filePath = "resources/xxx/xxx.png"
		int result = bService.deleteBoard(bno);
		
		if(result > 0) {
			//삭제 성공
			
			//첨부파일 있었을 경우 => 파일삭제
			if(!filePath.equals("")) {
				new File( session.getServletContext().getRealPath(filePath)).delete();
			}
			
			//게시판 리스트 페이지 list.bo로 url재요청
			session.setAttribute("alertMsg", "성공적으로 게시글 삭제되었습니다");
			return "redirect:list.bo";
			
		}else {//에러페이지
			//삭제실패
			model.addAttribute("errorMsg", "게시글 삭제 실패");
			return "common/errorPage";
		}
	}
//==================글 상세 조회(수정 폼)=====================================================

	@RequestMapping("updateForm.bo")
	public String updateForm(int bno, Model model) {
		model.addAttribute("b",bService.selectBoard(bno)); //bno로 조회한 결과를 b에다가 셋팅해줌
		return "board/boardUpdateForm";
	}
//==================글 상세 조회(수정)=====================================================	
	@RequestMapping("update.bo")
	public String updateBoard(Board b, MultipartFile reupfile, HttpSession session, Model model) {
		
		//새로 넘어온 첨부파일이 있을경우
		if(!reupfile.getOriginalFilename().equals("")) {
			
			//기존에 첨부파일이 있었을 경우 => 기존의 첨부파일 지우기
			if(b.getOriginName() != null) {
				new File(session.getServletContext().getRealPath(b.getChangeName())).delete();
			}
			
			//새로 넘어온 첨부파일 서버 업로드 시키기
			String changeName = saveFile(reupfile, session);
			
			//b에 새로 넘어온 첨부파일에 대한 원본명, 저장경로 담기
			b.setOriginName(reupfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName);
			
		}
		
		/*
		 * b에는 boardNo, boardTitle, boardContent 무조건 담겨있음.
		 * 
		 * 1.새로 첨부된 파일 x, 기존 첨부 파일 x
		 *   => originName : null, changeName : null
		 *   
		 * 2.새로 첨부된 파일 x, 기존 첨부 파일 o
		 *   => originName : 기존첨부파일원본명, changeName : 기존첨부파일저장경로
		 *   
		 * 3.새로 첨부된 파일 o, 기존 첨부 파일 x
		 *   => 새로 전달된 파일 서버에 업로드
		 *   => originName : 새로운 첨부파일 원본명, changeName : 새로운 첨부파일 저장경로
		 *   
		 * 4.새로 첨부된 파일 o , 기존 첨부 파일 o
		 *   => 기존 첨부파일 삭제 
		 *   => 새로 전달된 파일 서버에 업로드
		 */
		
		int result = bService.updateBoard(b);
		
		if(result > 0) {//수정성공 => 상세페이지 detail.bo
			session.setAttribute("alertMsg", "성공적으로 게시글 수정되엇음");
			return "redirect:detail.bo?bno=" + b.getBoardNo();
			
		}else {//수정실패 => 에러페이지
			model.addAttribute("errorMsg", "게시글 수정실패");
			return "common/errorPage";
		}
		
	}
	
}