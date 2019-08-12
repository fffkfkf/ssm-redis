package com.dell.anli08_util;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import java.util.Date;
import java.util.Map;

public class MyBeanUtilsPlus {
	/*
	 * 使用泛型的工具类
	 */
	//泛型方法
	public static <T> T populate(Class<T> c, Map<String,String[]> map) throws Exception {
		//1:使用t创建一个普通对象;
		T o=c.newInstance();
		DateConverter dc = new DateConverter();
		//设置支持的字符串格式
		dc.setPatterns(new String[]{"yyyy-MM-dd","yyyy/MM/dd","yyyy年MM月dd","yyyy年MM月dd日"});
		//注册转换器
		ConvertUtils.register(dc, Date.class);
		//开始封装
		BeanUtils.populate(o, map);
		return o;
	}
}
