package com.bjsxt.softrc.util;

public class BeanUtil {
	
	//返回set方法
	public static String getSetterName(String fieldName){
		return "set"+fieldName.toUpperCase().substring(0,1)+fieldName.substring(1);
	}
	
	//返回get方法
	public static String getGetterName(String fieldName){
		return "get"+fieldName.toUpperCase().substring(0,1)+fieldName.substring(1);
	}
	
	
}
