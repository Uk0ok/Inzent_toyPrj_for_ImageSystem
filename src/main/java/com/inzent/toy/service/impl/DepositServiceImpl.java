package com.inzent.toy.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.inzent.toy.entity.DepositDto;
import com.inzent.toy.mapper.DepositMapper;
import com.inzent.toy.service.DepositService;
import com.windfire.apis.asysConnectData;
import com.windfire.apis.asys.asysUsrElement;

@Service
public class DepositServiceImpl implements DepositService {
	private asysConnectData conn = null;

	@Value("${xtorm.engineIp}")
    private String hostName;
    
    @Value("${xtorm.enginePort}")
    private int port;
    
    @Value("${xtorm.description}")
    private String description;
    
    @Value("${xtorm.engineId}")
    private String id;
    
    @Value("${xtorm.enginePw}")
    private String password;

	@Value("${xtorm.gateway}")
	private String gateway;

	@Value("${xtorm.tempPath}")
	private String tempPath;

	@Autowired
	private DepositMapper dMapper;

	@Override
	public List<String> getECodeList() {
		return dMapper.getECodeList();
	}

	@Override
	public String createToyKey() {
		int leftLimit = 48; // letter 'a'
    	int rightLimit = 57; // letter 'z'
		int randomToyKeyLength = 4;

		Date nowDate = new Date();
		
		String generatedFrontString = new SimpleDateFormat("yyyyMMddHHmmss").format(nowDate);

		Random random = new Random();
		StringBuilder buffer = new StringBuilder(randomToyKeyLength);
		for (int i = 0; i < randomToyKeyLength; i++) {
			int randomLimitedInt = leftLimit + (int) 
			(random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
    	String generatedBackString = buffer.toString();

		String generatedString = generatedFrontString + "DP" + generatedBackString;

		return generatedString;
	}

	@Override
	public String createCustNo() {
		Random random = new Random();
		int createCustNo = 0;
		String ranCustNo = "";
		int length = 10;
		String resultCustNo = "";

		for (int i=0; i<length; i++) {
			createCustNo = random.nextInt(9);
			ranCustNo = Integer.toString(createCustNo);
			resultCustNo += ranCustNo;
		}
		return resultCustNo;
	}

	public String createTenLengthNo() {
		Random random = new Random();
		int createTenLengthtNo = 0;
		String ranTenLengthNo = "";
		int length = 10;
		String resultTenLengthNo = "";

		for (int i=0; i<length; i++) {
			createTenLengthtNo = random.nextInt(9);
			ranTenLengthNo = Integer.toString(createTenLengthtNo);
			resultTenLengthNo += ranTenLengthNo;
		}

		return resultTenLengthNo;
	}

	@Override
	public List<String> create(List<MultipartFile> files, DepositDto depositDto) {
		asysConnectData conn = new asysConnectData(hostName, port, description, id, password);

		// Map<String, Object> param = new HashMap<>();
		List<String> result = new ArrayList<>();
		asysUsrElement uePage = null;

		System.out.println(files.size());
		
		for (MultipartFile file : files) {
			File requestFile = new File(tempPath + UUID.randomUUID().toString());
			try {
				file.transferTo(requestFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			uePage = new asysUsrElement(conn);
			uePage.m_localFile = requestFile.getAbsolutePath();
			uePage.m_descr = depositDto.getDescription();
			uePage.m_cClassId = depositDto.getCclassId();
			uePage.m_userSClass = depositDto.getUserClass();
			uePage.m_eClassId = depositDto.getEclassId();
	
			try {
				int ret = uePage.create(gateway);
				if (ret != 0) {
					System.out.println("Error, create failed, " + uePage.getLastError());
					result.add("error");
				}
				else {
					System.out.println("Success, create normal, " + uePage.m_elementId);
					result.add(uePage.getShortID());
				}
			} finally {
				requestFile.deleteOnExit();
				requestFile.delete();
				disconn();
			}
		}
		return result;
	}

	private void disconn() {
        if (conn != null) {
			conn.close();
			conn = null;
		}
    }

	@Override
	public void insertToDepositTable(DepositDto depositDto, String toyKey, String custNo) {
		Map<String, Object> param = new HashMap<>();

		param.put("toyKey", toyKey);
		param.put("custNo", custNo);
		param.put("custName", depositDto.getCustName());
		param.put("rrnNo", depositDto.getRrnNo());
		param.put("empCode", depositDto.getEmpCode());

		dMapper.insertToDepositTable(param);
	}

	@Override
	public void insertToImageTable(List<MultipartFile> fileList, List<String> eidList, DepositDto depositDto, String toyKey, String docCode) {
		Map<String, Object> param = new HashMap<>();
		String seqNo = "";
		String lCatCode = "";
		String fileName = "";
		String extension = "";
		int idx = 0;

		System.out.println(eidList);
		for (int i=0; i<eidList.size(); i++) {
			seqNo = Integer.toString(i+1);
			// System.out.println(eidList.get(i));
			param.put("elementId", eidList.get(i));

			param.put("seqNo", seqNo);
			param.put("toyKey", toyKey);
			param.put("docCode", docCode);

			lCatCode = docCode.substring(0,2);
			param.put("lcatCode", lCatCode);
			// System.out.println(lCatCode);

			idx = fileList.get(i).getOriginalFilename().indexOf(".");
			fileName = fileList.get(i).getOriginalFilename().substring(0, idx);
			// System.out.println(fileName);
			param.put("fileName", fileName);

			extension = fileList.get(i).getOriginalFilename().substring(idx+1, fileList.get(i).getOriginalFilename().length());
			// System.out.println(extension);
			param.put("extension", extension);

			if(docCode == "02002001" || docCode == "02005001" || docCode == "02008001" || docCode == "02009001" || docCode == "02010001") {
				param.put("warrantyPer", 5);
			} else if (docCode == "02003001" || docCode == "02007001") {
				param.put("warrantyPer", 7);
			} else if (docCode == "02004001" || docCode == "02006001") {
				param.put("warrantyPer", 10);
			} else {
				param.put("warrantyPer", 30);
			}

			param.put("empCode", depositDto.getEmpCode());

			dMapper.insertToImageTable(param);
		}
	}
}
