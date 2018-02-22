/*
 * SysRoleService.java 1.0.0 2018/2/13  上午 10:11 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/13  上午 10:11 created by lbb
 */
package com.mmall.service;

import com.mmall.common.RequestHolder;
import com.mmall.dao.SysRoleMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysRole;
import com.mmall.param.RoleParam;
import com.mmall.util.BeanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    public void save(RoleParam roleParam) {
        BeanValidator.check(roleParam);
        if (checkExist(roleParam.getName(), null)) {
            throw new ParamException("角色名称已经存在");
        }

        SysRole sysRole = new SysRole();
        sysRole.setName(roleParam.getName());
        sysRole.setRemark(roleParam.getRemark());
        sysRole.setStatus(roleParam.getStatus());
        sysRole.setType(SysRole.NOT_ADMIN);
        sysRole.setOperator(RequestHolder.getCurrentUserName());
        sysRole.setOperateIp(RequestHolder.getRequestIp());
        sysRole.setOperateTime(new Date());
        sysRoleMapper.insert(sysRole);
    }

    public void update(RoleParam roleParam) {
        BeanValidator.check(roleParam);

        if (checkExist(roleParam.getName(), roleParam.getId())) {
            throw new ParamException("角色名称已经存在");
        }

        SysRole beforeSysRole = sysRoleMapper.selectByPrimaryKey(roleParam.getId());
        if (beforeSysRole == null) {
            throw new ParamException("待修改角色不存在");
        }

        SysRole afterSysRole = new SysRole();
        afterSysRole.setId(roleParam.getId());
        afterSysRole.setName(roleParam.getName());
        afterSysRole.setRemark(roleParam.getRemark());
        afterSysRole.setStatus(roleParam.getStatus());
        afterSysRole.setOperator(RequestHolder.getCurrentUserName());
        afterSysRole.setOperateIp(RequestHolder.getRequestIp());
        afterSysRole.setOperateTime(new Date());
        sysRoleMapper.updateByPrimaryKeySelective(afterSysRole);
    }

    public List<SysRole> getAll(){
        return sysRoleMapper.selectAll();
    }

    private boolean checkExist(String name, Integer id) {
        return sysRoleMapper.countByNameAndId(name, id) > 0;
    }

}
