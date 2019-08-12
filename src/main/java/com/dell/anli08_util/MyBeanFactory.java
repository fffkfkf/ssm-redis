package com.dell.anli08_util;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;
//import org.dom4j.Document;

/*
 * 使用工具类,降低模块之间的耦合度
 */
public class MyBeanFactory {
	public static Object getBean(String id){
		try {
			//1.获取document对象
			Document doc=new SAXReader().read(MyBeanFactory.class.getClassLoader().getResourceAsStream("beans.xml"));

			//2.调用api selectSingleNode(表达式)
			Element beanEle=(Element) doc.selectSingleNode("//bean[@id='"+id+"']");

			//3.获取元素的class属性
			String classValue = beanEle.attributeValue("class");
			
			//4.通过反射返回实现类的对象
			final Object newInstance = Class.forName(classValue).newInstance();
			//------------------判断newInstance这个类是不是Dao层的对象,如果是,则生成一个动态代理对象,使用动态代理的调用处理流程,处理dao中的所有方法--------------------------------
			if(id.endsWith("Dao")){
				//创建一个动态代理对象
				Object o=Proxy.newProxyInstance(newInstance.getClass().getClassLoader(),newInstance.getClass().getInterfaces(), new InvocationHandler() {
					//调用处理流程
                    @Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						//method就是要执行的方法对象,args就是实际参数
						String className = newInstance.getClass().getName();
						String methodName = method.getName();
						System.out.println(className+"在:"+new Date().toLocaleString()+" 执行了:"+methodName);
						//让method对象执行
						return method.invoke(newInstance, args);
					}
				});
				return o;
			}
			//--------------------------------------------------
			return newInstance;
		}  catch (Exception e) {
			e.printStackTrace();
			System.out.println("获取bean失败");
			throw new RuntimeException();
		}
	}
}
