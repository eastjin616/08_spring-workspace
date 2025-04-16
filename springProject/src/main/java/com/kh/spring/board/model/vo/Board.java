package com.kh.spring.board.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor //기본생성자
@AllArgsConstructor//전체 매개변수 생성자
@Setter//세터
@Getter//게터
@ToString//투스트링
public class Board {

	private int boardNo;
    private String boardTitle;
    private String boardWriter;
    private String boardContent;
    private String originName;
    private String changeName;
    private int count;
    private String createDate;
    private String status;
    
    
}
