/*
 * AclDto.java 1.0.0 2018/2/13  下午 3:01 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/13  下午 3:01 created by lbb
 */
package com.mmall.dto;

import com.mmall.model.SysAcl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@Setter
@Getter
@ToString
public class AclDto extends SysAcl implements Comparable<AclDto> {

    //是否默认勾选
    private boolean checked = false;

    //是否有权限查看
    private boolean hasAcl = false;

    public static AclDto adapt(SysAcl acl) {
        AclDto dto = new AclDto();
        BeanUtils.copyProperties(acl, dto);
        return dto;
    }

    @Override
    public int compareTo(AclDto o) {
        return this.getSeq() - o.getSeq();
    }
}
