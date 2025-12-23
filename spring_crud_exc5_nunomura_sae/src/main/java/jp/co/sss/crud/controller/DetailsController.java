package jp.co.sss.crud.controller;

import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.service.CalcYearsOfServiceService;
import jp.co.sss.crud.service.SearchForEmployeesByEmpIdService;

@Controller
public class DetailsController {

	@Autowired
	SearchForEmployeesByEmpIdService searchForEmployeesByEmpIdService;
	
	@Autowired
	CalcYearsOfServiceService calcYearsOfServiceService;

	@RequestMapping(path = "/details/check", method = RequestMethod.GET)
	public String checkdetail(Integer empId, Model model) {

		EmployeeBean employee = searchForEmployeesByEmpIdService.execute(empId);
		Period period = calcYearsOfServiceService.execute(employee.getHireDate());
		model.addAttribute("employee", employee);
		model.addAttribute("serviceYears", period.getYears());
		model.addAttribute("serviceMonth", period.getMonths());
		System.out.println("コントローラでのhireDate:" + employee.getHireDate());
		System.out.println("コントローラでのempComment:" + employee.getEmpComment());
		System.out.println("コントローラでのempId:" + employee.getEmpId());

		return "detail/details";
	}
}
