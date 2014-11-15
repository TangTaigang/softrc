package com.bjsxt.softrc.dao;

import java.io.IOException;
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

import com.bjsxt.softrc.util.BeanUtil;
import com.bjsxt.softrc.util.Pagenation;

public class DBUtil {
	static Properties prop = null;
	static String driverclass = null;
	static String url = null;
	static String uname = null;
	static String pwd = null;
	static {
		try {
			//从properties配置文件读取数据库连接的配置信息
			prop = new Properties();
			prop.load(DBUtil.class.getClassLoader().getResourceAsStream("db.properties"));
			driverclass = prop.getProperty("driverClassName");
			url = prop.getProperty("url");
			uname = prop.getProperty("username");
			pwd = prop.getProperty("password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName(driverclass);
			conn = DriverManager.getConnection(url,uname,pwd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e){
			e.printStackTrace();
		}
		return conn;
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
	
	@SuppressWarnings("unchecked")
	public static List getRowsFields(String sql,Object[] params,Class c){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		String sql = "select id,uname,pwd,email,phone,age,gender,address,degree,joinTime from _user";
		List list = new ArrayList();
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			if(params!=null){
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i+1, params[i]);
				}
			}
			
			rs = pstmt.executeQuery();
			ResultSetMetaData rsmt = rs.getMetaData();
			rsmt.getColumnCount();//返回查询的列数
			rsmt.getColumnName(1);//返回指定索引列的名字
			
			while(rs.next()){
				Object obj = null;
				try {
					obj = c.newInstance();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
				
				//获取查询的列名：id,uname,pwd,email,phone,age,gender,address,degree,joinTime
				for (int i = 0; i < rsmt.getColumnCount(); i++) {
					//id-->setId(rs.getInt(1));
					//uname-->setUname(rs.getString("uname"));
					String fieldname = rsmt.getColumnName(i+1);
					Field f;
					try {
						f = c.getDeclaredField(fieldname);
						Method m = c.getMethod(BeanUtil.getSetterName(fieldname),f.getType());
						m.invoke(obj, rs.getObject(fieldname));
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
				
				list.add(obj);
			}
  			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		} finally{
			DBUtil.close(pstmt, conn);
		}
	}
	
	public static Object getRowFields(String sql,Object[] params,Class c){
		List list = getRowsFields(sql, params, c);
		if(list != null && list.size()>0){
			return list.get(0);
		} else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Object getValue(String sql,Object[] params){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Object obj = null;
		try {
			conn = DBUtil.getConnection();
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
  			return obj;
		} catch (SQLException e) {
			e.printStackTrace();
			return obj;
		} finally{
			DBUtil.close(pstmt, conn);
		}
	}
	

	
	public static int updateData(String sql,Object[] params){
		Connection conn = null;
		PreparedStatement pstmt = null;
		int numResult = 0;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			if(params!=null){
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i+1, params[i]);
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
	
	public static void add(Object obj){
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
				Method m = null;
				try {
					m = obj.getClass().getDeclaredMethod(BeanUtil.getGetterName(fs[i].getName()));
					params.add(m.invoke(obj));
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
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
	
	//根据id查询用户
	public static Object getObjectById(int id,Class c){
		String sql = "select * from _" + c.getSimpleName().toLowerCase()+" where id=? ";
		return getRowFields(sql, new Object[]{id}, c);
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
				Method m = obj.getClass().getDeclaredMethod(BeanUtil.getGetterName(fs[i].getName()));
				params.add(m.invoke(obj));
				if(i<fs.length-1){
					sb.append(",");
				}
				j++;
			}
		}
		sb.append(" where id=?");
		Method m = obj.getClass().getDeclaredMethod(BeanUtil.getGetterName("id"));
		params.add(m.invoke(obj));
		System.out.println(sb);
		updateData(sb.toString(), params.toArray());
	}
	
	//添加或跟新数据，如果id存在则更新，不存在则添加
	public static void addOrUpdate(Object obj){
		Method m = null;
		int id = 0;
		long num = 0;
		try {
			m = obj.getClass().getDeclaredMethod(BeanUtil.getGetterName("id"));
			id = (Integer)m.invoke(obj);
			num = (Long)getValue("select count(*) from _"+obj.getClass().getSimpleName().toLowerCase()+" where id=?",new Object[]{id});
			if(num==0){
				add(obj);
			} else{
				update(obj);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	//把这个对象对应的记录删除，通过Id确定是否对应！
	public static void delete(Object obj) throws Exception{
		//delete from _user where id = obj.id;
		Method m = obj.getClass().getDeclaredMethod(BeanUtil.getGetterName("id"));
		String sql = "delete from _"+obj.getClass().getSimpleName().toLowerCase()+" where id=? ";
		updateData(sql,new Object[]{m.invoke(obj)});
	}

	//查询一张表中所有数据
	public static List getAllData(Class c){
		return getRowsFields("select * from _"+c.getSimpleName().toLowerCase(),null,c);
	}
	
	/**
	 * 根据传入的SQL及参数返回一个分页对象
	 * @param pageNum 当前页号
	 * @param size 每页最多显示多少数据
	 * @param sql SQL语句
	 * @param c 记录封装到哪个javabean
	 * @param param SQL参数
	 * @return Page 分页对象
	 */
	//select *from _user where id>1 and gender=0  limit 20,10
	//select count(*) from _user where id>1 and gender=0
	public static Pagenation getRowsData4Page(String pageNum,int size,String sql,Class c,Object[] param){
		String sql1 = sql+" limit ?,? ";
		
		//(?i)(from)(?-i)或者[Ff][Rr][Oo][Mm]
		String[] str = sql.split("(?i)(from)(?-i)");//利用正则表达式的模式修改符处理大小写问题!
		String sql2 = "select count(*) "+" from "+str[1];
		Long lg = (Long)DBUtil.getValue(sql2, param);
		
		Pagenation p = new Pagenation(pageNum,lg.intValue(),size);
		
		System.out.println("tangHibernate:"+sql1);
		Object[] param2 = null;
		if(param!=null){
			param2 = new Object[param.length+2];
			for (int i = 0; i < param.length; i++) {
				param2[i] = param[i];
			}
			param2[param.length+1] = p.getStartRow();
			param2[param.length+2] = p.getSize();
		} else{
			param2 = new Object[2];
			param2[0] = p.getStartRow();
			param2[1] = p.getSize();
		}
		p.setList(DBUtil.getRowsFields(sql1,param2,c));
		return p;
	}
	
	

/*	public static void main(String[] args) {
		Connection c = DBUtil.getConnection();
		System.out.println(c);
	}*/
	
}




