package com.inzent.toy.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepositMapper {
	List<String> getECodeList();
	List<String> getRrnNoList();
	String getCustNo(String rrnNo);
	void insertToDepositTable(Map<String, Object> param);
	void insertToImageTable(Map<String, Object> param);
}
