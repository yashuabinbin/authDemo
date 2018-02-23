/*
 * SearchLogParam.java 1.0.0 2018/2/23  下午 4:45 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/23  下午 4:45 created by lbb
 */
package com.mmall.param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchLogParam {

    private Integer type; // LogType

    private String beforeSeg;

    private String afterSeg;

    private String operator;

    private String fromTime;//yyyy-MM-dd HH:mm:ss

    private String toTime;
}
