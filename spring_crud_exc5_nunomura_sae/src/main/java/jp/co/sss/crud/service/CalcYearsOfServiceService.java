package jp.co.sss.crud.service;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Service;

@Service
public class CalcYearsOfServiceService {

	public Period execute(LocalDate hireDate) {
		Period period = Period.between(hireDate, LocalDate.now());
	    return period;
	}
}
