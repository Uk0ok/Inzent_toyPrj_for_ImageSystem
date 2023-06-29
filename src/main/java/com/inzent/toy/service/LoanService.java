package com.inzent.toy.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.inzent.toy.entity.LoanDto;

public interface LoanService {
	public List<String> getECodeList();
	public String createToyKey();
	public String createCustNo();
	public List<String> create(List<MultipartFile> files, LoanDto loanDto);
	public void insertToLoanTable(LoanDto loanDto, String toyKey, String custNo);
	public void insertToImageTable(List<MultipartFile> fileList, List<String> eidList, LoanDto loanDto, String toyKey, String docCode);
}
