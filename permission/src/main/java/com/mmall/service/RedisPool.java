/*
 * RedisPool.java 1.0.0 2018/2/23  下午 1:53 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/23  下午 1:53 created by lbb
 */
package com.mmall.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
@Slf4j
public class RedisPool {

    @Autowired
    private JedisPool jedisPool;

    public Jedis instance() {
        return jedisPool.getResource();
    }

    public void safeClose(Jedis jedis) {
        try {
            if (jedis != null) {
                jedis.close();
            }
        } catch (Exception e) {
            log.error("close jedisPool error! ", e);
        }
    }
}
