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
import com.mmall.dao.SysDeptMapper;
import com.mmall.dto.DeptLevelDto;
import com.mmall.model.SysDept;
import com.mmall.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysTreeService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    public List<DeptLevelDto> deptTree() {
        List<SysDept> depList = sysDeptMapper.selectAllDept();
        List<DeptLevelDto> dtoList = depList.stream().map(sysDept -> DeptLevelDto.adapt(sysDept)).collect(Collectors.toList());
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
}
