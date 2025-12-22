package jp.co.sss.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.repository.EmployeeRepository;
import jp.co.sss.crud.util.BeanManager;

@Service
public class SortByHireDateService {

	@Autowired
	EmployeeRepository repository;
	
	public List<EmployeeBean> execute() {
		List<Employee> empList = repository.findAllByOrderByHireDate();
		List<EmployeeBean> empBeanList = BeanManager.copyEntityListToBeanList(empList);
		for (int i =0; i < empList.size(); i++) {
			empBeanList.get(i).setHireDate(empList.get(i).getHireDate());
		}
		
		return empBeanList;
	}
}
