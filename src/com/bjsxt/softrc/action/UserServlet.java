package com.bjsxt.softrc.action;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bjsxt.softrc.po.User;
import com.bjsxt.softrc.service.UserService;
import com.bjsxt.softrc.service.UserServiceImpl;
import com.bjsxt.softrc.util.Pagenation;

public class UserServlet extends HttpServlet {

	private UserService userService = new UserServiceImpl();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//struts里的dispatchAction(请求分发也是这么做的)
		String method = request.getParameter("method");
		if("reg".equals(method)){
			try {
				reg(request,response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if("nameOnly".equals(method)){
			nameOnly(request,response);
		} else if("login".equals(method)){
			login(request,response);
		} else if("exit".equals(method)){
			exit(request, response);
		} else if("listAllUsers".equals(method)){
			listAllUsers(request,response);
		}
	}
	
	//列出所有用户
	private void listAllUsers(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		String page = request.getParameter("pageNum");
		Pagenation p = userService.listAllUsers(page);
		request.setAttribute("pagenation",p ); //选择一个作用域,宁小勿大request,session,application
		request.getRequestDispatcher("user_list_all.jsp").forward(request, response);
			
	}

	//用户注册
	private void reg(HttpServletRequest request, HttpServletResponse response)throws Exception{
		//判断验证码是否相等	
		String inrand = request.getParameter("inrand");
			String rand = (String)request.getSession().getAttribute("rand");
			if(rand.equals(inrand)){
				
				/*long sessionToken = (Long)request.getSession().getAttribute("token");
		long paramToken = Long.parseLong(request.getParameter("token"));
		
		if(paramToken==sessionToken){*/
				//session里面的值只能使用一次
				request.getSession().setAttribute("token", new Date().getTime());
				//formBean与过滤器Form2BeanFilter中setAttribute的名称要相同(request.setAttribute("formBean", obj);)
				User user = (User) request.getAttribute("formBean"); 
				String ip = request.getRemoteAddr();
				java.sql.Date joinTime = new java.sql.Date(new java.util.Date().getTime());
				user.setIp(ip);
				user.setJoinTime(joinTime);
				
				userService.register(user);
				
				request.getRequestDispatcher("reg_ok.jsp").forward(request, response);
				/*		} else{
			request.setAttribute("error", "表单不能重复提交！");
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}*/
			} else{
				request.setAttribute("inrandError", "验证码输入错误");
				request.getRequestDispatcher("reg.jsp").forward(request, response);
			}
		
		
	}
	
	//检查用户名是否唯一
	private void nameOnly(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		boolean only = userService.nameOnly(request.getParameter("uname").trim());
		response.getWriter().print(""+only);
	}
	
	//用户登陆
	private void login(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		User user = userService.login(request.getParameter("uname").trim(), request.getParameter("pwd").trim());
		if(user==null){
			request.setAttribute("loginError", "用户名或密码错误，请重新输入！");	
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		} else{
			request.getSession().setAttribute("user", user);
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
	}
	
	//退出登陆
	private void exit(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
			request.getSession().invalidate();
			response.sendRedirect("index.jsp");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
		
	}

}
