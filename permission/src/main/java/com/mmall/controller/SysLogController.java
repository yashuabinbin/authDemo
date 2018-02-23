/*
 * SysLogController.java 1.0.0 2018/2/23  下午 3:37 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/23  下午 3:37 created by lbb
 */
package com.mmall.controller;

import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.JsonData;
import com.mmall.model.SysLogWithBLOBs;
import com.mmall.param.SearchLogParam;
import com.mmall.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 跳转日志页面
     *
     * @return
     */
    @RequestMapping(value = "/log.page")
    public ModelAndView log() {
        return new ModelAndView("log");
    }

    /**
     * 获取日志列表
     *
     * @param pageQuery
     * @param searchLogParam
     * @return
     */
    @RequestMapping(value = "/page.json")
    @ResponseBody
    public JsonData page(SearchLogParam searchLogParam, PageQuery pageQuery) {
        PageResult<SysLogWithBLOBs> pageList = sysLogService.searchPageList(searchLogParam, pageQuery);
        return JsonData.success(pageList);
    }
}
