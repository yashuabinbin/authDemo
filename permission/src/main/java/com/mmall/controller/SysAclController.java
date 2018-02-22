/*
 * SysAclController.java 1.0.0 2018/2/12  下午 7:14 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/12  下午 7:14 created by lbb
 */
package com.mmall.controller;

import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.JsonData;
import com.mmall.model.SysAcl;
import com.mmall.model.SysRole;
import com.mmall.model.SysUser;
import com.mmall.param.AclParam;
import com.mmall.service.SysAclService;
import com.mmall.service.SysRoleService;
import com.mmall.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/sys/acl")
@Slf4j
public class SysAclController {

    @Autowired
    private SysAclService sysAclService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 保存权限点
     *
     * @param aclParam
     * @return
     */
    @RequestMapping(value = "/save.json")
    @ResponseBody
    public JsonData save(AclParam aclParam) {
        sysAclService.save(aclParam);
        return JsonData.success();
    }

    /**
     * 更新权限点
     *
     * @param aclParam
     * @return
     */
    @RequestMapping(value = "/update.json")
    @ResponseBody
    public JsonData update(AclParam aclParam) {
        sysAclService.update(aclParam);
        return JsonData.success();
    }

    /**
     * 获取权限点分页信息
     *
     * @param aclModuleId
     * @param pageQuery
     * @return
     */
    @RequestMapping(value = "/page.json")
    @ResponseBody
    public JsonData list(@RequestParam(value = "aclModuleId") Integer aclModuleId, PageQuery pageQuery) {
        PageResult<SysAcl> pageResult = sysAclService.getPageByAclModuleId(aclModuleId, pageQuery);
        return JsonData.success(pageResult);
    }

    /**
     * 根据权限id获取对应的角色及用户列表
     *
     * @param aclId
     * @return
     */
    @RequestMapping(value = "/acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam(value = "aclId") Integer aclId) {
        List<SysRole> roleList = sysRoleService.getRoleListByAclId(aclId);
        List<SysUser> userList = sysUserService.getUserListByRoleList(roleList);

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("roles", roleList);
        returnMap.put("users", userList);
        return JsonData.success(returnMap);
    }
}
