/*
 * JsonMapper.java 1.0.0 2018/1/26  上午 10:44 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/1/26  上午 10:44 created by lbb
 */
package com.mmall.util;

import com.mmall.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;

@Slf4j
public class JsonMapper {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_NULL_FOR_PRIMITIVES);
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    }

    public static <T> String obj2String(T src) {
        if (src == null)
            return null;

        try {
            return src instanceof String ? (String) src : objectMapper.writeValueAsString(src);
        } catch (IOException e) {
            log.warn("parse object to String exception", e);
            return null;
        }
    }

    public static <T> T string2Obj(String src, TypeReference<T> typeReference) {
        if (src == null || typeReference == null)
            return null;

        try {
            return (T) (typeReference.getType().equals(String.class) ? src : objectMapper.readValue(src, typeReference));
        } catch (Exception e) {
            log.warn("parse String to Object exception, String:{}, TypeReference:{}, error:{}", src, typeReference, e);
            return null;
        }
    }

    public static void main(String[] args) {
        SysUser user = new SysUser();
        user.setId(1);
        user.setMail("luybingbin@qq.com");
        user.setOperateIp("192.168.12.1");
        user.setPassword("11");
        String userJson = JsonMapper.obj2String(user);
        System.out.println(userJson);

        user = null;
        user = JsonMapper.string2Obj(userJson, new TypeReference<SysUser>() {
        });
        System.out.println(user);
    }
}
