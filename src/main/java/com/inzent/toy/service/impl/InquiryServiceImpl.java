package com.inzent.toy.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.inzent.toy.entity.PageVo;
import com.inzent.toy.mapper.InquiryMapper;
import com.inzent.toy.service.InquiryService;
import com.windfire.apis.asysConnectData;
import com.windfire.apis.asys.asysUsrElement;

@Service
public class InquiryServiceImpl implements InquiryService {
	// xtorm Connection 선언 및 초기화
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

	@Value("${xtorm.downPath}")
	private String downPath;

	@Value("${xtorm.eclassId}")
	private String eclassId;

	@Autowired
	private InquiryMapper iMapper;

	// Common
	@Override
	public int countCommonWorksAfterSearch(Map<String, Object> commonJson) {
		return iMapper.countCommonWorksAfterSearch(commonJson);
	}
	@Override
	public List<Map<String, Object>> selectCommonWorksByElemnts(Map<String, Object> commonJson, PageVo pVo) {
		return iMapper.selectCommonWorksByElemnts(commonJson, pVo);
	}

	// Deposit
	@Override
	public int countDepositWorksAfterSearch(Map<String, Object> depositJson) {
		return iMapper.countDepositWorksAfterSearch(depositJson);
}
	@Override
	public List<Map<String, Object>> selectDepositWorksByElemnts(Map<String, Object> depositJson, PageVo pVo) {
		return iMapper.selectDepositWorksByElemnts(depositJson, pVo);
	}
	
	// Loan
	@Override
	public int countLoanWorksAfterSearch(Map<String, Object> loanJson) {
		return iMapper.countLoanWorksAfterSearch(loanJson);
	}
	@Override
	public List<Map<String, Object>> selectLoanWorksByElemnts(Map<String, Object> searchJson, PageVo pVo) {
		return iMapper.selectLoanWorksByElemnts(searchJson, pVo);
	}
	
	// 공통
	@Override
	public List<Map<String, Object>> selectImageTable(String listToyKey) {
		return iMapper.selectImageTable(listToyKey);
	}
	@Override
	public void deleteImage(String elementIdDel) {
		iMapper.deleteImage(elementIdDel);
	}
	@Override
	public Map<String, String> selectFileNmExtension(String elementIdDown) {
		return iMapper.selectFileNmExtension(elementIdDown);
	}
	@Override
	public void downloadImage(String elementIdDown) {
		//asysConnectData초기화
		asysConnectData conn = new asysConnectData(hostName, port, description, id, password);

		asysUsrElement uePage = new asysUsrElement(conn);
		uePage.m_elementId = gateway + "::" + elementIdDown + "::" + eclassId;

		int ret = uePage.getContent(downPath + elementIdDown);

		if (ret != 0) {
			System.out.println("Error, download failed " + uePage.getLastError());
			disconn();
		} else {
			System.out.println("Success, download normal " + uePage.m_elementId);
			disconn();
		}
	}
	// Connection 종료
	public void disconn() {
		if (conn != null) {
			conn.close();
			conn = null;
		}
	}
}

/*
String filename = elementIdDown + "." + extension;
		Resource resource = new ClassPathResource("static/download/" + filename);

        if (resource.exists()) {
        // 파일이 존재하는 경우 다운로드 처리
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        try (InputStream inputStream = resource.getInputStream();
             OutputStream outputStream = response.getOutputStream()) {
            IOUtils.copy(inputStream, outputStream);
        }
    } else {
        // 파일이 존재하지 않는 경우 404 오류 반환
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
 */