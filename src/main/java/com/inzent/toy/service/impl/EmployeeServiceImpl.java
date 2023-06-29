package com.inzent.toy.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inzent.toy.entity.PageVo;
import com.inzent.toy.mapper.EmployeeMapper;
import com.inzent.toy.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeMapper eMapper;

	@Override
	public int countEmployees() {
		return eMapper.countEmployees();
	}

	@Override
	public List<Map<String, Object>> selectEmployees(PageVo pVo) {
		return eMapper.selectEmployees(pVo);
	}

	@Override
	public void createEmployee(Map<String, String> map) {
		eMapper.createEmployee(map);
	}

	@Override
	public void deleteEmployee(Map<String, String> map) {
		eMapper.deleteEmployee(map);
	}
}
