package com.bjsxt.softrc.test;

public class Person {
	private static int age=10;
	private String name;
	
	public Person(String name){
		super();
		this.name = name;
	}
	
	public Person(){
		
	}
	
	public static int getAge() {
		return age;
	}
	public static void setAge(int age) {
		Person.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
