/*
 * TestController.java 1.0.0 2018/1/25  下午 2:03 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/1/25  下午 2:03 created by lbb
 */
package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.dao.SysAclModuleMapper;
import com.mmall.exception.PermissionException;
import com.mmall.model.SysAclModule;
import com.mmall.param.TestVo;
import com.mmall.common.ApplicationContextHelper;
import com.mmall.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/test")
@Slf4j
public class TestController {

    @RequestMapping(value = "/hello.json")
    @ResponseBody
    public JsonData hello() {
        log.info("hello log");
        throw new PermissionException("111111");
//        return JsonData.success("hello");
    }

    @RequestMapping(value = "/validate.json")
    @ResponseBody
    public JsonData validate(TestVo testVo) {
        log.info("validate");

        SysAclModuleMapper moduleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule module = moduleMapper.selectByPrimaryKey(1);
        log.info(JsonMapper.obj2String(module));

        return JsonData.success("test validate");
    }
}
