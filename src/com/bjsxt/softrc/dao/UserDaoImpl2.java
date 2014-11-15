package com.bjsxt.softrc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bjsxt.softrc.po.User;
import com.bjsxt.softrc.util.Pagenation;
import com.bjsxt.softrc.vo.UserInfo;

public class UserDaoImpl2 implements UserDao{
	public void add(User user) throws Exception{
		DBUtil.add(user);
	}

	@Override
	public int countUnameNum(String uname) {
		String sql = "select count(*) from _user where uname=?";
		long num = (Long) DBUtil.getValue(sql, new Object[]{uname});
		return (int)num;
	}

	@Override
	public User getUserByUnamePwd(String uname, String pwd) {
		String sql = "select id,uname,pwd,email,phone,age,gender,address,degree,joinTime from _user where uname=? and pwd=?";
		User user = (User)DBUtil.getRowFields(sql, new Object[]{uname,pwd}, User.class);
		return user;
	}

	@Override
	public Pagenation getAllUsers(String pageNum,int size) {
		String sql = "select id,uname,pwd,email,phone,age,gender,address,degree,joinTime from _user";
		Pagenation p = DBUtil.getRowsData4Page(pageNum,size, sql, User.class, null);
		return p;
	}
	
	@Override
	public int getUsersCount() {
		String sql = "select count(*) from _user";
		long num = (Long)DBUtil.getValue(sql, null);
		return (int)num;
	}

}
