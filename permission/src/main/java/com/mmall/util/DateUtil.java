/*
 * DateUtil.java 1.0.0 2018/2/23  下午 4:53 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/23  下午 4:53 created by lbb
 */
package com.mmall.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String date2Str(Date date, String datePattern) {
        return new SimpleDateFormat(datePattern).format(date);
    }

    public static Date str2Date(String dateStr, String datePattern) throws ParseException {
        return new SimpleDateFormat(datePattern).parse(dateStr);
    }

}
