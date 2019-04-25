package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Auther: Administrator
 * @Date: 2019/4/20 21:44
 * @Description:
 */
public class RedisPool {
    private static JedisPool pool;//jedis连接池
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","10")); //可存放的最大空闲实例
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","2"));//可存放的最小空闲实例
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20"));//最大连接数
    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.on.borrow","true"));//取jedis实例时,对实例进行测试,保证取出的实例是可用的
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.on.return","true"));//还jedis实例时,对实例进行测试,保证还回的实例是可用的
    private static String ip = PropertiesUtil.getProperty("redis.ip");
    private static Integer port = Integer.parseInt(PropertiesUtil.getProperty("redis.port"));
    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        config.setMaxTotal(maxTotal);
        config.setBlockWhenExhausted(true); //当连接超过最大连接时, 配置false系统将抛出异常,配置true将会阻塞直到超时

        pool = new JedisPool(config,ip,port,1000*2);
    }
    static {
        initPool();
    }
    public static Jedis getJedis(){
        return pool.getResource();
    }
    public static void returnResource(Jedis jedis){
        pool.returnResource(jedis);
    }

    public static void returnBrokenResource(Jedis jedis){
        pool.returnBrokenResource(jedis);
    }
}