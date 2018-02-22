package com.mmall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUser {

    // 正常
    public final static int STATUS_USE = 1;

    // 冻结
    public final static int STATUS_FREEZE = 0;

    // 删除
    public final static int STATUS_DELETE = 2;

    private Integer id;

    private String username;

    private String telephone;

    private String mail;

    private String password;

    private Integer deptId;

    private Integer status;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;

}