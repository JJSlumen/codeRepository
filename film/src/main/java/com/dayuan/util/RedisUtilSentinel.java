package com.dayuan.util;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RedisUtilSentinel {

    private static final JedisSentinelPool pool;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxIdle(5);
        jedisPoolConfig.setMinIdle(5);

        Set<String> sentinels = new HashSet<>(Arrays.asList(
                "182.61.40.167:26379",
                "182.61.40.167:26380",
                "182.61.40.167:26381"
        ));
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(5);
        pool = new JedisSentinelPool("mymaster", sentinels, jedisPoolConfig);
    }

    public static void main(String[] args) throws Exception {
        String key1 = "key3";
        try (Jedis jedis = pool.getResource()) {
            jedis.set(key1, "111");
            System.out.println(jedis.get(key1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
