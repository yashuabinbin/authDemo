/*
 * PasswordUtil.java 1.0.0 2018/1/30  上午 11:39 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/1/30  上午 11:39 created by lbb
 */
package com.mmall.util;

import java.util.Date;
import java.util.Random;

public class PasswordUtil {

    private static final String[] word = {
            "a", "b", "c", "d", "e",
            "f", "g", "h", "j", "k",
            "m", "n", "o", "p", "q",
            "r", "s", "t", "u", "v",
            "w", "x", "y", "z",
            "A", "B", "C", "D", "E",
            "F", "G", "H", "J", "K",
            "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V",
            "w", "x", "Y", "Z"
    };

    private static final String[] num = {"2", "3", "4", "5", "6", "7", "8", "9"};

    /**
     * 生成随机密码
     *
     * @return
     */
    public static String generatePassword() {
        StringBuffer sb = new StringBuffer();
        Random random = new Random(new Date().getTime());

        int length = 8 + random.nextInt(3);
        for (int i = 0; i < length; i++) {
            if (i % 2 == 0) {
                sb.append(word[random.nextInt(word.length)]);
            } else {
                sb.append(num[random.nextInt(num.length)]);
            }
        }

        return sb.toString();
    }
}
