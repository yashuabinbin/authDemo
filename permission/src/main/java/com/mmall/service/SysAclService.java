/*
 * SysAclService.java 1.0.0 2018/2/12  下午 5:06 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/12  下午 5:06 created by lbb
 */
package com.mmall.service;

import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysAcl;
import com.mmall.param.AclParam;
import com.mmall.util.BeanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SysAclService {

    @Autowired
    private SysAclMapper sysAclMapper;

    public void save(AclParam aclParam) {
        BeanValidator.check(aclParam);
        if (checkExist(aclParam.getAclModuleId(), aclParam.getName(), aclParam.getId())) {
            throw new ParamException("当前权限模块下存在相同名称的权限点");
        }

        SysAcl sysAcl = new SysAcl();
        sysAcl.setAclModuleId(aclParam.getAclModuleId());
        sysAcl.setId(aclParam.getId());
        sysAcl.setName(aclParam.getName());
        sysAcl.setRemark(aclParam.getRemark());
        sysAcl.setSeq(aclParam.getSeq());
        sysAcl.setStatus(aclParam.getStatus());
        sysAcl.setType(aclParam.getType());
        sysAcl.setOperateTime(new Date());
        sysAcl.setOperator(RequestHolder.getCurrentUserName());
        sysAcl.setUrl(aclParam.getUrl());
        sysAcl.setOperateIp(RequestHolder.getRequestIp());
        sysAcl.setCode(generateCode());
        sysAclMapper.insert(sysAcl);
    }

    public void update(AclParam aclParam) {
        BeanValidator.check(aclParam);
        if (checkExist(aclParam.getAclModuleId(), aclParam.getName(), aclParam.getId())) {
            throw new ParamException("当前权限模块下存在相同名称的权限点");
        }

        SysAcl beforeSysAcl = sysAclMapper.selectByPrimaryKey(aclParam.getId());
        if (beforeSysAcl == null) {
            throw new ParamException("待更新的权限点不存在");
        }

        SysAcl afterSysAcl = new SysAcl();
        afterSysAcl.setAclModuleId(aclParam.getAclModuleId());
        afterSysAcl.setId(aclParam.getId());
        afterSysAcl.setName(aclParam.getName());
        afterSysAcl.setRemark(aclParam.getRemark());
        afterSysAcl.setSeq(aclParam.getSeq());
        afterSysAcl.setStatus(aclParam.getStatus());
        afterSysAcl.setType(aclParam.getType());
        afterSysAcl.setOperateTime(new Date());
        afterSysAcl.setOperator(RequestHolder.getCurrentUserName());
        afterSysAcl.setUrl(aclParam.getUrl());
        afterSysAcl.setOperateIp(RequestHolder.getRequestIp());
        afterSysAcl.setCode(generateCode());
        sysAclMapper.updateByPrimaryKeySelective(afterSysAcl);
    }

    public boolean checkExist(int aclModuleId, String name, Integer id) {
        return sysAclMapper.countByNameAndAclModuleId(aclModuleId, name, id) > 0;
    }

    private String generateCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date() + "_" + (int) (Math.random() * 100));
    }

    public PageResult<SysAcl> getPageByAclModuleId(Integer aclModuleId, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);
        PageResult<SysAcl> pageResult = new PageResult<>();

        int count = sysAclMapper.countByAclModuleId(aclModuleId);
        if (count != 0) {
            List<SysAcl> sysAclList = sysAclMapper.selectPageByAclModuleId(aclModuleId, pageQuery);
            pageResult.setTotal(count);
            pageResult.setData(sysAclList);
        }

        return pageResult;
    }

}
