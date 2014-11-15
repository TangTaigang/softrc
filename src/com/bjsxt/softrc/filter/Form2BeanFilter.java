package com.bjsxt.softrc.filter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.bjsxt.softrc.util.BeanUtil;

public class Form2BeanFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String beanFullName = request.getParameter("beanFullName");
		Object obj = null;
		if(beanFullName!=null){
			//com.bjsxt.softrc.po.User
			try {
				Class c = Class.forName(beanFullName);
				obj = c.newInstance();
				Field[] fields = c.getDeclaredFields();
				for (Field field : fields) {
					String fieldName = field.getName();
					Class fieldType = field.getType();
					String param = request.getParameter(fieldName);
					if(param!=null){
						//把param的值赋给field set方法
						Method m = c.getMethod(BeanUtil.getSetterName(fieldName), fieldType);
						String typeName = fieldType.getName();
						if("int".equals(typeName) || "java.lang.Integer".equals(typeName)){
							m.invoke(obj,Integer.parseInt(param));
						} else if("long".equals(typeName) || "java.lang.Long".equals(typeName)){
							m.invoke(obj,Long.parseLong(param));
						} else if("byte".equals(typeName) || "java.lang.Byte".equals(typeName)){
							m.invoke(obj,Byte.parseByte(param));
						} else if("short".equals(typeName) || "java.lang.Short".equals(typeName)){
							m.invoke(obj,Short.parseShort(param));
						} else if("float".equals(typeName) || "java.lang.Float".equals(typeName)){
							m.invoke(obj,Float.parseFloat(param));
						} else if("double".equals(typeName) || "java.lang.Double".equals(typeName)){
							m.invoke(obj,Double.parseDouble(param));
						} else{
							m.invoke(obj,param.trim());
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("formBean", obj);
		} 
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
}
