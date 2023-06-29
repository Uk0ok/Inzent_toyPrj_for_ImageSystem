package com.inzent.toy.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.inzent.toy.entity.PageVo;

@Mapper
public interface InquiryMapper {
	// Common
	public int countCommonWorksAfterSearch(Map<String, Object> commonJson);
	public List<Map<String, Object>> selectCommonWorksByElemnts(Map<String, Object> commonJson, PageVo pVo);

	// Deposit
	public int countDepositWorksAfterSearch(Map<String, Object> depositJson);
	public List<Map<String, Object>> selectDepositWorksByElemnts(Map<String, Object> depositJson, PageVo pVo);
	
	// Loan
	public int countLoanWorksAfterSearch(Map<String, Object> loanJson);
	public List<Map<String, Object>> selectLoanWorksByElemnts(Map<String, Object> loanJson, PageVo pVo);
	
	// 공통
	public List<Map<String, Object>> selectImageTable(String listToyKey);
	public void deleteImage(String elementIdDel);
	public Map<String, String> selectFileNmExtension(String elementIdDown);
}
