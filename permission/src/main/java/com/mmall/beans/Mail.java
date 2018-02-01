/*
 * Mail.java 1.0.0 2018/2/1  下午 5:06 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/1  下午 5:06 created by lbb
 */
package com.mmall.beans;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

    private String subject;

    private String message;

    private Set<String> receivers;

}
