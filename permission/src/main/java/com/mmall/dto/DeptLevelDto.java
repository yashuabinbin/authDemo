/*
 * DeptLevelDto.java 1.0.0 2018/1/26  下午 4:59 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/1/26  下午 4:59 created by lbb
 */
package com.mmall.dto;

import com.google.common.collect.Lists;
import com.mmall.model.SysDept;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class DeptLevelDto extends SysDept implements Comparable<DeptLevelDto> {

    private List<DeptLevelDto> depList = Lists.newArrayList();

    public static DeptLevelDto adapt(SysDept sysDept) {
        DeptLevelDto deptLevelDto = new DeptLevelDto();
        BeanUtils.copyProperties(sysDept, deptLevelDto);
        return deptLevelDto;
    }

    @Override
    public int compareTo(DeptLevelDto o2) {
        return this.getSeq() - o2.getSeq();
    }
}
