/*
 * SysUserService.java 1.0.0 2018/1/30  上午 11:33 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/1/30  上午 11:33 created by lbb
 */
package com.mmall.service;

import com.google.common.collect.Lists;
import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysRoleUserMapper;
import com.mmall.dao.SysUserMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysRole;
import com.mmall.model.SysUser;
import com.mmall.param.UserParam;
import com.mmall.util.BeanValidator;
import com.mmall.util.MD5Util;
import com.mmall.util.PasswordUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

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
        sysUser.setDeptId(userParam.getDeptId());
        sysUser.setRemark(userParam.getRemark());
        sysUser.setStatus(userParam.getStatus());
        sysUser.setUsername(userParam.getUsername());
        sysUser.setTelephone(userParam.getTelephone());
        sysUser.setPassword(MD5Util.encrypt(PasswordUtil.generatePassword()));
        sysUser.setOperateIp(RequestHolder.getRequestIp());
        sysUser.setOperator(RequestHolder.getCurrentUserName());
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
        if (beforeSysUser == null)
            throw new ParamException("待更新用户不存在");

        SysUser afterSysUser = new SysUser();
        afterSysUser.setId(userParam.getId());
        afterSysUser.setMail(userParam.getMail());
        afterSysUser.setUsername(userParam.getUsername());
        afterSysUser.setDeptId(userParam.getDeptId());
        afterSysUser.setRemark(userParam.getRemark());
        afterSysUser.setStatus(userParam.getStatus());
        afterSysUser.setTelephone(userParam.getTelephone());
        afterSysUser.setOperateIp(RequestHolder.getRequestIp());
        afterSysUser.setOperator(RequestHolder.getCurrentUserName());
        afterSysUser.setOperateTime(new Date());

        sysUserMapper.updateByPrimaryKeySelective(afterSysUser);
    }

    private boolean checkEmailExist(String mail, Integer userId) {
        return sysUserMapper.countByEmail(mail, userId) > 0;
    }

    private boolean checkTelephoneExist(String telephone, Integer userId) {
        return sysUserMapper.countByTelephone(telephone, userId) > 0;
    }

    public SysUser findByKeyword(String username) {
        return sysUserMapper.findByKeyword(username);
    }

    public PageResult<SysUser> getPageByDepId(Integer depId, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);
        int count = sysUserMapper.countByDepId(depId);
        if (count > 0) {
            List<SysUser> userList = sysUserMapper.selectPageByDepId(depId, pageQuery);
            PageResult<SysUser> pageResult = new PageResult();
            pageResult.setTotal(count);
            pageResult.setData(userList);
            return pageResult;
        }

        return new PageResult<SysUser>();
    }

    /**
     * 获取所有角色
     *
     * @return
     */
    public List<SysUser> getAll() {
        return sysUserMapper.selectAll();
    }

    /**
     * 根据角色列表获取用户列表
     *
     * @param roleList
     * @return
     */
    public List<SysUser> getUserListByRoleList(List<SysRole> roleList) {
        if (CollectionUtils.isEmpty(roleList)) {
            return Lists.newArrayList();
        }

        List<Integer> roleIdList = roleList.stream().map(role -> role.getId()).collect(Collectors.toList());
        List<Integer> userIdList = sysRoleUserMapper.selectUserIdListByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }

        return sysUserMapper.selectByUserIdList(userIdList);
    }
}
