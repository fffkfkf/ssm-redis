package com.dell.anli08_util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/*
 * 获取jedis对象的工具类
 */
public class MyJedisUtils {
	private static JedisPool jp;
	//使用静态代码块初始化jedisPoolConfig对象
	static{
		
		JedisPoolConfig jpc = new JedisPoolConfig();
		jpc.setMaxTotal(20);
		jpc.setMaxIdle(2);
		jp = new JedisPool(jpc,"192.168.116.129",6379);
	}
	//提供一个静态方法,用于获取jedis对象
	public static Jedis getJedis(){
		return jp.getResource();
	}
}
