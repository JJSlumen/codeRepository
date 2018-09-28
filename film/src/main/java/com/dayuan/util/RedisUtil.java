package com.dayuan.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

    static JedisPool pool = null;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(8);//最大空闲数
        config.setMaxTotal(18);//最大连接数
        pool = new JedisPool(config, "182.61.40.167", 6379, 2000, "strange");

    }


    public static void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();//获取连接
            jedis.set(key, value);
        } catch (Exception e) {
//            log.error();
        } finally {
            jedis.close();
            pool.close();
        }


    }


    public static void main(String[] args) {
//        Jedis jedis = new Jedis("localhost", 6380);
//        jedis.auth("123123");
//
////        jedis.set("foo", "bar");
//        String value = jedis.get("foo");
//
//        jedis.expire("foo", 5);
//
//        System.out.println(value);
//
//
//        jedis.hset("id0", "userName", "小A同学");
//
//        String hget = jedis.hget("id0", "userName");
//        System.out.println(hget);


        Jedis jedis = pool.getResource();//获取连接
        jedis.set("id","6217");
        System.out.println(jedis.keys("*"));
        String value = jedis.get("id");
        System.out.println(value);
        jedis.close();
        pool.close();

    }

}
