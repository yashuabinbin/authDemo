/*
 * SysRoleAclService.java 1.0.0 2018/2/22  下午 2:39 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/22  下午 2:39 created by lbb
 */
package com.mmall.service;

import com.google.common.collect.Sets;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysRoleAclMapper;
import com.mmall.model.SysRoleAcl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SysRoleAclService {

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Transactional
    public void changeRoleAcls(Integer roleId, List<Integer> aclIdList) {
        List<Integer> oldAclIds = sysRoleAclMapper.selectAclIdListByRoleIdList(Arrays.asList(roleId));

        if (aclIdList.size() == oldAclIds.size()) {
            Set<Integer> oldAclIdSet = Sets.newHashSet(oldAclIds);
            Set<Integer> newAclIdSet = Sets.newHashSet(aclIdList);

            oldAclIdSet.removeAll(newAclIdSet);
            if (oldAclIdSet.size() == 0) {
                return;
            }
        }

        updateRoleAcls(roleId, aclIdList);
    }

    private void updateRoleAcls(Integer roleId, List<Integer> aclIdList) {
        // 删除该角色所拥有的所有权限
        sysRoleAclMapper.deleteByRoleId(roleId);

        if (aclIdList.size() == 0) {
            return;
        }

        List<SysRoleAcl> roleAclList = new ArrayList<>();
        for (Integer aclId : aclIdList) {
            SysRoleAcl sysRoleAcl = new SysRoleAcl();
            sysRoleAcl.setAclId(aclId);
            sysRoleAcl.setRoleId(roleId);
            sysRoleAcl.setOperateIp(RequestHolder.getRequestIp());
            sysRoleAcl.setOperateTime(new Date());
            sysRoleAcl.setOperator(RequestHolder.getCurrentUserName());
            roleAclList.add(sysRoleAcl);
        }

        sysRoleAclMapper.insertBatch(roleAclList);
    }
}
