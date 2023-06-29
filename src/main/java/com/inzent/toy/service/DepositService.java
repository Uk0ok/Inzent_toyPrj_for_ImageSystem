package com.inzent.toy.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.inzent.toy.entity.DepositDto;

public interface DepositService {
	public List<String> getECodeList();
	public String createToyKey();
	public String createCustNo();
	public List<String> create(List<MultipartFile> files, DepositDto depositDto);
	public void insertToDepositTable(DepositDto depositDto, String toyKey, String custNo);
	public void insertToImageTable(List<MultipartFile> fileList, List<String> eidList, DepositDto depositDto, String toyKey, String docCode);
	
}
