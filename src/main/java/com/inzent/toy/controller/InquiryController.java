package com.inzent.toy.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inzent.toy.entity.PageVo;
import com.inzent.toy.service.InquiryService;

/**
 * @packageName   : com.inzent.toy.controller
 * @fileName      : InquiryController.java
 * @author        : uk0ok
 * @version       : 1.0
 * @date          : 2023.06.12
 * @description   : Inquiry 관리 Controller
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023.06.12        uk0ok            최초 생성
 */
@Controller
@RequestMapping("toy")
public class InquiryController {

	@Autowired
	private InquiryService iService;

	@Value("${xtorm.downPath}")
	private String downPath;

	@RequestMapping(value = "inquiry/inquiryCommon", method = RequestMethod.GET)
	public String inquiryCommon(Model model, 
								@RequestParam(value="page", defaultValue = "1") int page,
								@RequestParam(value="toyKey", required = false) String toyKey,
								@RequestParam(value="custNm", required = false) String custNm,
								@RequestParam(value="rrnNo", required = false) String rrnNo) {

		PageVo pVo = null;
		Map<String, Object> commonJson = new HashMap<>();

		if (toyKey == null || custNm == null || rrnNo == null) {
			if (toyKey == null) {
				toyKey = "";
				if (custNm == null) {
					custNm = "";
				}
				if (rrnNo == null) {
					rrnNo = "";
				}
			} else if (custNm == null) {
				custNm = "";
				if (rrnNo == null) {
					rrnNo = "";
				}
			} else if (rrnNo == null) {
				rrnNo = "";
			}
		}

		commonJson.put("toyKey", toyKey);
		commonJson.put("custNm", custNm);
		commonJson.put("rrnNo", rrnNo);

		pVo = new PageVo(iService.countCommonWorksAfterSearch(commonJson), page);

		model.addAttribute("commonJson", commonJson);
		model.addAttribute("page", page);
		model.addAttribute("pVo", pVo);
		model.addAttribute("viewAll", iService.selectCommonWorksByElemnts(commonJson, pVo));

		// return "/toy/inquiry/inquiryCommon";
		return "toy/inquiry/inquiryCommon";
	}
	
	@RequestMapping(value = "inquiry/inquiryCommon", method = RequestMethod.POST)
	public String inquiryCommonByElements(Model model, 
										  @RequestParam(value="page", defaultValue = "1") int page,
										  @RequestBody Map<String, Object> commonJson) {
		
		System.out.println("COMMON POST 호출");

		PageVo pVo = new PageVo(iService.countCommonWorksAfterSearch(commonJson), page);

		if (commonJson.get("listToyKey") != null && commonJson.get("listToyKey") != "") {
			String listToyKey = commonJson.get("listToyKey").toString();	
			System.out.println("listToyKey : " + listToyKey);
			model.addAttribute("viewImage", iService.selectImageTable(listToyKey));
		} 

		System.out.println("commonJson : " + commonJson);
		
		model.addAttribute("commonJson", commonJson);
		model.addAttribute("page", page);
		model.addAttribute("pVo", pVo);
		model.addAttribute("viewAll", iService.selectCommonWorksByElemnts(commonJson, pVo));

		if (commonJson.get("listToyKey") != null && commonJson.get("listToyKey") != "") {
			// return "/toy/inquiry/imageModal :: #imageListTable";
			return "toy/inquiry/imageModal :: #imageListTable";
		} else {
			// return "/toy/inquiry/inquiryCommon :: #test";
			return "toy/inquiry/inquiryCommon :: #test";
		}
	}

	@RequestMapping(value = "inquiry/inquiryDeposit", method = RequestMethod.GET)
	public String inquiryDeposit (Model model, 
						  	      @RequestParam(value="page", defaultValue = "1") int page,
							      @RequestParam(value="toyKey", required = false) String toyKey,
							      @RequestParam(value="custNm", required = false) String custNm,
							      @RequestParam(value="rrnNo", required = false) String rrnNo) {

		System.out.println("DEPOSIT POST 호출");

		PageVo pVo = null;
		Map<String, Object> depositJson = new HashMap<>();

		if (toyKey == null || custNm == null || rrnNo == null) {
			if (toyKey == null) {
				toyKey = "";
				if (custNm == null) {
					custNm = "";
				}
				if (rrnNo == null) {
					rrnNo = "";
				}
			} else if (custNm == null) {
				custNm = "";
				if (rrnNo == null) {
					rrnNo = "";
				}
			} else if (rrnNo == null) {
				rrnNo = "";
			}
		}

		depositJson.put("toyKey", toyKey);
		depositJson.put("custNm", custNm);
		depositJson.put("rrnNo", rrnNo);

		pVo = new PageVo(iService.countDepositWorksAfterSearch(depositJson), page);

		model.addAttribute("depositJson", depositJson);
		model.addAttribute("page", page);
		model.addAttribute("pVo", pVo);
		model.addAttribute("viewAll", iService.selectDepositWorksByElemnts(depositJson, pVo));

		// return "/toy/inquiry/inquiryDeposit";
		return "toy/inquiry/inquiryDeposit";
	}
	@RequestMapping(value = "inquiry/inquiryDeposit", method = RequestMethod.POST)
	public String inquiryDeposit (Model model, 
						  	      @RequestParam(value="page", defaultValue = "1") int page,
							      @RequestBody Map<String, Object> depositJson) {
		
		System.out.println("DEPOSIT POST 호출");

	    PageVo pVo = new PageVo(iService.countDepositWorksAfterSearch(depositJson), page);

	    if (depositJson.get("listToyKey") != null && depositJson.get("listToyKey") != "") {
			String listToyKey = depositJson.get("listToyKey").toString();	
			System.out.println("listToyKey : " + listToyKey);
			model.addAttribute("viewImage", iService.selectImageTable(listToyKey));
		} 

		if (depositJson.get("elementIdDel") != null && depositJson.get("elementIdDel") != "") {
			String elementIdDel = depositJson.get("elementIdDel").toString();
			System.out.println("elementIdDel : " + elementIdDel);
			iService.deleteImage(elementIdDel);
		}

		if (depositJson.get("elementIdDown") != null && depositJson.get("elementIdDown") != "") {
			String elementIdDown = depositJson.get("elementIdDown").toString();
			String extension = depositJson.get("extension").toString();
			System.out.println("elementIdDown : " + elementIdDown);
			System.out.println("extension : " + extension);
			// iService.downloadImage(elementIdDown, extension);
		}

		System.out.println("depositJson : " + depositJson);

		model.addAttribute("depositJson", depositJson);
		model.addAttribute("page", page);
		model.addAttribute("pVo", pVo);
		model.addAttribute("viewAll", iService.selectDepositWorksByElemnts(depositJson, pVo));

		if (depositJson.get("listToyKey") != null && depositJson.get("listToyKey") != "") {
			// return "/toy/inquiry/imageModal :: #imageListTable";
			return "toy/inquiry/imageModal :: #imageListTable";
		} else {
			// return "/toy/inquiry/inquiryDeposit :: #test";
			return "toy/inquiry/inquiryDeposit :: #test";
		}
	}

	@RequestMapping(value = "inquiry/inquiryLoan", method = RequestMethod.GET)
	public String inquiryLoan (Model model, 
						  	   @RequestParam(value="page", defaultValue = "1") int page,
							   @RequestParam(value="toyKey", required = false) String toyKey,
							   @RequestParam(value="custNm", required = false) String custNm,
							   @RequestParam(value="rrnNo", required = false) String rrnNo) {

		PageVo pVo = null;
		Map<String, Object> loanJson = new HashMap<>();

		if (toyKey == null || custNm == null || rrnNo == null) {
			if (toyKey == null) {
				toyKey = "";
				if (custNm == null) {
					custNm = "";
				}
				if (rrnNo == null) {
					rrnNo = "";
				}
			} else if (custNm == null) {
				custNm = "";
				if (rrnNo == null) {
					rrnNo = "";
				}
			} else if (rrnNo == null) {
				rrnNo = "";
			}
		}

		loanJson.put("toyKey", toyKey);
		loanJson.put("custNm", custNm);
		loanJson.put("rrnNo", rrnNo);

		pVo = new PageVo(iService.countLoanWorksAfterSearch(loanJson), page);

		model.addAttribute("loanJson", loanJson);
		model.addAttribute("page", page);
		model.addAttribute("pVo", pVo);
		model.addAttribute("viewAll", iService.selectLoanWorksByElemnts(loanJson, pVo));

		// return "/toy/inquiry/inquiryLoan";
		return "toy/inquiry/inquiryLoan";
	}
	@RequestMapping(value = "inquiry/inquiryLoan", method = RequestMethod.POST)
	public String inquiryLoan (Model model, 
						  	   @RequestParam(value="page", defaultValue = "1") int page,
							   @RequestBody Map<String, Object> loanJson) {
		
		System.out.println("LOAN POST 호출");

	    PageVo pVo = new PageVo(iService.countLoanWorksAfterSearch(loanJson), page);

	    if (loanJson.get("listToyKey") != null && loanJson.get("listToyKey") != "") {
			String listToyKey = loanJson.get("listToyKey").toString();	
			System.out.println("listToyKey : " + listToyKey);
			model.addAttribute("viewImage", iService.selectImageTable(listToyKey));
		} 

		if (loanJson.get("elementIdDel") != null && loanJson.get("elementIdDel") != "") {
			String elementIdDel = loanJson.get("elementIdDel").toString();
			System.out.println("elementIdDel : " + elementIdDel);
			iService.deleteImage(elementIdDel);
		}

		if (loanJson.get("elementIdDown") != null && loanJson.get("elementIdDown") != "") {
			String elementIdDown = loanJson.get("elementIdDown").toString();
			String extension = loanJson.get("extension").toString();
			System.out.println("elementIdDown : " + elementIdDown);
			System.out.println("extension : " + extension);
			// iService.downloadImage(elementIdDown, extension);
		}

		System.out.println("loanJson : " + loanJson);

		model.addAttribute("loanJson", loanJson);
		model.addAttribute("page", page);
		model.addAttribute("pVo", pVo);
		model.addAttribute("viewAll", iService.selectLoanWorksByElemnts(loanJson, pVo));

		if (loanJson.get("listToyKey") != null && loanJson.get("listToyKey") != "") {
			// return "/toy/inquiry/imageModal :: #imageListTable";
			return "toy/inquiry/imageModal :: #imageListTable";
		} else {
			// return "/toy/inquiry/inquiryLoan :: #test";
			return "toy/inquiry/inquiryLoan :: #test";
		}
	}
	// 공통
	// 다운로드 및 삭제 공통
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public void inquiryDelete (@RequestBody Map<String, Object> commonJson) {
		if (commonJson.get("elementIdDel") != null && commonJson.get("elementIdDel") != "") {
			String elementIdDel = commonJson.get("elementIdDel").toString();
			System.out.println("elementIdDel : " + elementIdDel);
			iService.deleteImage(elementIdDel);
		}
	}

	@RequestMapping(value = "download", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> inquiryDownload(@RequestParam String elementIdDown) {
		File file = null;

		Map<String, String> fileNameObject = iService.selectFileNmExtension(elementIdDown);
		
		String fileNm = fileNameObject.get("FILE_NM").toString();
		String extension = fileNameObject.get("EXTENSION").toString();
		String downNm = fileNm + "." + extension;

		iService.downloadImage(elementIdDown);
		
		try {
			file = new File(downPath + elementIdDown);
			
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + new String(downNm.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + "\"");
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentLength(file.length());

			Resource resource = new InputStreamResource(new FileInputStream(file));

			return ResponseEntity.ok().headers(headers).body(resource);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} finally {
			if (file.exists()) {
				file.delete();
			}
		}
	}

	//  뷰어 공통 사용
	@RequestMapping(value = "viewJpg", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] viewJpg(@RequestParam String elementIdView) throws IOException {
		System.out.println("JPG 뷰어");

		String res = downPath + elementIdView;
		iService.downloadImage(elementIdView);
		
		InputStream in = new FileInputStream(res);
		return IOUtils.toByteArray(in);
    }
	@RequestMapping(value = "viewPng", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] viewPng(@RequestParam String elementIdView) throws IOException {
		System.out.println("PNG 뷰어");
		
		String res = downPath + elementIdView;
		iService.downloadImage(elementIdView);
		
		InputStream in = new FileInputStream(res);
		return IOUtils.toByteArray(in);
    }
	@RequestMapping(value = "viewPdf", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody byte[] viewPdf(@RequestParam String elementIdView) throws IOException {
		System.out.println("PDF 뷰어");
		
		String res = downPath + elementIdView;
		iService.downloadImage(elementIdView);
		
		InputStream in = new FileInputStream(res);
		return IOUtils.toByteArray(in);
    }
	@RequestMapping(value = "viewText", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public @ResponseBody byte[] viewText(@RequestParam String elementIdView) throws IOException {
		System.out.println("TEXT 뷰어");

		String res = downPath + elementIdView;
		iService.downloadImage(elementIdView);
		
		InputStream in = new FileInputStream(res);
		return IOUtils.toByteArray(in);
    }
}
