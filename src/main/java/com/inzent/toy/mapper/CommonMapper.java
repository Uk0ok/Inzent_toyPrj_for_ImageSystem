package com.inzent.toy.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMapper {
	List<String> getECodeList();
	List<String> getRrnNoList();
	String getCustNo(String rrnNo);
	void insertToCommonTable(Map<String, Object> param);
	void insertToImageTable(Map<String, Object> param);
}
