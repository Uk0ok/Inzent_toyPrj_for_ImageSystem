package com.inzent.toy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.inzent.toy.entity.PageVo;
import com.inzent.toy.service.EmployeeService;

/**
 * @packageName   : com.inzent.toy.controller
 * @fileName      : EmployeeController.java
 * @author        : uk0ok
 * @version       : 1.0
 * @date          : 2023.05.16
 * @description   : Employee 관리 Controller
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022.05.16        uk0ok            최초 생성
 */
@Controller
@RequestMapping("toy")
public class EmployeeController {
	@Autowired
	private EmployeeService eService;

	@RequestMapping(value = "employee", method = RequestMethod.GET)
	public String employee(Model model, 
						  @RequestParam(value="page", defaultValue = "1") int page) {

		PageVo pVo = new PageVo(eService.countEmployees(), page);

		model.addAttribute("page", page);
		model.addAttribute("pVo", pVo);
		model.addAttribute("viewAll", eService.selectEmployees(pVo));

		// return "/toy/employee";
		return "toy/employee";
	}

	@RequestMapping(value = "employee/enroll", method = RequestMethod.POST)
	// public String employeeModal(@RequestBody EmployeeDto eDto) {
	public String employeeEnroll(@RequestBody Map<String, String> map,
								 Model model,
								 @RequestParam(value="page", defaultValue = "1") int page) {

		PageVo pVo = new PageVo(eService.countEmployees(), page);

		model.addAttribute("page", page);
		model.addAttribute("pVo", pVo);
		model.addAttribute("viewAll", eService.selectEmployees(pVo));

		System.out.println(map.size());
		System.out.println(map.get("empCd"));
		System.out.println(map.get("empName"));
		System.out.println(map.get("orgCd"));
		System.out.println(map.get("email"));
		System.out.println(map.get("phoneNo"));
		eService.createEmployee(map);

		// return "/toy/employee";
		return "toy/employee";
	}

	@RequestMapping(value = "employee/delete", method = RequestMethod.POST)
	// public String employeeModal(@RequestBody EmployeeDto eDto) {
	public String employeeDelete(@RequestBody Map<String, String> map,
								 Model model,
								 @RequestParam(value="page", defaultValue = "1") int page) {

		PageVo pVo = new PageVo(eService.countEmployees(), page);

		model.addAttribute("page", page);
		model.addAttribute("pVo", pVo);
		model.addAttribute("viewAll", eService.selectEmployees(pVo));

		eService.deleteEmployee(map);

		// return "/toy/employee";
		return "toy/employee";
	}

}
