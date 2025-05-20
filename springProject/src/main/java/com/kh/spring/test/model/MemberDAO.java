package com.kh.spring.test.model;



public class MemberDAO {

	// DAO 메소드 작성 (메소드명 : isEmailExist )

	//6. isEmailExist 함수는 전달받은 email이 member 테이블 내에 존재하는지 검사하고 그 결과를 boolean 형으로 반환한다.

	public boolean isEmailExist(String email){

		String [] existEmailList = {"w@w.test","w@w.test2","w@w.test3"};


		for(int i =0; i<existEmailList.length; i++) {

			if(existEmailList[i].equals(email)) {

				return true;

			}

		}

		return false;

	}

	}

