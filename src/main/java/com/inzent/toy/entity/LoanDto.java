package com.inzent.toy.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @packageName   : com.inzent.toy.entity
 * @fileName      : LoanDto.java
 * @author        : uk0ok
 * @version       : 1.0
 * @date          : 2023.06.12
 * @description   : 여신 업무에 사용하는 dto
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022.06.12        uk0ok            최초 생성
 */
@ToString
@Data
public class LoanDto {
	private String empCode;
	private String custName;
	private String rrnNo;
	private String description;
    private String cclassId;
    private String userClass;
    private String eclassId;
}
