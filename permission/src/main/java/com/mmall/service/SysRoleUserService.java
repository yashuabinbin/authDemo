/*
 * SysRoleUserService.java 1.0.0 2018/2/22  下午 4:52 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/22  下午 4:52 created by lbb
 */
package com.mmall.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysRoleUserMapper;
import com.mmall.dao.SysUserMapper;
import com.mmall.model.SysRoleUser;
import com.mmall.model.SysUser;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    /**
     * 根据角色id获取用户列表
     *
     * @param roleId
     * @return
     */
    public List<SysUser> getUserListByRoleId(int roleId) {
        List<Integer> userIdList = sysRoleUserMapper.selectUserIdListByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        } else {
            return sysUserMapper.selectByUserIdList(userIdList);
        }
    }

    /**
     * 修改角色与用户的对应关系
     *
     * @param roleId
     * @param userIdList
     */
    @Transactional
    public void changeRoleUsers(Integer roleId, List<Integer> userIdList) {
        List<Integer> oldUserIds = sysRoleUserMapper.selectUserIdListByRoleId(roleId);
        if (userIdList.size() == oldUserIds.size()) {
            Set<Integer> oldUserIdSet = Sets.newHashSet(oldUserIds);
            Set<Integer> newUserIdSet = Sets.newHashSet(userIdList);

            oldUserIdSet.removeAll(newUserIdSet);
            if (oldUserIdSet.size() == 0)
                return;
        }

        updateRoleUsers(roleId, userIdList);
    }

    private void updateRoleUsers(Integer roleId, List<Integer> userIdList) {
        sysRoleUserMapper.deleteByRoleId(roleId);

        List<SysRoleUser> roleUserList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(userIdList)) {
            for (Integer userId : userIdList) {
                SysRoleUser sysRoleUser = new SysRoleUser();
                sysRoleUser.setRoleId(roleId);
                sysRoleUser.setUserId(userId);
                sysRoleUser.setOperateIp(RequestHolder.getRequestIp());
                sysRoleUser.setOperateTime(new Date());
                sysRoleUser.setOperator(RequestHolder.getCurrentUserName());
                roleUserList.add(sysRoleUser);
            }
            sysRoleUserMapper.insertBatch(roleUserList);
        }
    }
}
