package com.bjsxt.softrc.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;


public class TestDWR {
	public void test1(){
		System.out.println("TestDWR.test1()");
	}
	
	public void test2(int a,String b ,boolean c){
		if(c){
			System.out.println(a);
		} else{
			System.out.println(b);
		}
	}
	
	public String test3(){
		return "sss";
	}
	
	public boolean test4(boolean b){
		if(b){
			System.out.println("bbbbb");
		}
		return true;
	}
	
	public Date test5(){
		return new Date(12421412);
	}
	
	public Teacher test6(){
		return new Teacher(1,"杨萌");
	}
	
	public void test7(Teacher t){
		System.out.println(t.getName());
	}
	
	public List<Teacher> test8(List<Teacher> ts){
		for (Teacher teacher : ts) {
			System.out.println(teacher.getName());
		}
		List<Teacher> list = new ArrayList<Teacher>();
		list.add(new Teacher(1,"汤"));
		list.add(new Teacher(2,"萌"));
		return list;
	}
	
	public void test9(){
		System.out.println("9999999999999999");
	}
	
	public void test10(){
		WebContext wc = WebContextFactory.get();
		wc.getSession();
	}
	
}
