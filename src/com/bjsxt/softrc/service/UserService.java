package com.bjsxt.softrc.service;

import java.util.List;

import com.bjsxt.softrc.po.User;
import com.bjsxt.softrc.util.Pagenation;

public interface UserService {
	public void register(User user) throws Exception;
	public boolean nameOnly(String uname);
	public User login(String uname,String pwd);
	public Pagenation listAllUsers(String page);
	
}
