package com.bjsxt.softrc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/*
 * 令牌机制：防止表单重复提交
 */
public class TokenFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String token = request.getParameter("token");
		if(token!=null){
			HttpServletRequest req = (HttpServletRequest) request;
			long sessionToken = (Long)req.getSession().getAttribute("token");
			long paramToken = Long.parseLong(token);
			if(paramToken!=sessionToken){
				request.setAttribute("error", "表单不能重复提交！");
				request.getRequestDispatcher("error.jsp").forward(request, response);
				return;
			} 
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig request) throws ServletException {
		
	}

}
