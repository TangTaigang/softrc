package com.bjsxt.softrc.po;

import java.sql.Date;

public class User {
	private int id;
	private String uname;
	private String pwd;
	private String email;
	private String phone;
	private int age;
	private int gender; //0:男 1:女
	private String address;
	private int degree; //0:高中1:专科2:本科3:硕士4:博士
	private Date joinTime;
	private String ip;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getDegree() {
		return degree;
	}
	public void setDegree(int degree) {
		this.degree = degree;
	}
	public Date getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public User(String uname, String pwd, String email, String phone, int age,
			int gender, String address, int degree, Date joinTime, String ip) {
		super();
		this.uname = uname;
		this.pwd = pwd;
		this.email = email;
		this.phone = phone;
		this.age = age;
		this.gender = gender;
		this.address = address;
		this.degree = degree;
		this.joinTime = joinTime;
		this.ip = ip;
	}
	
	public User(){
	}
	

	//没有ip的构造器，为登陆时userDao查询到数据时封装数据使用
	public User(int id, String uname, String pwd, String email, String phone,
			int age, int gender, String address, int degree, Date joinTime) {
		super();
		this.id = id;
		this.uname = uname;
		this.pwd = pwd;
		this.email = email;
		this.phone = phone;
		this.age = age;
		this.gender = gender;
		this.address = address;
		this.degree = degree;
		this.joinTime = joinTime;
	}
	
	
}
