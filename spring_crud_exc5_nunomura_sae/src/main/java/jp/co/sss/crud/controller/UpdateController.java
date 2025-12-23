package jp.co.sss.crud.controller;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.bean.LoginResultBean;
import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.service.SaveEmployeeImgService;
import jp.co.sss.crud.service.SearchForEmployeesByEmpIdService;
import jp.co.sss.crud.service.UpdateEmployeeService;
import jp.co.sss.crud.util.BeanManager;

@Controller
public class UpdateController {

	@Autowired
	SearchForEmployeesByEmpIdService searchForEmployeesByEmpIdService;

	@Autowired
	UpdateEmployeeService updateEmployeeService;
	
	@Autowired
	SaveEmployeeImgService saveEmployeeImgService;

	/**
	 * 社員情報の変更内容入力画面を出力
	 *
	 * @param empId
	 *            社員ID
	 * @param model
	 *            モデル
	 * @return 遷移先のビュー
	 * @throws ParseException 
	 */
	@RequestMapping(path = "/update/input", method = RequestMethod.GET)
	public String inputUpdate(@RequestParam Integer empId, @ModelAttribute EmployeeForm employeeForm, Model model) {

		System.out.println("リクエストempId:" + empId);
		//TODO SearchForEmployeesByEmpIdService完成後にコメントを外す
		EmployeeBean employee = searchForEmployeesByEmpIdService.execute(empId);
		employeeForm = BeanManager.copyBeanToForm(employee);
		model.addAttribute("employeeForm", employee);

		return "update/update_input";
	}

	/**
	 * 社員情報の変更確認画面を出力
	 *
	 * @param employeeForm
	 *            変更対象の社員情報
	 * @param model
	 *            モデル
	 * @return 遷移先のビュー
	 * @throws IOException 
	 */
	@RequestMapping(path = "/update/check", method = RequestMethod.POST)
	public String checkUpdate(@Valid @ModelAttribute EmployeeForm employeeForm, BindingResult result,
			@RequestParam("empImg") MultipartFile empImg, HttpSession session, Model model) throws IOException {

		if (result.hasErrors()) {
			return "update/update_input";
		} else {
			System.out.println("check時のリクエストempId:" + employeeForm.getEmpId());
			if(!empImg.isEmpty()) {
				session.setAttribute("empImgBytes", empImg.getBytes());
			}
			return "update/update_check";
		}

	}

	/**
	 * 変更内容入力画面に戻る
	 *
	 * @param employeeForm 変更対象の社員情報
	 * @return 遷移先のビュー
	 */
	@RequestMapping(path = "/update/back", method = RequestMethod.POST)
	public String backInputUpdate(@ModelAttribute EmployeeForm employeeForm) {
		return "update/update_input";
	}

	/**
	 * 社員情報の変更
	 *
	 * @param employeeForm
	 *            変更対象の社員情報
	 * @return 遷移先のビュー
	 * @throws IOException 
	 */
	@RequestMapping(path = "/update/complete", method = RequestMethod.POST)
	public String completeUpdate(EmployeeForm employeeForm, HttpSession session, HttpServletRequest request) throws IOException {

		//TODO UpdateEmployeeService完成後にコメントを外す
		LoginResultBean loginResultBean = updateEmployeeService.execute(employeeForm);
		Integer empId = employeeForm.getEmpId();
		saveEmployeeImgService.execute(empId, session);
		
		EmployeeBean loginUserBean = (EmployeeBean) request.getSession().getAttribute("loginUser");
		if (loginUserBean.getAuthority() == 2
				&& loginUserBean.getEmpId() != loginResultBean.getLoginUser().getEmpId()) {
			System.out.println("complete時のリクエストempId" + loginUserBean.getEmpId());
			return "redirect:/update/complete";
		} else {
			session.setAttribute("loginUser", loginResultBean.getLoginUser());
			session.setAttribute("empId", loginResultBean.getLoginUser().getEmpId());
			System.out.println("complete時のセッションempId" + loginResultBean.getLoginUser().getEmpId());
			return "redirect:/update/complete";
		}
	}

	/**
	 * 完了画面の表示
	 * 
	 * @return 遷移先ビュー
	 */
	@RequestMapping(path = "/update/complete", method = RequestMethod.GET)
	public String completeUpdate(HttpSession session) {
		Integer empId = (Integer) session.getAttribute("empId");
		System.out.println("リダイレクト後のempId: " + empId);
		return "update/update_complete";
	}

}
