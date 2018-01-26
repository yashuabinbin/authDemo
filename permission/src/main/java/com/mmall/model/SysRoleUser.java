package com.mmall.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class SysRoleUser {
    private Integer id;

    private Integer roleId;

    private Integer userId;

    private String operator;

    private Date operateTime;

    private String operateIp;
    
}