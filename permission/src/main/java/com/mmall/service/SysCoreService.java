/*
 * SysCoreService.java 1.0.0 2018/2/13  下午 3:06 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/13  下午 3:06 created by lbb
 */
package com.mmall.service;

import com.google.common.collect.Lists;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysRoleAclMapper;
import com.mmall.dao.SysRoleUserMapper;
import com.mmall.model.SysAcl;
import com.mmall.model.SysUser;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysCoreService {

    @Autowired
    private SysAclMapper sysAclMapper;

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    /**
     * 获取当前用户所拥有的权限点
     *
     * @return
     */
    public List<SysAcl> getCurrentUserAclList() {
        SysUser user = RequestHolder.getCurrentUser();
        return getUserAclList(user.getId());
    }

    /**
     * 查询某个角色所拥有的角色
     *
     * @param roleId
     * @return
     */
    public List<SysAcl> getRoleAclList(int roleId) {
        List<Integer> aclIdList = sysRoleAclMapper.selectAclIdListByRoleIdList(Arrays.asList(roleId));
        if (CollectionUtils.isEmpty(aclIdList)) {
            return Lists.newArrayList();
        }

        return sysAclMapper.selectByIdList(aclIdList);
    }

    /**
     * 查询某个用户所拥有的角色
     *
     * @param userId
     * @return
     */
    public List<SysAcl> getUserAclList(int userId) {
        if (isSuperAdmin()) {
            return sysAclMapper.selectAll();
        }

        List<Integer> roleIdList = sysRoleUserMapper.selectRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }

        List<Integer> aclIdList = sysRoleAclMapper.selectAclIdListByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(aclIdList)) {
            return Lists.newArrayList();
        }

        return sysAclMapper.selectByIdList(aclIdList);
    }

    /**
     * 是否是管理员
     *
     * @return
     */
    public boolean isSuperAdmin() {
        SysUser user = RequestHolder.getCurrentUser();
        if (user.getUsername().equals("Admin")) {
            return true;
        }
        return false;
    }

    /**
     * 是否有该权限点
     *
     * @param visitPath
     * @return
     */
    public boolean hasUrlAcl(String visitPath) {
        if (isSuperAdmin()) {
            return true;
        }

        List<SysAcl> sysAclList = sysAclMapper.selectByUrl(visitPath);
        if (CollectionUtils.isEmpty(sysAclList)) {
            return true;
        }

        List<SysAcl> currentUserAclList = getCurrentUserAclList();
        if (CollectionUtils.isEmpty(currentUserAclList)) {
            return false;
        }

        boolean hasValidAcl = false;
        Set<Integer> currentUserAclIdSet = currentUserAclList.stream().map(SysAcl::getId).collect(Collectors.toSet());
        for (SysAcl sysAcl : sysAclList) {
            if (sysAcl == null || sysAcl.getStatus() == SysAcl.STATUS_FREEZE) {
                continue;
            }

            hasValidAcl = true;
            if (currentUserAclIdSet.contains(sysAcl.getId())) {
                return true;
            }
        }

        if (!hasValidAcl) {
            return true;
        }

        return false;
    }
}
