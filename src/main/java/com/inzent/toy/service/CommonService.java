package com.inzent.toy.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.inzent.toy.entity.CommonDto;

public interface CommonService {

	public List<String> getECodeList();
	public String createToyKey();
	public String createCustNo();
	public List<String> create(List<MultipartFile> files, CommonDto commonDto);
	public void insertToCommonTable(CommonDto commonDto, String toyKey, String custNo);
	public void insertToImageTable(List<MultipartFile> fileList, List<String> eidList, CommonDto commonDto, String toyKey, String docCode);
}
