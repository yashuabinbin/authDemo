/*
 * SysLogController.java 1.0.0 2018/2/23  下午 3:37 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/23  下午 3:37 created by lbb
 */
package com.mmall.controller;

import com.mmall.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

}
