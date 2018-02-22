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

@Service
public class SysCoreService {

    @Autowired
    private SysAclMapper sysAclMapper;

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

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

    public boolean isSuperAdmin() {
        SysUser user = RequestHolder.getCurrentUser();
        return false;
    }
}
