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

public class UserDaoImpl implements UserDao{
	public void add(User user){
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into _user (uname,pwd,email,phone,age,gender,address,degree,joinTime,ip) values (?,?,?,?,?,?,?,?,?,?)";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setObject(1, user.getUname());
			pstmt.setObject(2, user.getPwd());
			pstmt.setObject(3, user.getEmail());
			pstmt.setObject(4, user.getPhone());
			pstmt.setObject(5, user.getAge());
			pstmt.setObject(6, user.getGender());
			pstmt.setObject(7, user.getAddress());
			pstmt.setObject(8, user.getDegree());
			pstmt.setObject(9, user.getJoinTime());
			pstmt.setObject(10, user.getIp());
			
			pstmt.executeUpdate();
System.out.println("一个用户注册成功！");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(pstmt, conn);
		}
	}

	@Override
	public int countUnameNum(String uname) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from _user where uname=?";
		int unameNum = 0;
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setObject(1, uname);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				unameNum = rs.getInt(1);
			}
			return unameNum;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally{
			DBUtil.close(pstmt, conn);
		}
		
	}

	@Override
	public User getUserByUnamePwd(String uname, String pwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select id,uname,pwd,email,phone,age,gender,address,degree,joinTime from _user where uname=? and pwd=?";
		User user = null;
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setObject(1, uname);
			pstmt.setObject(2, pwd);
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				user = new User(rs.getInt("id"), rs.getString("uname"),rs.getString("pwd") , 
						rs.getString("email"), rs.getString("phone"), rs.getInt("age"), 
						rs.getInt("gender"), rs.getString("address"), rs.getInt("degree"),rs.getDate("joinTime"));
			}
  			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			return user;
		} finally{
			DBUtil.close(pstmt, conn);
		}
	}

	public Pagenation getAllUsers(int startRow,int size) {
		/*Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select id,uname,pwd,email,phone,age,gender,address,degree,joinTime from _user limit ?,?";
		List<UserInfo> list = new ArrayList<UserInfo>();
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2,size);
			rs = pstmt.executeQuery();
			while(rs.next()){
				UserInfo user = new UserInfo(rs.getInt("id"), rs.getString("uname"),rs.getString("pwd") , 
						rs.getString("email"), rs.getString("phone"), rs.getInt("age"), 
						rs.getInt("gender"), rs.getString("address"), rs.getInt("degree"),rs.getDate("joinTime"));
				list.add(user);
			}
  			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		} finally{
			DBUtil.close(pstmt, conn);
		}*/
		return null;
	}
	
	@Override
	public int getUsersCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from _user";
		int count = 0;
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				count = rs.getInt(1);
			}
  			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			return count;
		} finally{
			DBUtil.close(pstmt, conn);
		}
	}

	@Override
	public Pagenation getAllUsers(String pageNum, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
/*
    //插入六百条数据
	public static void main(String[] args) {
		for(int i=0;i<600;i++){
			Date date = new Date(new java.util.Date().getTime());
			User user = new User("meng"+i,"123","ym@sina.cn","15558828288",18,1,"nanjin",2,date,"127.0.0.1");
			new UserDaoImpl().add(user);
		}
	}*/

	
	
}
