package com.bjsxt.softrc.vo;

import java.sql.Date;

import com.bjsxt.softrc.po.User;
/*
 * 做展示信息时使用
 */
public class UserInfo {

		private int id;
		private String uname;
		private String pwd;
		private String email;
		private String phone;
		private int age;
		private String gender; //0:男 1:女
		private String address;
		private String degree; //0:高中1:专科2:本科3:硕士4:博士
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
		public String getGender() {
			return gender;
		}
		public void setGender(int gender) {
			if(gender==0){
				this.gender = "男";
			} else{
				this.gender = "女";
			}
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getDegree() {
			return degree;
		}
		public void setDegree(int degree) {
			switch(degree){
			case 0:
				this.degree = "高中";
				break;
			case 1:
				this.degree = "专科";
				break;
			case 2:
				this.degree = "本科";
				break;
			case 3:
				this.degree = "硕士";
				break;
			case 4:
				this.degree = "博士";
				break;
			}
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
		public UserInfo(String uname, String pwd, String email, String phone, int age,
				int gender, String address, int degree, Date joinTime, String ip) {
			super();
			this.uname = uname;
			this.pwd = pwd;
			this.email = email;
			this.phone = phone;
			this.age = age;
			setGender(gender);
			this.address = address;
			setDegree(degree);
			this.joinTime = joinTime;
			this.ip = ip;
		}
		
		public UserInfo(){
		}
		

		public UserInfo(int id, String uname, String pwd, String email, String phone,
				int age, int gender, String address, int degree, Date joinTime) {
			super();
			this.id = id;
			this.uname = uname;
			this.pwd = pwd;
			this.email = email;
			this.phone = phone;
			this.age = age;
			setGender(gender);
			this.address = address;
			setDegree(degree);
			this.joinTime = joinTime;
		}
		
		public UserInfo(User user){
			this.id = user.getId();
			this.uname = user.getUname();
			this.pwd = user.getPwd();
			this.email = user.getEmail();
			this.phone = user.getPhone();
			this.age = user.getAge();
			setGender(user.getGender());
			this.address = user.getAddress();
			setDegree(user.getDegree());
			this.joinTime = user.getJoinTime();
			this.ip = user.getIp();
		}
	

}
