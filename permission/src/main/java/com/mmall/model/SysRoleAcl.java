package com.mmall.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class SysRoleAcl {
    private Integer id;

    private Integer roleId;

    private Integer aclId;

    private String operator;

    private Date operateTime;

    private String operateIp;
}