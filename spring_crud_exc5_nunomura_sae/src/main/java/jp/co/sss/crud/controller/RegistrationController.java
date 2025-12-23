package jp.co.sss.crud.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.service.RegisterEmployeeService;
import jp.co.sss.crud.service.SaveEmployeeImgService;
import jp.co.sss.crud.util.Constant;

@Controller
public class RegistrationController {

	@Autowired
	private RegisterEmployeeService service;

	@Autowired
	SaveEmployeeImgService saveEmployeeImgService;

	/**
	 * 社員情報の登録内容入力画面を出力
	 *
	 * @return 遷移先のビュー
	 */
	@RequestMapping(path = "/regist/input", method = RequestMethod.GET)
	public String inputRegist(@ModelAttribute EmployeeForm employeeForm) {
		employeeForm.setGender(Constant.DEFAULT_GENDER);
		employeeForm.setAuthority(Constant.DEFAULT_AUTHORITY);
		employeeForm.setDeptId(Constant.DEFAULT_DEPT_ID);

		return "regist/regist_input";
	}

	/**
	 * 社員情報の登録確認画面を出力
	 *
	 * @param employeeForm
	 *            登録対象の社員情報
	 * @param model
	 *            モデル
	 * @return 遷移先のビュー
	 * @throws IOException 
	 */
	@RequestMapping(path = "/regist/check", method = RequestMethod.POST)
	public String checkRegist(@Valid @ModelAttribute EmployeeForm employeeForm, BindingResult result,
			@RequestParam("empImg") MultipartFile empImg, HttpSession session, Model model) throws IOException {

		if (result.hasErrors()) {
			return "regist/regist_input";
		} else {
			if (!empImg.isEmpty()) {
		        session.setAttribute("empImgBytes", empImg.getBytes());
		        session.setAttribute("empImgName", empImg.getOriginalFilename());
		    }
			System.out.println("登録チェックコントローラ: empImage isEmpty = " + empImg.isEmpty());
		    System.out.println("登録チェックコントローラ: empImage size = " + empImg.getSize());
			return "regist/regist_check";
		}
	}

	/**
	 * 変更内容入力画面に戻る
	 *
	 * @param employeeForm 変更対象の社員情報
	 * @return 遷移先のビュー
	 */
	@RequestMapping(path = "/regist/back", method = RequestMethod.POST)
	public String backInputRegist(@ModelAttribute EmployeeForm employeeForm) {
		return "regist/regist_input";
	}

	/**
	 * 社員情報の登録完了画面を出力
	 *
	 * @param employeeForm
	 *            登録対象の社員情報
	 * @return リダイレクト：完了画面
	 * @throws IOException 
	 */
	@RequestMapping(path = "/regist/complete", method = RequestMethod.POST)
	public String completeRegist(EmployeeForm employeeForm, HttpSession session) throws IOException {

		//登録実行
		//TODO RegisterEmployeeService完成後にコメントを外す
		
		Employee emp = service.execute(employeeForm);
		Integer empId = emp.getEmpId();
		
		saveEmployeeImgService.execute(empId, session);

		return "redirect:/regist/complete";
	}

	/**
	 * 完了画面表示
	 * 
	 * @return 遷移先のビュー
	 */
	@RequestMapping(path = "/regist/complete", method = RequestMethod.GET)
	public String completeRegist() {

		return "regist/regist_complete";
	}

}
