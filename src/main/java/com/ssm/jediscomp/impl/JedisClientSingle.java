package com.ssm.jediscomp.impl;



import org.springframework.beans.factory.annotation.Autowired;

import com.ssm.jediscomp.JedisClient;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Author  飞鸟
 * Created by kauw on 2016/10/18.
 */
public class JedisClientSingle implements JedisClient {

    @Autowired
    private JedisPool jedisPool;

    public String get(String key) {
        Jedis jedis=jedisPool.getResource();
        String result=jedis.get(key);
        jedis.close();
        return result;
    }

    public String set(String key, String value) {
        Jedis jedis=jedisPool.getResource();
        String result=jedis.set(key, value);
        jedis.close();
        return result;
    }

    public String hget(String hkey, String key) {
        Jedis jedis=jedisPool.getResource();
        String result=jedis.hget(hkey, key);
        jedis.close();
        return result;
    }

    public long hset(String hkey, String key, String value) {
        Jedis jedis=jedisPool.getResource();
        Long result = jedis.hset(hkey, key, value);
        jedis.close();
        return result;
    }

    public long incr(String key) {
        Jedis jedis=jedisPool.getResource();
        Long result = jedis.incr(key);
        jedis.close();
        return result;
    }

    public long decr(String key) {
        Jedis jedis=jedisPool.getResource();
        Long result = jedis.decr(key);
        jedis.close();
        return result;
    }

    public long expire(String key, int second) {
        Jedis jedis=jedisPool.getResource();
        Long result = jedis.expire(key, second);
        jedis.close();
        return result;
    }

    public long ttl(String key) {
        Jedis jedis=jedisPool.getResource();
        Long result = jedis.ttl(key);
        jedis.close();
        return result;
    }

    public long del(String key) {
        Jedis jedis=jedisPool.getResource();
        Long result = jedis.del(key);
        jedis.close();
        return result;
    }

    public long hdel(String hkey, String key) {
        Jedis jedis=jedisPool.getResource();
        Long result = jedis.hdel(hkey, key);
        jedis.close();
        return result;
    }
}
