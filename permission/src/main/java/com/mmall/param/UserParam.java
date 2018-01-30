/*
 * UserParam.java 1.0.0 2018/1/30  上午 10:21 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/1/30  上午 10:21 created by lbb
 */
package com.mmall.param;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class UserParam {

    private Integer id;

    @NotBlank(message = "用户名不能为空")
    @Length(min = 1, max = 20, message = "用户名长度需要在20个字符以内")
    private String username;

    @NotBlank(message = "电话不能为空")
    @Length(min = 1, max = 13, message = "电话长度需要在13个字符以内")
    private String telephone;

    @NotBlank(message = "邮箱不能为空")
    @Length(min = 5, max = 20, message = "邮箱需要在20个字符以内")
    private String mail;

    @NotNull(message = "必须提供用户所在的部门")
    private Integer depId;

    @NotNull(message = "必须指定用户状态")
    @Min(value = 0, message = "用户状态不合法")
    @Max(value = 2, message = "用户状态不合法")
    private Integer status;

    @Length(min = 0, max = 200, message = "备注长度需要控制在200个字符以内")
    private String remark;

}
