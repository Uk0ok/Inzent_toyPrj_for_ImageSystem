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

import com.inzent.toy.entity.CommonDto;
import com.inzent.toy.mapper.CommonMapper;
import com.inzent.toy.service.CommonService;

import lombok.extern.slf4j.Slf4j;

/**
 * @packageName   : com.inzent.toy.controller
 * @fileName      : CommonController.java
 * @author        : uk0ok
 * @version       : 1.0
 * @date          : 2023.05.16
 * @description   : 공통 업무 controller
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022.05.16        uk0ok            최초 생성
 */
@Slf4j
@Controller
@RequestMapping("/toy")
public class CommonController {
 
	@Autowired
	private CommonService cService;

	@Autowired
	private CommonMapper cMapper;

	@RequestMapping(value = "/common", method = RequestMethod.GET)
	public String common(Model model) throws Exception {
		List<String> eCodeList = cService.getECodeList();
		model.addAttribute("employeeList", eCodeList);

		// return "/toy/common";
		return "toy/common";
	}

	@RequestMapping(value = "/common", method = RequestMethod.POST)
	public String common(
		@RequestPart(value = "slipFiles", required = false) List<MultipartFile> slipFiles, 
		@RequestPart(value = "sealFiles", required = false) List<MultipartFile> sealFiles, 
		@RequestPart(value = "copyFiles", required = false) List<MultipartFile> copyFiles, 
		@RequestPart(value = "familyFiles", required = false) List<MultipartFile> familyFiles, 
		@RequestPart(value = "nonresidentFiles", required = false) List<MultipartFile> nonresidentFiles, 
		@RequestPart(value = "entrepreneurFiles", required = false) List<MultipartFile> entrepreneurFiles, 
		@RequestPart(value = "groupFiles", required = false) List<MultipartFile> groupFiles, 
		@RequestPart(value = "commonDto") CommonDto commonDto) {

		log.info("post common work!");

		List<String> rrnList = new ArrayList<>();
		List<String> eidList = new ArrayList<>();
		String custNo = "";

		// System.out.println(commonDto.getRrnNo());

		String toyKey = cService.createToyKey();
		
		if (cMapper.getRrnNoList() == null || cMapper.getRrnNoList().size() == 0) {
			System.out.println("there's no rrnNo!");
		} else {
			rrnList = cMapper.getRrnNoList();
			// System.out.println(rrnList);,
			// System.out.println(commonDto.getRrnNo());
		}
		if (rrnList != null  && !rrnList.isEmpty() && rrnList.contains(commonDto.getRrnNo())) {
			custNo = cMapper.getCustNo(commonDto.getRrnNo());
		} else {
			custNo = cService.createCustNo();
		}

		// 파일을 등록 후 IMAGE_TB에 정보 등록
		if (slipFiles != null && !slipFiles.isEmpty()) {
			eidList = cService.create(slipFiles, commonDto);
			cService.insertToImageTable(slipFiles, eidList, commonDto, toyKey, "01001001");
		}
		if (sealFiles != null && !sealFiles.isEmpty()) {
			eidList = cService.create(sealFiles, commonDto);
			cService.insertToImageTable(sealFiles, eidList, commonDto, toyKey, "01003001");
		}
		if (copyFiles != null && !copyFiles.isEmpty()) {
			eidList = cService.create(copyFiles, commonDto);
			cService.insertToImageTable(copyFiles, eidList, commonDto, toyKey, "01003002");
		}
		if (familyFiles != null && !familyFiles.isEmpty()) {
			eidList = cService.create(familyFiles, commonDto);
			cService.insertToImageTable(familyFiles, eidList, commonDto, toyKey, "01003003");
		}
		if (nonresidentFiles != null && !nonresidentFiles.isEmpty()) {
			eidList = cService.create(nonresidentFiles, commonDto);
			cService.insertToImageTable(nonresidentFiles, eidList, commonDto, toyKey, "01003004");
		}
		if (entrepreneurFiles != null && !entrepreneurFiles.isEmpty()) {
			eidList = cService.create(entrepreneurFiles, commonDto);
			cService.insertToImageTable(entrepreneurFiles, eidList, commonDto, toyKey, "01003005");
		}
		if (groupFiles != null && !groupFiles.isEmpty()) {
			eidList = cService.create(groupFiles, commonDto);
			cService.insertToImageTable(groupFiles, eidList, commonDto, toyKey, "01003006");
		}
		// COMMON_TB에 insert
		cService.insertToCommonTable(commonDto, toyKey, custNo);

		log.info("post common done!");

		// return "/toy/common";
		return "toy/common";
	}
}
