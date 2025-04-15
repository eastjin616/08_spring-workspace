package com.kh.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * * Lombok(롬복)
 * 
 * 1. 라이브러리 다운 후 적용(完)
 * 2. 다운로드된 jar파일을 찾아서 설치 (작업할 IDE 선택해서 설치) 
 * 3. IDE 재실행
 */
@NoArgsConstructor //기본생성자
@AllArgsConstructor//전체 매개변수 생성자
@Setter//세터
@Getter//게터
@ToString//투스트링
public class Member {

	private String userId;
	private String userPwd;
	private String userName;
	private String email;
	private String gender;
//	private int age;
	private String age;
	private String phone;
	private String address;
	private Date enrollDate;
	private Date modifyDate;
	private String Status;
	
	//롬복 쓸때 필드명 적어도 소문자 두글자 이상으로 시작要
	
}
