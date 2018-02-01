/*
 * RequestHolder.java 1.0.0 2018/2/1  下午 4:42 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/1  下午 4:42 created by lbb
 */
package com.mmall.common;

import com.mmall.model.SysUser;
import com.mmall.util.IpUtil;

import javax.servlet.http.HttpServletRequest;

public class RequestHolder {

    private static final ThreadLocal<SysUser> userHolder = new ThreadLocal<>();

    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

    public static void add(SysUser sysUser) {
        userHolder.set(sysUser);
    }

    public static SysUser getCurrentUser() {
        return userHolder.get();
    }

    public static void add(HttpServletRequest request) {
        requestHolder.set(request);
    }

    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }

    public static void remove() {
        userHolder.remove();
        requestHolder.remove();
    }

    public static String getCurrentUserName() {
        SysUser sysUser = userHolder.get();
        if (sysUser != null)
            return sysUser.getUsername();
        return null;
    }

    public static String getRequestIp() {
        HttpServletRequest request = requestHolder.get();
        if (request != null)
            return IpUtil.getRemoteIp(request);
        return null;
    }
}
