package jp.co.sss.crud.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginCheckFilter extends HttpFilter {
	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String requestURL = request.getRequestURI();
		if (requestURL.indexOf("/html/") != -1 || requestURL.indexOf("/css/") != -1 || requestURL.indexOf("/img/") != -1
				|| requestURL.indexOf("/js/") != -1 || requestURL.indexOf("/login") != -1
				|| requestURL.endsWith("/spring_crud/")) {
			chain.doFilter(request, response);
		} else {
			HttpSession session = request.getSession();
			Object loginUser = session.getAttribute("loginUser");
			if (loginUser == null) {
				response.sendRedirect("/spring_crud");
				return;
			} else {
				chain.doFilter(request, response);
			}
		}
	}
}
