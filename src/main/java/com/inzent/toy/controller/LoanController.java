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

import com.inzent.toy.entity.LoanDto;
import com.inzent.toy.mapper.LoanMapper;
import com.inzent.toy.service.LoanService;

import lombok.extern.slf4j.Slf4j;

/**
 * @packageName   : com.inzent.toy.controller
 * @fileName      : LoanController.java
 * @author        : uk0ok
 * @version       : 1.0
 * @date          : 2023.06.12
 * @description   : 여신 업무 controller
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023.06.12        uk0ok            최초 생성
 */
@Slf4j
@Controller
@RequestMapping("/toy")
public class LoanController {
	
	@Autowired
	private LoanService lService;

	@Autowired
	private LoanMapper lMapper;

	@RequestMapping(value = "/loan", method = RequestMethod.GET)
	public String loan(Model model) throws Exception {
		List<String> eCodeList = lService.getECodeList();
		model.addAttribute("employeeList", eCodeList);

		// return "/toy/loan";
		return "toy/loan";
	}

	@RequestMapping(value = "/loan", method = RequestMethod.POST)
	public String loan(
		@RequestPart(value = "attachmentFiles", required = false) List<MultipartFile> attachmentFiles, 
		@RequestPart(value = "deliberationFiles", required = false) List<MultipartFile> deliberationFiles, 
		@RequestPart(value = "exemptDeliberationFiles", required = false) List<MultipartFile> exemptDeliberationFiles, 
		@RequestPart(value = "appraisalFiles", required = false) List<MultipartFile> appraisalFiles, 
		@RequestPart(value = "approvalFiles", required = false) List<MultipartFile> approvalFiles, 
		@RequestPart(value = "changeOfConditionsFiles", required = false) List<MultipartFile> changeOfConditionsFiles, 
		@RequestPart(value = "agreementFiles", required = false) List<MultipartFile> agreementFiles, 
		@RequestPart(value = "creditFiles", required = false) List<MultipartFile> creditFiles, 
		@RequestPart(value = "limitFiles", required = false) List<MultipartFile> limitFiles, 
		@RequestPart(value = "repaymentFiles", required = false) List<MultipartFile> repaymentFiles, 
		@RequestPart(value = "aptFiles", required = false) List<MultipartFile> aptFiles, 
		@RequestPart(value = "loanDto") LoanDto loanDto) {

		log.info("post loan work!");

		List<String> rrnList = new ArrayList<>();
		List<String> eidList = new ArrayList<>();
		String custNo = "";

		System.out.println(loanDto.getRrnNo());

		String toyKey = lService.createToyKey();
		
		if (lMapper.getRrnNoList() == null || lMapper.getRrnNoList().size() == 0) {
			System.out.println("there's no rrnNo!");
		} else {
			rrnList = lMapper.getRrnNoList();
		}
		String getRrnNoFromLoanDto = loanDto.getRrnNo();
		System.out.println(!rrnList.isEmpty());
		System.out.println(rrnList != null);
		System.out.println(rrnList.contains(loanDto.getRrnNo()));
		System.out.println(getRrnNoFromLoanDto);
		System.out.println(rrnList.contains(getRrnNoFromLoanDto));
		if (rrnList != null  && !rrnList.isEmpty() && rrnList.contains(loanDto.getRrnNo())) {
			System.out.println("custNo 가져옴");
			custNo = lMapper.getCustNo(loanDto.getRrnNo());
		} else {
			System.out.println("custNo 신규채번");
			custNo = lService.createCustNo();
		}

		System.out.println(toyKey);
		System.out.println(custNo);
		
		// 파일을 등록 후 IMAGE_TB에 정보 등록
		if (attachmentFiles != null && !attachmentFiles.isEmpty()) {
			eidList = lService.create(attachmentFiles, loanDto);
			lService.insertToImageTable(attachmentFiles, eidList, loanDto, toyKey, "03001001");
		}
		if (deliberationFiles != null && !deliberationFiles.isEmpty()) {
			eidList = lService.create(deliberationFiles, loanDto);
			lService.insertToImageTable(deliberationFiles, eidList, loanDto, toyKey, "03002001");
		}
		if (exemptDeliberationFiles != null && !exemptDeliberationFiles.isEmpty()) {
			eidList = lService.create(exemptDeliberationFiles, loanDto);
			lService.insertToImageTable(exemptDeliberationFiles, eidList, loanDto, toyKey, "03003001");
		}
		if (appraisalFiles != null && !appraisalFiles.isEmpty()) {
			eidList = lService.create(appraisalFiles, loanDto);
			lService.insertToImageTable(appraisalFiles, eidList, loanDto, toyKey, "03004001");
		}
		if (approvalFiles != null && !approvalFiles.isEmpty()) {
			eidList = lService.create(approvalFiles, loanDto);
			lService.insertToImageTable(approvalFiles, eidList, loanDto, toyKey, "03005001");
		}
		if (changeOfConditionsFiles != null && !changeOfConditionsFiles.isEmpty()) {
			eidList = lService.create(changeOfConditionsFiles, loanDto);
			lService.insertToImageTable(changeOfConditionsFiles, eidList, loanDto, toyKey, "03005002");
		}
		if (agreementFiles != null && !agreementFiles.isEmpty()) {
			eidList = lService.create(agreementFiles, loanDto);
			lService.insertToImageTable(agreementFiles, eidList, loanDto, toyKey, "03006001");
		}
		if (creditFiles != null && !creditFiles.isEmpty()) {
			eidList = lService.create(creditFiles, loanDto);
			lService.insertToImageTable(creditFiles, eidList, loanDto, toyKey, "03007001");
		}
		if (limitFiles != null && !limitFiles.isEmpty()) {
			eidList = lService.create(limitFiles, loanDto);
			lService.insertToImageTable(limitFiles, eidList, loanDto, toyKey, "03008001");
		}
		if (repaymentFiles != null && !repaymentFiles.isEmpty()) {
			eidList = lService.create(repaymentFiles, loanDto);
			lService.insertToImageTable(repaymentFiles, eidList, loanDto, toyKey, "03008002");
		}
		if (aptFiles != null && !aptFiles.isEmpty()) {
			eidList = lService.create(aptFiles, loanDto);
			lService.insertToImageTable(aptFiles, eidList, loanDto, toyKey, "03008003");
		}
		// DEPOSIT_TB에 insert
		lService.insertToLoanTable(loanDto, toyKey, custNo);

		log.info("post loan done!");

		// return "/toy/loan";
		return "toy/loan";
	}
}
