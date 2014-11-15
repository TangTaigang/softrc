package com.bjsxt.softrc.dao;

import java.util.List;

import com.bjsxt.softrc.po.User;
import com.bjsxt.softrc.util.Pagenation;
import com.bjsxt.softrc.vo.UserInfo;

public interface UserDao {
	public void add(User user) throws Exception;
	public int countUnameNum(String uname);
	public User getUserByUnamePwd(String uname,String pwd);
	public Pagenation getAllUsers(String pageNum,int size);
	public int getUsersCount();
}
