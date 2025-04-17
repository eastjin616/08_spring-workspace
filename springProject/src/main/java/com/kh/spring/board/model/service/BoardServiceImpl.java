package com.kh.spring.board.model.service;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardDao bDao;

    @Autowired
    private SqlSessionTemplate sqlSession;
 //=========================게시판 리스트 조회==========================================
    @Override
    public int selectListCount() {
        return bDao.selectListCount(sqlSession);
    }
 //=========================페이징=======================================================
    @Override
    public ArrayList<Board> selectList(PageInfo pi) {
        return bDao.selectList(sqlSession, pi);
    }
//======================글쓰기==================================================
    @Override
    public int insertBoard(Board b) {
    	return bDao.insertBoard(sqlSession, b);
    }
    
//======================상세조회==================================================

    @Override
    public int increaseCount(int bno) {
    	return bDao.increaseCount(sqlSession, bno);
    }

    @Override
    public Board selectBoard(int bno) {
       return bDao.selectBoard(sqlSession, bno);
    }
//====================상세조회 글 (삭제)=============================
    @Override
    public int deleteBoard(int boardNo) {
    	return bDao.deleteBoard(sqlSession, boardNo);
    }
//====================상세조회 글 (수정)=============================
    @Override
    public int updateBoard(Board b) {
        return bDao.updateBoard(sqlSession, b);
    }

    @Override
    public ArrayList<Reply> selectReplyList(int boardNo) {
        return null;
    }

    @Override
    public int insertReply(Reply r) {
        return 0;
    }
}