/*
 * PageQuery.java 1.0.0 2018/2/1  上午 10:55 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/1  上午 10:55 created by lbb
 */
package com.mmall.beans;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

public class PageQuery {

    @Setter
    @Getter
    @Min(value = 1, message = "当前页码不合法")
    private int pageNo = 1;

    @Setter
    @Getter
    @Min(value = 1, message = "每页展示数量不合法")
    private int pageSize = 10;

    @Setter
    private int offSet;

    public int getOffSet() {
        return (pageNo - 1) * pageSize;
    }

}
