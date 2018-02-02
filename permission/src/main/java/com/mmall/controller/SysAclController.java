/*
 * SysAclController.java 1.0.0 2018/2/1  下午 5:26 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/1  下午 5:26 created by lbb
 */
package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.dto.AclModuleLevelDto;
import com.mmall.param.AclModuleParam;
import com.mmall.service.SysAclModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/sys/aclModule")
@Slf4j
public class SysAclController {

    @Autowired
    private SysAclModuleService sysAclModuleService;

    /**
     * 跳转到权限页面
     *
     * @return
     */
    @RequestMapping(value = "/acl.page")
    public ModelAndView acl() {
        return new ModelAndView("acl");
    }

    /**
     * 保存权限模块
     *
     * @param aclModuleParam
     * @return
     */
    @RequestMapping(value = "/save.json")
    @ResponseBody
    public JsonData saveAclModule(AclModuleParam aclModuleParam) {
        sysAclModuleService.save(aclModuleParam);
        return JsonData.success();
    }

    /**
     * 更新权限模块
     *
     * @param aclModuleParam
     * @return
     */
    @RequestMapping(value = "/update.json")
    @ResponseBody
    public JsonData updateAclModule(AclModuleParam aclModuleParam) {
        sysAclModuleService.update(aclModuleParam);
        return JsonData.success();
    }

    /**
     * 获取权限树
     *
     * @return
     */
    @RequestMapping(value = "/tree.json")
    @ResponseBody
    public JsonData tree() {
        List<AclModuleLevelDto> aclTree = sysAclModuleService.aclModuleTree();
        return JsonData.success(aclTree);
    }

    /**
     * 删除权限
     *
     * @param moduleId
     * @return
     */
    @RequestMapping(value = "/delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam(value = "id") Integer moduleId) {
        sysAclModuleService.delete(moduleId);
        return JsonData.success();
    }
}
