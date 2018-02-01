/*
 * userController.java 1.0.0 2018/1/30  下午 2:32 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/1/30  下午 2:32 created by lbb
 */
package com.mmall.controller;

import com.mmall.common.Const;
import com.mmall.model.SysUser;
import com.mmall.service.SysUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登出
     *
     * @param session
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "logout.page")
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {
        session.invalidate();
        response.sendRedirect("signin.jsp");
    }

    /**
     * 登录
     *
     * @param session
     * @param request
     * @param response
     * @param username
     * @param password
     * @param ret
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping(value = "login.page")
    public void login(HttpSession session,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "ret", required = false) String ret) throws IOException, ServletException {
        String errorMsg = "";

        if (StringUtils.isBlank(username)) {
            errorMsg = "用户名不能为空";
        } else if (StringUtils.isBlank(password)) {
            errorMsg = "密码不能为空";
        } else {
            SysUser sysUser = sysUserService.findByKeyword(username);

            if (sysUser == null || !sysUser.getPassword().equals(MD5Util.encrypt(password))) {
                errorMsg = "用户名或者密码错误";
            } else if (sysUser.getStatus() != 1) {
                errorMsg = "用户已经被冻结，请联系管理员";
            } else {
                session.setAttribute(Const.CURRENT_USER, sysUser);
                System.out.println(StringUtils.isBlank(ret));
                response.sendRedirect(StringUtils.isBlank(ret) ? "/admin/index.page" : ret);
                return;
            }
        }

        request.setAttribute("error", errorMsg);
        request.setAttribute("username", username);
        if (StringUtils.isNotBlank(ret)) {
            request.setAttribute("ret", ret);
        }

        String path = "signin.jsp";
        request.getRequestDispatcher(path).forward(request, response);
    }
}
