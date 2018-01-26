/*
 * LevelUtil.java 1.0.0 2018/1/26  下午 3:19 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/1/26  下午 3:19 created by lbb
 */
package com.mmall.util;

import org.apache.commons.lang3.StringUtils;

public class LevelUtil {

    public static final String SEPERATOR = ".";

    public static final String ROOT = "0";

    public static String calculateLevel(Integer parentId, String parentLevel) {
        if (StringUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return StringUtils.join(parentLevel, SEPERATOR, parentId);
        }
    }
}
