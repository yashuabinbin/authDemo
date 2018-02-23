/*
 * AclControlFilter.java 1.0.0 2018/2/23  上午 10:00 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/23  上午 10:00 created by lbb
 */
package com.mmall.filter;

import com.google.common.base.Splitter;
import com.mmall.common.ApplicationContextHelper;
import com.mmall.common.JsonData;
import com.mmall.common.RequestHolder;
import com.mmall.model.SysUser;
import com.mmall.service.SysCoreService;
import com.mmall.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class AclControlFilter implements Filter {

    // 不进行过滤的url集合
    private static Set<String> exclusionUrlSet = new HashSet<>();

    // 无权限页面url
    private static final String NO_AUTH_URL = "/sys/user/noAuth.page";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String exclusionUrlStr = filterConfig.getInitParameter("exclusionUrls");
        List<String> exclusionUrlList = Splitter.on(",").omitEmptyStrings().splitToList(exclusionUrlStr);
        exclusionUrlSet = new HashSet<>(exclusionUrlList);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String visitPath = request.getServletPath();
        if (exclusionUrlSet.contains(visitPath)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        SysUser sysUser = RequestHolder.getCurrentUser();
        if (sysUser == null) {
            log.info("someone want to visit {}, but not logined, parameter:{}", visitPath, JsonMapper.obj2String(request.getParameterMap()));
            noAuth(request, response);
            return;
        }

        SysCoreService sysCoreService = ApplicationContextHelper.popBean(SysCoreService.class);
        if (!sysCoreService.hasUrlAcl(visitPath)) {
            log.info("someone want to visit {}, but not logined, parameter:{}", visitPath, JsonMapper.obj2String(request.getParameterMap()));
            noAuth(request, response);
            return;
        }

        System.out.println("acl filter dodo");

        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }

    /**
     * 进行无权限处理
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void noAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String visitPath = request.getServletPath();

        if (visitPath.endsWith(".json")) {
            JsonData jsonData = JsonData.fail("没有访问权限，如需要访问，请联系管理员");
            response.setHeader("Content-Type", "application/json");
            response.getWriter().print(JsonMapper.obj2String(jsonData));
        } else {
            clientRedirect(NO_AUTH_URL, response);
        }

        return;
    }

    private void clientRedirect(String url, HttpServletResponse response) throws IOException {
        response.setHeader("Content-Type", "text/html");
        response.getWriter()
                .print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                        + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n" +
                        "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"/>\n"
                        + "<title>跳转中...</title>\n" + "</head>\n" + "<body>\n" + "跳转中，请稍候...\n" + "<script type=\"text/javascript\">//<![CDATA[\n"
                        + "window.location.href='" + url + "?ret='+encodeURIComponent(window.location.href);\n" + "//]]></script>\n" + "</body>\n" +
                        "</html>\n");
    }

    @Override
    public void destroy() {

    }
}
