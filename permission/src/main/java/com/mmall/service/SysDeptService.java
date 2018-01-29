/*
 * SysDeprService.java 1.0.0 2018/1/26  下午 2:33 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/1/26  下午 2:33 created by lbb
 */
package com.mmall.service;

import com.mmall.dao.SysDeptMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysDept;
import com.mmall.param.DeptParam;
import com.mmall.util.BeanValidator;
import com.mmall.util.LevelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    public void save(DeptParam deptParam) {
        BeanValidator.check(deptParam);

        if (checkExist(deptParam.getParentId(), deptParam.getName(), deptParam.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }

        SysDept dept = new SysDept();
        dept.setParentId(deptParam.getParentId());
        dept.setName(deptParam.getName());
        dept.setRemark(deptParam.getRemark());
        dept.setSeq(deptParam.getSeq());

        dept.setLevel(LevelUtil.calculateLevel(dept.getParentId(), getLevel(deptParam.getParentId())));
        dept.setOperateIp("127.0.0.1");
        dept.setOperator("admin");
        sysDeptMapper.insertSelective(dept);
    }

    private boolean checkExist(Integer parentId, String depName, Integer id) {
        return true;
    }

    private String getLevel(Integer depId) {
        SysDept dept = sysDeptMapper.selectByPrimaryKey(depId);
        if (dept == null) {
            return null;
        } else {
            return dept.getLevel();
        }
    }
}
