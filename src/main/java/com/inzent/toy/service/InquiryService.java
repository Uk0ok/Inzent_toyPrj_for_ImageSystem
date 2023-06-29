package com.inzent.toy.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.InputStreamResource;

import com.inzent.toy.entity.PageVo;

public interface InquiryService {
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
	public void downloadImage(String elementId);
	public Map<String, String> selectFileNmExtension(String elementIdDown);
}
