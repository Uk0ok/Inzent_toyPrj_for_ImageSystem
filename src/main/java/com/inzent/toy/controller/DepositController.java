package com.inzent.toy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.inzent.toy.entity.DepositDto;
import com.inzent.toy.mapper.DepositMapper;
import com.inzent.toy.service.DepositService;

import lombok.extern.slf4j.Slf4j;

/**
 * @packageName   : com.inzent.toy.controller
 * @fileName      : DepositController.java
 * @author        : uk0ok
 * @version       : 1.0
 * @date          : 2023.05.24
 * @description   : 수신 업무 controller
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023.05.24        uk0ok            최초 생성
 */
@Slf4j
@Controller
@RequestMapping("/toy")
public class DepositController {
	
	@Autowired
	private DepositService dService;
	
	@Autowired
	private DepositMapper dMapper;

	@RequestMapping(value = "/deposit", method = RequestMethod.GET)
	public String deposit(Model model) throws Exception {
		List<String> eCodeList = dService.getECodeList();
		model.addAttribute("employeeList", eCodeList);

		// return "/toy/deposit";
		return "toy/deposit";
	}

	@RequestMapping(value = "/deposit", method = RequestMethod.POST)
	public String deposit(
		@RequestPart(value = "transactionFiles", required = false) List<MultipartFile> transactionFiles, 
		@RequestPart(value = "depositBalanceFiles", required = false) List<MultipartFile> depositBalanceFiles, 
		@RequestPart(value = "attorneyFiles", required = false) List<MultipartFile> attorneyFiles, 
		@RequestPart(value = "automaticFiles", required = false) List<MultipartFile> automaticFiles, 
		@RequestPart(value = "reissuanceFiles", required = false) List<MultipartFile> reissuanceFiles, 
		@RequestPart(value = "electronicFiles", required = false) List<MultipartFile> electronicFiles, 
		@RequestPart(value = "consentFiles", required = false) List<MultipartFile> consentFiles, 
		@RequestPart(value = "cmsFiles", required = false) List<MultipartFile> cmsFiles, 
		@RequestPart(value = "judgingFiles", required = false) List<MultipartFile> judgingFiles, 
		@RequestPart(value = "exclusionFiles", required = false) List<MultipartFile> exclusionFiles, 
		@RequestPart(value = "depositDto") DepositDto depositDto) {

		log.info("post deposit work!");

		List<String> rrnList = new ArrayList<>();
		List<String> eidList = new ArrayList<>();
		String custNo = "";

		// System.out.println(depositDto.getRrnNo());

		String toyKey = dService.createToyKey();
		
		if (dMapper.getRrnNoList() == null || dMapper.getRrnNoList().size() == 0) {
			System.out.println("there's no rrnNo!");
		} else {
			rrnList = dMapper.getRrnNoList();
			// System.out.println(rrnList);
			// System.out.println(depositDto.getRrnNo());
		}
		if (rrnList != null  && !rrnList.isEmpty() && rrnList.contains(depositDto.getRrnNo())) {
			custNo = dMapper.getCustNo(depositDto.getRrnNo());
		} else {
			custNo = dService.createCustNo();
		}

		// System.out.println(toyKey);
		// System.out.println(custNo);
		
		// 파일을 등록 후 IMAGE_TB에 정보 등록
		if (transactionFiles != null && !transactionFiles.isEmpty()) {
			eidList = dService.create(transactionFiles, depositDto);
			dService.insertToImageTable(transactionFiles, eidList, depositDto, toyKey, "02001001");
		}
		if (depositBalanceFiles != null && !depositBalanceFiles.isEmpty()) {
			eidList = dService.create(depositBalanceFiles, depositDto);
			dService.insertToImageTable(depositBalanceFiles, eidList, depositDto, toyKey, "02002001");
		}
		if (attorneyFiles != null && !attorneyFiles.isEmpty()) {
			eidList = dService.create(attorneyFiles, depositDto);
			dService.insertToImageTable(attorneyFiles, eidList, depositDto, toyKey, "02003001");
		}
		if (automaticFiles != null && !automaticFiles.isEmpty()) {
			eidList = dService.create(automaticFiles, depositDto);
			dService.insertToImageTable(automaticFiles, eidList, depositDto, toyKey, "02004001");
		}
		if (reissuanceFiles != null && !reissuanceFiles.isEmpty()) {
			eidList = dService.create(reissuanceFiles, depositDto);
			dService.insertToImageTable(reissuanceFiles, eidList, depositDto, toyKey, "02005001");
		}
		if (electronicFiles != null && !electronicFiles.isEmpty()) {
			eidList = dService.create(electronicFiles, depositDto);
			dService.insertToImageTable(electronicFiles, eidList, depositDto, toyKey, "02006001");
		}
		if (consentFiles != null && !consentFiles.isEmpty()) {
			eidList = dService.create(consentFiles, depositDto);
			dService.insertToImageTable(consentFiles, eidList, depositDto, toyKey, "02007001");
		}
		if (cmsFiles != null && !cmsFiles.isEmpty()) {
			eidList = dService.create(cmsFiles, depositDto);
			dService.insertToImageTable(cmsFiles, eidList, depositDto, toyKey, "02008001");
		}
		if (judgingFiles != null && !judgingFiles.isEmpty()) {
			eidList = dService.create(judgingFiles, depositDto);
			dService.insertToImageTable(judgingFiles, eidList, depositDto, toyKey, "02009001");
		}
		if (exclusionFiles != null && !exclusionFiles.isEmpty()) {
			eidList = dService.create(exclusionFiles, depositDto);
			dService.insertToImageTable(exclusionFiles, eidList, depositDto, toyKey, "02010001");
		}
		// DEPOSIT_TB에 insert
		dService.insertToDepositTable(depositDto, toyKey, custNo);

		log.info("post deposit done!");

		// return "/toy/deposit";
		return "toy/deposit";
	}
}
	
