package com.kh.spring.member.model.service;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;

@Service
public class MemberServiceImpl implements MemberService {
	
	//빈으로 등록을 한 상태로
	//	@Autowired 해야함
	@Autowired
	private MemberDao mDao;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
//================로그인========================
	
	@Override
	public Member loginMember(Member m) {


		Member loginUser = mDao.loginMember(sqlSession, m);
		return loginUser;
	}
//===============================================
	@Override
	public int insertMember(Member m) { 
		
		int result = mDao.insertMember(sqlSession, m);
		return result;
	}
//============회원정보변경=========================
	@Override
	public int updateMember(Member m) {
		
		int result = mDao.updateMember(sqlSession, m);
		return result;
		
	}
//=================회원삭제===========================
	@Override
	public int deleteMember(String userId) {
		
		int result = mDao.deleteMember(sqlSession, userId);
		return result;
	}
//===============================================
	@Override
	public int idCheck(String checkId) {
		return 0;
	}

}
