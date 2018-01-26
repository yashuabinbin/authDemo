package com.mmall.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class SysLog {
    private Integer id;

    private Integer type;

    private Integer targetId;

    private String operator;

    private Date operateTime;

    private String operateIp;

    private Integer status;

}