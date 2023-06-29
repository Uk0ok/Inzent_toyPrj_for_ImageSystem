package com.inzent.toy.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.inzent.toy.entity.PageVo;

@Mapper
public interface EmployeeMapper {
	public int countEmployees();
	List<Map<String, Object>> selectEmployees(PageVo pVo);
	public void createEmployee(Map<String, String> map);
	public void deleteEmployee(Map<String, String> map);
}
