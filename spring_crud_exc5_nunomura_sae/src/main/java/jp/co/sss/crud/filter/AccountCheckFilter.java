package jp.co.sss.crud.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jp.co.sss.crud.bean.EmployeeBean;

public class AccountCheckFilter extends HttpFilter {
	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String requestURL = request.getRequestURI();
		if (requestURL.indexOf("/html/") != -1 || requestURL.indexOf("/css/") != -1 || requestURL.indexOf("/img/") != -1
				|| requestURL.indexOf("/js/") != -1 || requestURL.indexOf("/login") != -1
				|| requestURL.indexOf("/logout") != -1
				|| requestURL.endsWith("/spring_crud/") || requestURL.contains("list")) {
			System.out.println("権限チェックの必要のないURLです");
			chain.doFilter(request, response);
		} else {
			HttpSession session = request.getSession(false);
			EmployeeBean loginUserBean = (EmployeeBean) session.getAttribute("loginUser");
			if (loginUserBean.getAuthority() != 2) {
				String empIdParam = request.getParameter("empId");
				Integer empId = (Integer) session.getAttribute("empId");
				System.out.println("一般権限者です");
				if (empIdParam != null && loginUserBean.getEmpId() == Integer.parseInt(empIdParam)) {
					chain.doFilter(request, response);
					System.out.println("リクエストスコープのidとログインユーザーのidが一致しています。"
							+ "empIdParam:" + empIdParam);
				} else if (empId != null && loginUserBean.getEmpId() == empId) {
					chain.doFilter(request, response);
					System.out.println("セッションスコープのidとログインユーザーのidが一致しています。"
							+ "empId:" + empId);
				} else if (empId == null || empIdParam == null) {
					response.sendRedirect("/spring_crud");
					System.out.println("empIdまたはempIdParamがnullです。");
					System.out.println("empId:" + empId);
					System.out.println("empIdParam:" + empIdParam);
					return;
				}
			} else {
				System.out.println("管理者権限です");
				chain.doFilter(request, response);
			}
		}
	}
}
