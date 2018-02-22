/*
 * SysTreeService.java 1.0.0 2018/1/26  下午 5:11 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/1/26  下午 5:11 created by lbb
 */
package com.mmall.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.mmall.dao.SysAclMapper;
import com.mmall.dao.SysAclModuleMapper;
import com.mmall.dao.SysDeptMapper;
import com.mmall.dto.AclDto;
import com.mmall.dto.AclModuleLevelDto;
import com.mmall.dto.DeptLevelDto;
import com.mmall.model.SysAcl;
import com.mmall.model.SysAclModule;
import com.mmall.model.SysDept;
import com.mmall.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysTreeService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;

    @Autowired
    private SysCoreService sysCoreService;

    @Autowired
    private SysAclMapper sysAclMapper;

    public List<DeptLevelDto> deptTree() {
        List<SysDept> depList = sysDeptMapper.selectAllDept();
        List<DeptLevelDto> dtoList = depList.stream()
                .map(sysDept -> DeptLevelDto.adapt(sysDept)).collect(Collectors.toList());
        return depListToTree(dtoList);
    }

    private List<DeptLevelDto> depListToTree(List<DeptLevelDto> dtoList) {
        if (dtoList.isEmpty()) {
            return Lists.newArrayList();
        }

        Multimap<String, DeptLevelDto> levelDeptMap = ArrayListMultimap.create();
        dtoList.forEach(dto -> levelDeptMap.put(dto.getLevel(), dto));

        List<DeptLevelDto> rootList = dtoList.stream()
                .filter(dto -> LevelUtil.ROOT.equals(dto.getLevel()))
                .sorted(DeptLevelDto::compareTo)
                .collect(Collectors.toList());

        transformDeptTree(rootList, LevelUtil.ROOT, levelDeptMap);
        return rootList;
    }

    //递归部门树
    private void transformDeptTree(List<DeptLevelDto> dtoList, String level, Multimap<String, DeptLevelDto> levelDeptMap) {
        dtoList.forEach(dto -> {
            String nextLev = LevelUtil.calculateLevel(dto.getId(), level);

            List<DeptLevelDto> nextLevelDto = (List<DeptLevelDto>) levelDeptMap.get(nextLev);
            if (CollectionUtils.isNotEmpty(nextLevelDto)) {
                //排序
                nextLevelDto.sort(DeptLevelDto::compareTo);
                //设置下一层部门
                dto.setDeptList(nextLevelDto);
                //进入下一层处理
                transformDeptTree(nextLevelDto, nextLev, levelDeptMap);
            }
        });
    }

    public List<AclModuleLevelDto> roleTree(Integer roleId) {
        // 1、当前用户已分配的权限点
        List<SysAcl> userAclList = sysCoreService.getCurrentUserAclList();

        // 2、当前角色分配的权限点
        List<SysAcl> roleAclList = sysCoreService.getRoleAclList(roleId);

        Set<Integer> userAclIdList = userAclList.stream()
                .map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());

        Set<Integer> roleAclIdList = roleAclList.stream()
                .map(sysAcl -> sysAcl.getId()).collect(Collectors.toSet());

        //获取所有acl权限
        List<SysAcl> allAclList = sysAclMapper.selectAll();
        Set<SysAcl> aclSet = new HashSet<>(allAclList);

        List<AclDto> aclDtoList = Lists.newArrayList();
        for (SysAcl sysAcl : aclSet) {
            AclDto aclDto = AclDto.adapt(sysAcl);
            if (userAclIdList.contains(aclDto.getId())) {
                aclDto.setHasAcl(true);
            }
            if (roleAclIdList.contains(aclDto.getId())) {
                aclDto.setChecked(true);
            }
            aclDtoList.add(aclDto);
        }

        return aclListToTree(aclDtoList);
    }

    public List<AclModuleLevelDto> aclListToTree(List<AclDto> aclDtoList) {
        if (CollectionUtils.isEmpty(aclDtoList)) {
            return Lists.newArrayList();
        }

        List<AclModuleLevelDto> aclModuleLevelDtoList = aclModuleTree();

        Multimap<Integer, AclDto> moduleIdAclMap = ArrayListMultimap.create();
        for (SysAcl acl : aclDtoList) {
            if (acl.getStatus() == SysAcl.STATUS_USE) {
                moduleIdAclMap.put(acl.getAclModuleId(), AclDto.adapt(acl));
            }
        }

        bindAclsWithOrder(aclModuleLevelDtoList, moduleIdAclMap);
        return aclModuleLevelDtoList;
    }

    public void bindAclsWithOrder(List<AclModuleLevelDto> aclModuleLevelDtoList, Multimap<Integer, AclDto> moduleIdAclMap) {
        if (CollectionUtils.isEmpty(aclModuleLevelDtoList))
            return;

        for (AclModuleLevelDto dto : aclModuleLevelDtoList) {
            List<AclDto> dtoList = (List<AclDto>) moduleIdAclMap.get(dto.getId());
            if (CollectionUtils.isNotEmpty(dtoList)) {
                Collections.sort(dtoList);
                dto.setAclDtoList(dtoList);
                bindAclsWithOrder(dto.getAclModuleList(), moduleIdAclMap);
            }
        }
    }

    public List<AclModuleLevelDto> aclModuleTree() {
        List<SysAclModule> aclModuleList = sysAclModuleMapper.selectAllAclModule();
        List<AclModuleLevelDto> dtoList = Lists.newArrayList();
        for (SysAclModule aclModule : aclModuleList) {
            dtoList.add(AclModuleLevelDto.adapt(aclModule));
        }
        return aclModuleListToTree(dtoList);
    }

    public List<AclModuleLevelDto> aclModuleListToTree(List<AclModuleLevelDto> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Lists.newArrayList();
        }
        // level -> [aclmodule1, aclmodule2, ...] Map<String, List<Object>>
        Multimap<String, AclModuleLevelDto> levelAclModuleMap = ArrayListMultimap.create();
        List<AclModuleLevelDto> rootList = Lists.newArrayList();

        for (AclModuleLevelDto dto : dtoList) {
            levelAclModuleMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        Collections.sort(rootList);
        transformAclModuleTree(rootList, LevelUtil.ROOT, levelAclModuleMap);
        return rootList;
    }

    public void transformAclModuleTree(List<AclModuleLevelDto> dtoList, String level, Multimap<String, AclModuleLevelDto> levelAclModuleMap) {
        for (int i = 0; i < dtoList.size(); i++) {
            AclModuleLevelDto dto = dtoList.get(i);
            String nextLevel = LevelUtil.calculateLevel(dto.getId(), level);
            List<AclModuleLevelDto> tempList = (List<AclModuleLevelDto>) levelAclModuleMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempList)) {
                Collections.sort(tempList);
                dto.setAclModuleList(tempList);
                transformAclModuleTree(tempList, nextLevel, levelAclModuleMap);
            }
        }
    }
}
