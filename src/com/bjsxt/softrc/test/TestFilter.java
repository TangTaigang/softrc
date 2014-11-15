package com.bjsxt.softrc.test;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class TestFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		System.out.println("aaaaaa");
		chain.doFilter(request, response);
		System.out.println("bbbbbbbbbb");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("TestFilter被初始化了");
	}

}
