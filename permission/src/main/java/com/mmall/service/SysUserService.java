/*
 * SysUserService.java 1.0.0 2018/1/30  上午 11:33 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/1/30  上午 11:33 created by lbb
 */
package com.mmall.service;

import com.mmall.dao.SysUserMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysUser;
import com.mmall.param.UserParam;
import com.mmall.util.BeanValidator;
import com.mmall.util.MD5Util;
import com.mmall.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    public void save(UserParam userParam) {
        BeanValidator.check(userParam);

        if (checkTelephoneExist(userParam.getTelephone(), userParam.getId())) {
            throw new ParamException("电话已被占用");
        }

        if (checkEmailExist(userParam.getMail(), userParam.getId())) {
            throw new ParamException("邮箱已被占用");
        }

        SysUser sysUser = new SysUser();
        sysUser.setMail(userParam.getMail());
        sysUser.setDeptId(userParam.getDepId());
        sysUser.setRemark(userParam.getRemark());
        sysUser.setStatus(userParam.getStatus());
        sysUser.setTelephone(userParam.getTelephone());
        sysUser.setPassword(MD5Util.encrypt(PasswordUtil.generatePassword()));
        sysUser.setOperateIp("127.0.0.1");
        sysUser.setOperator("admin");
        sysUser.setOperateTime(new Date());

        sysUserMapper.insertSelective(sysUser);
    }

    public void update(UserParam userParam) {
        BeanValidator.check(userParam);

        if (checkTelephoneExist(userParam.getTelephone(), userParam.getId())) {
            throw new ParamException("电话已被占用");
        }

        if (checkEmailExist(userParam.getMail(), userParam.getId())) {
            throw new ParamException("邮箱已被占用");
        }

        SysUser beforeSysUser = sysUserMapper.selectByPrimaryKey(userParam.getId());

        SysUser afterSysUser = new SysUser();
        afterSysUser.setId(userParam.getId());
        afterSysUser.setMail(userParam.getMail());
        afterSysUser.setDeptId(userParam.getDepId());
        afterSysUser.setRemark(userParam.getRemark());
        afterSysUser.setStatus(userParam.getStatus());
        afterSysUser.setTelephone(userParam.getTelephone());
        afterSysUser.setOperateIp("127.0.0.1");
        afterSysUser.setOperator("update_admin");
        afterSysUser.setOperateTime(new Date());

        sysUserMapper.updateByPrimaryKeySelective(afterSysUser);
    }

    private boolean checkEmailExist(String mail, Integer userId) {
        return false;
    }

    private boolean checkTelephoneExist(String telephone, Integer userId) {
        return false;
    }

    public SysUser findByKeyword(String username) {
        return sysUserMapper.findByKeyword(username);
    }
}
