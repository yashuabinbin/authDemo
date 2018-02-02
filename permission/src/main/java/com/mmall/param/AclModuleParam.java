/*
 * SysAclParam.java 1.0.0 2018/2/1  下午 5:26 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/1  下午 5:26 created by lbb
 */
package com.mmall.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
public class AclModuleParam {

    private Integer id;

    @NotBlank(message = "权限模块名称不能为空")
    @Length(min = 2, max = 64, message = "权限模块名称长度控制在2-64字符内")
    private String name;

    private Integer parentId;

    @NotNull(message = "权限模块展示顺序不能为空")
    private Integer seq;

    @NotNull(message = "权限模块状态不能为空")
    private Integer status;

    @Length(max = 64, message = "权限模块备注不可超过64字符")
    private String remark;

}
