/*
 * SysDeprService.java 1.0.0 2018/1/26  下午 2:33 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/1/26  下午 2:33 created by lbb
 */
package com.mmall.service;

import com.mmall.common.RequestHolder;
import com.mmall.dao.SysDeptMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysDept;
import com.mmall.param.DeptParam;
import com.mmall.util.BeanValidator;
import com.mmall.util.LevelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        dept.setOperateIp(RequestHolder.getRequestIp());
        dept.setOperator(RequestHolder.getCurrentUserName());
        sysDeptMapper.insertSelective(dept);
    }

    private boolean checkExist(Integer parentId, String depName, Integer id) {
        return sysDeptMapper.countByNameAndParentId(parentId, depName, id) > 0;
    }

    private String getLevel(Integer depId) {
        SysDept dept = sysDeptMapper.selectByPrimaryKey(depId);
        if (dept == null) {
            return null;
        } else {
            return dept.getLevel();
        }
    }

    public void update(DeptParam deptParam) {
        BeanValidator.check(deptParam);

        if (checkExist(deptParam.getParentId(), deptParam.getName(), deptParam.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }

        //获取更新前的部门对象
        SysDept beforeDept = sysDeptMapper.selectByPrimaryKey(deptParam.getId());

        SysDept afterDept = new SysDept();
        afterDept.setId(deptParam.getId());
        afterDept.setParentId(deptParam.getParentId());
        afterDept.setName(deptParam.getName());
        afterDept.setRemark(deptParam.getRemark());
        afterDept.setSeq(deptParam.getSeq());
        afterDept.setOperateIp(RequestHolder.getRequestIp());
        afterDept.setOperator(RequestHolder.getCurrentUserName());
        afterDept.setOperateTime(new Date());
        afterDept.setLevel(LevelUtil.calculateLevel(deptParam.getParentId(), getLevel(deptParam.getParentId())));
        updateWithChild(beforeDept, afterDept);
    }

    private void updateWithChild(SysDept beforeDept, SysDept afterDept) {
        String newLevelPrefix = afterDept.getLevel();
        String oldLevelPrefix = beforeDept.getLevel();

        if (!newLevelPrefix.equals(oldLevelPrefix)) {
            List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(oldLevelPrefix);
            if (CollectionUtils.isNotEmpty(deptList)) {
                for (SysDept dept : deptList) {
                    String level = dept.getLevel();
                    if (level.indexOf(oldLevelPrefix) != 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }

                sysDeptMapper.batchUpdateLevel(deptList);
            }
        }

        sysDeptMapper.updateByPrimaryKeySelective(afterDept);
    }
}
