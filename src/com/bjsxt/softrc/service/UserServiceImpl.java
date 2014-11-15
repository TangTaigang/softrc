package com.bjsxt.softrc.service;

import java.util.List;

import com.bjsxt.softrc.dao.UserDao;
import com.bjsxt.softrc.dao.UserDaoImpl;
import com.bjsxt.softrc.dao.UserDaoImpl2;
import com.bjsxt.softrc.po.User;
import com.bjsxt.softrc.util.Pagenation;
import com.bjsxt.softrc.vo.UserInfo;

public class UserServiceImpl implements UserService {
	private UserDao userDao = new UserDaoImpl2();
	
	public void register(User user) throws Exception{
		userDao.add(user);
	}

	@Override
	public boolean nameOnly(String uname) {
		int num = userDao.countUnameNum(uname);
		if(num>0){
			return false;
		} else{
			return true;
		}
	}

	@Override
	public User login(String uname, String pwd) {
		return userDao.getUserByUnamePwd(uname,pwd);
	}

	@Override
	public Pagenation listAllUsers(String page) {
		Pagenation p =	userDao.getAllUsers(page, 10);
		return p;
	}
	
	
}
