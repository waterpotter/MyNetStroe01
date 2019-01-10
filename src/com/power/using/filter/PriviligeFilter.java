package com.power.using.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.power.using.domian.Function;
import com.power.using.domian.Role;
import com.power.using.domian.User;
import com.power.using.service.BusinessServices;
import com.power.using.service.impl.BusinessServiceImpl;

//只拦截后台资源访问
public class PriviligeFilter implements Filter {

	private BusinessServices s = new BusinessServiceImpl();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request;
		HttpServletResponse response;

		try {
			request = (HttpServletRequest) req;
			response = (HttpServletResponse) res;
		} catch (Exception e) {
			throw new ServletException("non-HTTP request or response");
		}
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if(user==null){
			request.getRequestDispatcher("/passport/adminlogin.jsp").forward(request, response);
			return;
			
		}
		
		Set<Function> funs=new HashSet<>();
		
		List<Role> roles = s.findRolesByUser(user);
		
		for (Role role : roles) {
			List<Function> functions = s.findFunctionByRole(role);
			funs.addAll(functions);
		}
		
		String uri = request.getRequestURI();
		String queryString = request.getQueryString();
		if(queryString!=null){
			uri=uri+"?"+queryString;
		}
		
		uri=uri.replace(request.getContextPath(), "");
	
		boolean hasPermission=false;
		for(Function f:funs){
			if(uri.equals(f.getUri())){
				hasPermission=true;
				break;
			}
		}
		
		
		
		if(!hasPermission){
			response.getWriter().write("您没有权限");
			return;
		}
		
		chain.doFilter(request, response);

	}

	@Override
	public void destroy() {
	}

}
