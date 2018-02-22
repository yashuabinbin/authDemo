/*
 * SysModuleDto.java 1.0.0 2018/2/2  上午 10:15 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/2  上午 10:15 created by lbb
 */
package com.mmall.dto;

import com.google.common.collect.Lists;
import com.mmall.model.SysAclModule;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
@ToString
public class AclModuleLevelDto extends SysAclModule implements Comparable<SysAclModule> {

    // 下一层的AclModule列表
    private List<AclModuleLevelDto> aclModuleList = Lists.newArrayList();

    // 该AclModule模块下的acl具体的权限列表
    private List<AclDto> aclList = Lists.newArrayList();

    public static AclModuleLevelDto adapt(SysAclModule module) {
        AclModuleLevelDto dto = new AclModuleLevelDto();
        BeanUtils.copyProperties(module, dto);
        return dto;
    }

    @Override
    public int compareTo(SysAclModule o2) {
        return this.getSeq() - o2.getSeq();
    }
}
