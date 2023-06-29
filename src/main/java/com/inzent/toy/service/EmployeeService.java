package com.inzent.toy.service;

import java.util.List;
import java.util.Map;

import com.inzent.toy.entity.PageVo;

public interface EmployeeService {
	public int countEmployees();
	public List<Map<String, Object>> selectEmployees(PageVo pVo);
	public void createEmployee(Map<String, String> map);
	public void deleteEmployee(Map<String, String> map);
}
