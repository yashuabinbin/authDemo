/*
 * AdminController.java 1.0.0 2018/1/30  下午 2:51 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/1/30  下午 2:51 created by lbb
 */
package com.mmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @RequestMapping(value = "index.page")
    public ModelAndView index() {
        return new ModelAndView("admin");
    }
}
