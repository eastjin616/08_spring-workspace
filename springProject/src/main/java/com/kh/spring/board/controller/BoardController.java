package com.kh.spring.board.controller;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
}

