package com.mmall.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class SysRole {
    private Integer id;

    private String name;

    private Integer type;

    private Integer status;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;

}