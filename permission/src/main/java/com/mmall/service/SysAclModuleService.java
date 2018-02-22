/*
 * SysAclModuleService.java 1.0.0 2018/2/1  下午 5:59 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/1  下午 5:59 created by lbb
 */
package com.mmall.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysAclModuleMapper;
import com.mmall.dto.AclModuleLevelDto;
import com.mmall.exception.ParamException;
import com.mmall.model.SysAclModule;
import com.mmall.param.AclModuleParam;
import com.mmall.util.BeanValidator;
import com.mmall.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysAclModuleService {

    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;

    public void save(AclModuleParam aclModuleParam) {
        BeanValidator.check(aclModuleParam);

        if (checkNameExist(aclModuleParam.getName(), aclModuleParam.getParentId(), aclModuleParam.getId())) {
            throw new ParamException("同一层级下存在相同的名称");
        }

        SysAclModule sysAclModule = new SysAclModule();
        sysAclModule.setParentId(aclModuleParam.getParentId());
        sysAclModule.setRemark(aclModuleParam.getRemark());
        sysAclModule.setStatus(aclModuleParam.getStatus());
        sysAclModule.setName(aclModuleParam.getName());
        sysAclModule.setSeq(aclModuleParam.getSeq());
        sysAclModule.setLevel(LevelUtil.calculateLevel(aclModuleParam.getParentId(), getLevel(aclModuleParam.getParentId())));
        sysAclModule.setOperateIp(RequestHolder.getRequestIp());
        sysAclModule.setOperator(RequestHolder.getCurrentUserName());
        sysAclModule.setOperateTime(new Date());

        sysAclModuleMapper.insertSelective(sysAclModule);
    }

    private boolean checkNameExist(String name, Integer parentId, Integer id) {
        return sysAclModuleMapper.countByNameAndParentId(name, parentId, id);
    }

    @Transactional
    public void update(AclModuleParam aclModuleParam) {
        BeanValidator.check(aclModuleParam);

        if (checkNameExist(aclModuleParam.getName(), aclModuleParam.getParentId(), aclModuleParam.getId())) {
            throw new ParamException("同一层级下存在相同的名称");
        }

        SysAclModule beforeModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleParam.getId());

        SysAclModule afterModule = new SysAclModule();
        afterModule.setId(aclModuleParam.getId());
        afterModule.setParentId(aclModuleParam.getParentId());
        afterModule.setSeq(aclModuleParam.getSeq());
        afterModule.setName(aclModuleParam.getName());
        afterModule.setStatus(aclModuleParam.getStatus());
        afterModule.setRemark(aclModuleParam.getRemark());
        afterModule.setLevel(LevelUtil.calculateLevel(aclModuleParam.getParentId(), getLevel(aclModuleParam.getParentId())));
        afterModule.setOperateIp(RequestHolder.getRequestIp());
        afterModule.setOperateTime(new Date());
        afterModule.setOperator(RequestHolder.getCurrentUserName());

        updateWithChild(beforeModule, afterModule);
    }

    private String getLevel(Integer parentId) {
        SysAclModule module = sysAclModuleMapper.selectByPrimaryKey(parentId);
        if (module != null) {
            return module.getLevel();
        } else {
            return null;
        }
    }

    private void updateWithChild(SysAclModule beforeModule, SysAclModule afterModule) {
        String newLevelPrefix = afterModule.getLevel();
        String oldLevelPrefix = beforeModule.getLevel();

        if (!newLevelPrefix.equals(oldLevelPrefix)) {
            List<SysAclModule> moduleList = sysAclModuleMapper.selectChildModuleListByLevel(oldLevelPrefix);
            if (CollectionUtils.isNotEmpty(moduleList)) {
                for (SysAclModule module : moduleList) {
                    String level = module.getLevel();
                    if (level.indexOf(oldLevelPrefix) != -1) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        module.setLevel(level);
                    }
                }

                sysAclModuleMapper.batchUpdateLevel(moduleList);
            }
        }

        sysAclModuleMapper.updateByPrimaryKeySelective(afterModule);
    }

    /**
     * 获取权限树
     *
     * @return
     */
    public List<AclModuleLevelDto> aclModuleTree() {
        //获取所有aclModule
        List<SysAclModule> aclModuleList = sysAclModuleMapper.selectAllAclModule();
        List<AclModuleLevelDto> dtoList = aclModuleList.stream().map(aclModule -> AclModuleLevelDto.adapt(aclModule)).collect(Collectors.toList());
        return aclModuleListToTree(dtoList);
    }

    /**
     * 拼凑权限模块树
     *
     * @param dtoList
     * @return
     */
    private List<AclModuleLevelDto> aclModuleListToTree(List<AclModuleLevelDto> dtoList) {
        Multimap<String, AclModuleLevelDto> levelAclModuleMap = ArrayListMultimap.create();

        if (CollectionUtils.isEmpty(dtoList)) {
            return new ArrayList<>();
        }

        dtoList.forEach(dto -> levelAclModuleMap.put(dto.getLevel(), dto));
        dtoList = dtoList.stream()
                .filter(dto -> dto.getLevel().equals(LevelUtil.ROOT))
                .sorted()
                .collect(Collectors.toList());

        // 转换权限模块树
        transformAclModuleTree(dtoList, LevelUtil.ROOT, levelAclModuleMap);
        return dtoList;
    }

    /**
     * 转换权限模块树
     *
     * @param dtoList
     * @param level
     * @param levelAclModuleMap
     */
    private void transformAclModuleTree(List<AclModuleLevelDto> dtoList, String level, Multimap<String, AclModuleLevelDto> levelAclModuleMap) {
        if (CollectionUtils.isEmpty(dtoList))
            return;

        dtoList.forEach(dto -> {
            String nextLevel = LevelUtil.calculateLevel(dto.getId(), level);
            List<AclModuleLevelDto> tempList = (List<AclModuleLevelDto>) levelAclModuleMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempList)) {
                Collections.sort(tempList);

                dto.setAclModuleList(tempList);
                transformAclModuleTree(tempList, nextLevel, levelAclModuleMap);
            }
        });
    }

    /**
     * 删除权限模块
     *
     * @param moduleId
     */
    public void delete(Integer moduleId) {
        SysAclModule module = sysAclModuleMapper.selectByPrimaryKey(moduleId);
        if (module == null)
            throw new ParamException("该权限模块不存在");

        String level = module.getLevel() + LevelUtil.SEPERATOR + module.getId();
        sysAclModuleMapper.deleteSelfAndChildrenByLevel(level, module.getId());
    }
}
