/*
 * SysCacheService.java 1.0.0 2018/2/23  下午 1:59 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/23  下午 1:59 created by lbb
 */
package com.mmall.service;

import com.google.common.base.Joiner;
import com.mmall.beans.CacheKeyConstants;
import com.mmall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Slf4j
@Service
public class SysCacheService {

    @Autowired
    private RedisPool redisPool;

    public void saveCache(String toSaveValue, int timeoutSeconds, CacheKeyConstants prefix) {
        saveCache(toSaveValue, timeoutSeconds, prefix, null);
    }

    /**
     * 向redis中存储键值对
     *
     * @param toSaveValue
     * @param timeoutSeconds
     * @param prefix
     * @param keys
     */
    public void saveCache(String toSaveValue, int timeoutSeconds, CacheKeyConstants prefix, String... keys) {
        if (toSaveValue == null) {
            return;
        }

        Jedis jedis = null;
        try {
            jedis = redisPool.instance();

            String cacheKey = generateCacheKey(prefix, keys);
            jedis.setex(cacheKey, timeoutSeconds, toSaveValue);
        } catch (Exception e) {
            log.error("redis save value error! prefix:{}, keys:{}, toSaveValue:{}", prefix, keys, toSaveValue);
        } finally {
            RedisPool.safeClose(jedis);
        }
    }

    private String generateCacheKey(CacheKeyConstants prefix, String... keys) {
        String key = prefix.name();
        if (keys != null && keys.length > 0) {
            key += "_" + Joiner.on("_").join(keys);
        }
        return key;
    }

}
