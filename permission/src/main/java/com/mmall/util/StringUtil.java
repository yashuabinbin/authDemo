/*
 * StringUtil.java 1.0.0 2018/2/22  下午 2:31 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/22  下午 2:31 created by lbb
 */
package com.mmall.util;

import com.google.common.base.Splitter;

import java.util.List;
import java.util.stream.Collectors;

public class StringUtil {

    /**
     * 字符串(ex: aaa,bbb,,ccc)转List<Integer>
     *
     * @param str
     * @param splitStr
     * @return
     */
    public static List<Integer> split2ListInt(String str, String splitStr) {
        List<String> strList = Splitter.on(splitStr).omitEmptyStrings().splitToList(str);
        List<Integer> intList = strList.stream().map(Integer::parseInt).collect(Collectors.toList());
        return intList;
    }

}
