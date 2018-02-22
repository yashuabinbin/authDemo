/*
 * SysRoleController.java 1.0.0 2018/2/13  上午 10:10 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/13  上午 10:10 created by lbb
 */
package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.param.RoleParam;
import com.mmall.service.SysRoleAclService;
import com.mmall.service.SysRoleService;
import com.mmall.service.SysTreeService;
import com.mmall.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysTreeService sysTreeService;

    @Autowired
    private SysRoleAclService sysRoleAclService;

    /**
     * 跳转到角色页面
     *
     * @return
     */
    @RequestMapping(value = "/role.page")
    public ModelAndView page() {
        return new ModelAndView("role");
    }

    /**
     * 保存角色
     *
     * @param roleParam
     * @return
     */
    @RequestMapping(value = "/save.json")
    @ResponseBody
    public JsonData saveRole(RoleParam roleParam) {
        sysRoleService.save(roleParam);
        return JsonData.success();
    }

    /**
     * 更新角色
     *
     * @param roleParam
     * @return
     */
    @RequestMapping(value = "/update.json")
    @ResponseBody
    public JsonData updateRole(RoleParam roleParam) {
        sysRoleService.update(roleParam);
        return JsonData.success();
    }

    /**
     * 获取角色列表
     *
     * @return
     */
    @RequestMapping(value = "/list.json")
    @ResponseBody
    public JsonData list() {
        return JsonData.success(sysRoleService.getAll());
    }

    /**
     * 返回权限模块与权限点组成的树
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/roleTree.json")
    @ResponseBody
    public JsonData roleTree(@RequestParam("roleId") Integer roleId) {
        return JsonData.success(sysTreeService.roleTree(roleId));
    }

    /**
     * 改变权限选择
     *
     * @param roleId
     * @param aclIds
     * @return
     */
    @RequestMapping(value = "/changeAcls.json")
    @ResponseBody
    public JsonData changeAcls(@RequestParam(value = "roleId") Integer roleId,
            @RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds) {
        List<Integer> aclIdList = StringUtil.split2ListInt(aclIds, ",");
        sysRoleAclService.changeRoleAcls(roleId, aclIdList);
        return JsonData.success();
    }

}
