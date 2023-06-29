package com.inzent.toy.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @packageName   : com.inzent.toy.entity
 * @fileName      : InquiryDto.java
 * @author        : uk0ok
 * @version       : 1.0
 * @date          : 2023.06.13
 * @description   : 조회에 사용하는 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023.06.13        uk0ok            최초 생성
 */
@ToString
@Data
public class InquiryDto {
	private String toyKey;
	private String custNm;
	private String rrnNo;
	// private String regDateFin;
}
