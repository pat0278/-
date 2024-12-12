package com.event.cia103g1springboot.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {
//	@Autowired
//	HttpSession session;

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		HttpSession session = req.getSession();

		Object auth = req.getSession().getAttribute("auth");

		if (auth == null) {
			session.setAttribute("location", req.getRequestURI());
			res.sendRedirect("/mem/login");
		} else {
			chain.doFilter(request, response);
		}
	}

}
