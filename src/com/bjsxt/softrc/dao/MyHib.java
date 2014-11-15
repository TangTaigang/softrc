package com.bjsxt.softrc.dao;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.bjsxt.softrc.util.Pagenation;

/*
 * 使用反射机制封装JDBC（模拟Hibernate）
 */
public class MyHib {
	static Properties properties = null;
	static{		//静态代码块，在类加载时执行。通常可以对static属性做初始化工作
		InputStream is = MyHib.class.getClassLoader().getResourceAsStream("db.properties");
		properties = new Properties();
		try {
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(is != null){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName(properties.getProperty("driverClassName")); 
			String url = properties.getProperty("url");
			String uname = properties.getProperty("username");
			String pwd = properties.getProperty("password");
			conn = DriverManager.getConnection(url,uname,pwd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e){
			e.printStackTrace();
		}
		return conn;
	} 
	
	public static void close(Statement stmt,Connection conn){
		try {
			if(stmt != null){
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs,Statement stmt,Connection conn){
		try {
			if(rs != null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(stmt != null){
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//@SuppressWarnings("unchecked")
	public static List getRowsData(String sql,Class c,Object...param){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = new ArrayList();
		try {
			//创建Connection对象
			conn = getConnection();
			//创建preparedStatement对象
			pstmt = conn.prepareStatement(sql);
			if(param!=null){
				for (int i = 0; i < param.length; i++) {
					pstmt.setObject(i+1, param[i]);
				}
			}
			//查询
			rs = pstmt.executeQuery();
			//处理结果
			ResultSetMetaData rsd = rs.getMetaData();  //包含了列的信息
			while(rs.next()){
				Object obj = c.newInstance();
				for (int i = 0; i < rsd.getColumnCount(); i++) {
					String fieldname = rsd.getColumnName(i+1);
					 Field f = c.getDeclaredField(fieldname);
						Method m = c.getMethod("set"+firstAlphaUpper(f.getName()),f.getType());
						m.invoke(obj, rs.getObject(fieldname));
				}
				list.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			close(rs,pstmt, conn);
		}
		return list;
	}
	
	//获取set方法
	public static String firstAlphaUpper(String fieldName){  //id-->Id-->setId
		String upper = fieldName.toUpperCase();
		String result = upper.substring(0,1)+fieldName.substring(1);
		return result;
	} 
	
	/*
	 * 返回一条记录，多个字段
	 */
	public static Object getRowdata(String sql,Class c,Object[] params){
		List list = getRowsData(sql, c, params);
		if(list != null && list.size()>0){
			return list.get(0);
		} else{
			return null;
		}
	}
	
	/*
	 * 得到一个值（一条记录，一个字段）
	 */
	
	public static Object getValueData(String sql,Object...params){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Object obj = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			
			if(params!=null){
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i+1, params[i]);
				}
			}
			rs = pstmt.executeQuery();
			while(rs.next()){
				obj = rs.getObject(1);  //会自动转成long类型
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			close(pstmt, conn);
		}
		return obj;
	}
	
	public static int updateData(String sql,Object...param){
		Connection conn = null;
		PreparedStatement pstmt = null;
		int numResult = 0;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			if(param!=null){
				for (int i = 0; i < param.length; i++) {
					pstmt.setObject(i+1, param[i]);
				}
			}
			//执行
			numResult = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			close(pstmt, conn);
		}
		return numResult;
	}
	
	//Hibernate是一个ORM（Object relation mapping对象关系映射）
	public static void add(Object obj) throws Exception{
//		String sql = insert into _user (id,uname,pwd) values(?,?,?); id需要插入，由数据库来维护
		StringBuilder sb = new StringBuilder("insert into _"); //表名必须以下划线开头
		StringBuilder sb2 = new StringBuilder();
		String name = obj.getClass().getName().toLowerCase(); //obj.getClass().getSimpleName()
		sb.append(name.substring(name.lastIndexOf(".")+1));
		sb.append(" (");
		
		Field[] fs = obj.getClass().getDeclaredFields();
		List params = new ArrayList();
		for (int i = 0; i < fs.length; i++) {
			if(!"id".equalsIgnoreCase(fs[i].getName())){
				sb.append(fs[i].getName());
				System.out.println("---------"+fs[i].getName());
				Method m = obj.getClass().getDeclaredMethod("get"+firstAlphaUpper(fs[i].getName()));
				params.add(m.invoke(obj));
				sb2.append("?");
				if(i<fs.length-1){
					sb.append(",");
					sb2.append(",");
				}
				
			}
		}
		sb.append(") values (" +sb2+")");
		
		System.out.println("tangHibernate:"+sb);
		
		updateData(sb.toString(),params.toArray());
		
	}
	
	public static Object getObjectById(int id,Class c){
		String sql = "select * from _" + c.getSimpleName().toLowerCase()+" where id=? ";
		return getRowdata(sql, c, new Object[id]);  //参数?
	} 
	
	public static void update(Object obj) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		//更新时，首先要确保有这个记录，通过id
		//约定优于配置
		//update _user set name=?,pwd=?,email=?,gender=?,ip=?,id=?
		StringBuilder sb = new StringBuilder("update _"+obj.getClass().getSimpleName().toLowerCase()+" ");
		sb.append("set ");
		Field[] fs = obj.getClass().getDeclaredFields();
		List params = new ArrayList();
		int j=0;
		for (int i = 0; i < fs.length; i++) {
			if(!"id".equalsIgnoreCase(fs[i].getName())){
				sb.append(fs[i].getName()+"=?");
				Method m = obj.getClass().getDeclaredMethod("get"+firstAlphaUpper(fs[i].getName()));
				params.add(m.invoke(obj));
				if(i<fs.length-1){
					sb.append(",");
				}
				j++;
			}
		}
		sb.append(" where id=?");
		Method m = obj.getClass().getDeclaredMethod("get"+firstAlphaUpper("id"));
		params.add(m.invoke(obj));
		System.out.println(sb);
		updateData(sb.toString(), params.toArray());
	}
	
	public static void delete(Object obj) throws Exception{
		//把这个对象对应的记录删除，通过Id确定是否对应！
		//delete from _user where id = obj.id;
		Method m = obj.getClass().getDeclaredMethod("get"+firstAlphaUpper("id"));
		String sql = "delete from _"+obj.getClass().getSimpleName().toLowerCase()+" where id=? ";
		updateData(sql,m.invoke(obj));
		
	}
	
	/**
	 * 根据传入的SQL及参数返回一个分页对象
	 * @param pageNum 当前页号
	 * @param sql SQL语句
	 * @param c 记录封装到哪个javabean
	 * @param param SQL参数
	 * @return Page 分页对象
	 */
	public static Pagenation getRowsData4Page(String pageNum,String sql,Class c,Object...param){
		String sql1 = sql+" limit ?,? ";
		String[] str = sql.split("(?i)(from)(?-i)");//利用正则表达式的模式修改符处理大小写问题!
		String sql2 = "select count(*) "+" from "+str[1];
		Long lg = (Long)MyHib.getValueData(sql2, param);
		Pagenation p = new Pagenation(pageNum,lg.intValue(),10);
		System.out.println("sql1"+sql1);
		p.setList(MyHib.getRowsData(sql1, c, param,p.getStartRow(),p.getSize()));
		return p;
	}
	
	public static List getAllData(Class c){
		return getRowsData("select * from _"+c.getSimpleName().toLowerCase(),c,null);
	}
	
	
	
	
	
}











