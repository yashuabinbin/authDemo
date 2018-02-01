/*
 * PageResult.java 1.0.0 2018/2/1  上午 11:02 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/1  上午 11:02 created by lbb
 */
package com.mmall.beans;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PageResult<T> {

    private List<T> data = Lists.newArrayList();

    private int total = 0;
}
