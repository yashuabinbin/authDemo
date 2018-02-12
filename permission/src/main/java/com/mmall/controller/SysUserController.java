/*
 * SysUserController.java 1.0.0 2018/1/30  上午 11:32 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/1/30  上午 11:32 created by lbb
 */
package com.mmall.controller;

import com.mmall.beans.PageQuery;
import com.mmall.common.JsonData;
import com.mmall.param.UserParam;
import com.mmall.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/sys/user")
@Slf4j
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取分页用户列表
     *
     * @param depId
     * @param pageQuery
     * @return
     */
    @RequestMapping(value = "/page.json")
    @ResponseBody
    public JsonData page(@RequestParam(value = "deptId") Integer depId, PageQuery pageQuery) {
        return JsonData.success(sysUserService.getPageByDepId(depId, pageQuery));
    }

    /**
     * 保存用户
     *
     * @param userParam
     * @return
     */
    @RequestMapping(value = "/save.json")
    @ResponseBody
    public JsonData saveUser(UserParam userParam) {
        sysUserService.save(userParam);
        return JsonData.success("保存用户成功");
    }

    /**
     * 更新用户
     *
     * @param userParam
     * @return
     */
    @RequestMapping(value = "/update.json")
    @ResponseBody
    public JsonData updateUser(UserParam userParam) {
        sysUserService.update(userParam);
        return JsonData.success("更新用户成功");
    }
}
