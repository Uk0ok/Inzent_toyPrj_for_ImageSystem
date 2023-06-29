package com.inzent.toy.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @packageName   : com.inzent.toy.entity
 * @fileName      : PageVo.java
 * @author        : uk0ok
 * @version       : 1.0
 * @date          : 2023.06.13
 * @description   : Pageing 관리 Vo
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023.06.13        uk0ok            최초 생성
 */
@ToString
@Data
public class PageVo {
	private int     rowCount  = 15;    // 한 페이지 당 보여줄 게시물 개수
    private int     pageCount = 5;     // 한 블럭에 몇 개의 페이지 개수
    private int     totalCount;        // 총 게시물 개수
    private int     page;              // 현재 페이지

	private int     startPage = 1;     // 한 블럭의 시작 페이지: 기본 값 1 // ex) 1 2 3 4 5 일 때 1을 의미.
    private int     endPage;           // 한 블럭의 끝 페이지

	private int     totalPageCount;    // 총 페이지 개수

	private boolean isPrev    = false; // 다음 페이지로 이동하는 버튼 유무
    private boolean isNext    = false; // 이전 페이지로 이동하는 버튼 유무

	private int     offset;            // 얼만큼 끊어서 가져올 것인가.

	public PageVo(int totalCount, int page) {
        this.totalCount = totalCount;
        this.page = page;
	// 총 페이지 개수 구하기
	setTotalPageCount(totalCount, this.rowCount); 
	// 한 블럭의 첫 페이지 구하기
	setStartPage(this.startPage, page, this.pageCount);
	// 한 블럭의 끝 페이지 구하기
	setEndpage(this.startPage, this.pageCount, this.totalPageCount); 
	// 이전 블록 버튼 유무 판별하기
	isPrev(page, this.pageCount); 
	// 다음 블록 버튼 유무 판별하기
	isNext(this.endPage, this.totalPageCount); 
	// offset 구하기
	setOffset(page, this.rowCount); 
    }

    // 총 페이지 개수 구하기
    private void setTotalPageCount(int totalCount, int rowCount) {
        this.totalPageCount = (int) Math.ceil(totalCount * 1.0 / rowCount);
    }

    // 한 블럭의 첫 페이지 구하기
    private void setStartPage(int startPage, int page, int pageCount) {
        this.startPage = startPage + (((page - startPage) / pageCount) * pageCount);
    }

    // 한 블럭의 끝 페이지 구하기
    private void setEndpage(int startPage, int pageCount, int totalPageCount) {
        this.endPage = ((startPage - 1) + pageCount) < totalPageCount ? (startPage - 1) + pageCount : totalPageCount;
    }

    // 이전 블럭으로 이동할 버튼 생성 유무
    private void isPrev(int page, int pageCount) {
        this.isPrev = 1 < ((page * 1.0) / pageCount);
    }

    // 다음 블럭으로 이동할 버튼 생성 유무
    private void isNext(int endPage, int totalPageCount) {
        this.isNext = endPage < totalPageCount;
    }

    // offset 구하기 // 쿼리 select 시 끊어서 가져오기
    private void setOffset(int page, int rowCount) {
        this.offset = (page - 1) * rowCount;
    }
}
