/*
 * SysLogService.java 1.0.0 2018/2/23  下午 3:38
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/2/23  下午 3:38 created by lbb
 */
package com.mmall.service;

import com.mmall.beans.LogType;
import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.RequestHolder;
import com.mmall.dao.SysLogMapper;
import com.mmall.dto.SearchLogDto;
import com.mmall.exception.ParamException;
import com.mmall.model.*;
import com.mmall.param.SearchLogParam;
import com.mmall.util.BeanValidator;
import com.mmall.util.DateUtil;
import com.mmall.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    public void saveDeptLog(SysDept before, SysDept after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_DEPT);
        sysLog.setOperateIp(RequestHolder.getRequestIp());
        sysLog.setOperateTime(new Date());
        sysLog.setOperator(RequestHolder.getCurrentUserName());
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setStatus(1);
        sysLogMapper.insert(sysLog);
    }

    public void saveUserLog(SysUser before, SysUser after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_USER);
        sysLog.setOperateIp(RequestHolder.getRequestIp());
        sysLog.setOperateTime(new Date());
        sysLog.setOperator(RequestHolder.getCurrentUserName());
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setStatus(1);
        sysLogMapper.insert(sysLog);
    }

    public void saveRoleLog(SysRole before, SysRole after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE);
        sysLog.setOperateIp(RequestHolder.getRequestIp());
        sysLog.setOperateTime(new Date());
        sysLog.setOperator(RequestHolder.getCurrentUserName());
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setStatus(1);
        sysLogMapper.insert(sysLog);
    }

    public void saveAclModuleLog(SysAclModule before, SysAclModule after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ACL_MODULE);
        sysLog.setOperateIp(RequestHolder.getRequestIp());
        sysLog.setOperateTime(new Date());
        sysLog.setOperator(RequestHolder.getCurrentUserName());
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setStatus(1);
        sysLogMapper.insert(sysLog);
    }

    public void saveAclLog(SysAcl before, SysAcl after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ACL);
        sysLog.setOperateIp(RequestHolder.getRequestIp());
        sysLog.setOperateTime(new Date());
        sysLog.setOperator(RequestHolder.getCurrentUserName());
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setStatus(1);
        sysLogMapper.insert(sysLog);
    }

    public void saveRoleAclLog(int roleId, List<Integer> before, List<Integer> after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE_ACL);
        sysLog.setOperateIp(RequestHolder.getRequestIp());
        sysLog.setOperateTime(new Date());
        sysLog.setOperator(RequestHolder.getCurrentUserName());
        sysLog.setTargetId(roleId);
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setStatus(1);
        sysLogMapper.insert(sysLog);
    }

    public void saveRoleUserLog(int roleId, List<Integer> before, List<Integer> after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE_USER);
        sysLog.setOperateIp(RequestHolder.getRequestIp());
        sysLog.setOperateTime(new Date());
        sysLog.setOperator(RequestHolder.getCurrentUserName());
        sysLog.setTargetId(roleId);
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setStatus(1);
        sysLogMapper.insert(sysLog);
    }

    public PageResult<SysLogWithBLOBs> searchPageList(SearchLogParam searchLogParam, PageQuery pageQuery) {
        BeanValidator.check(searchLogParam);

        SearchLogDto dto = new SearchLogDto();

        dto.setAfterSeg(searchLogParam.getAfterSeg());
        dto.setBeforeSeg(searchLogParam.getBeforeSeg());
        dto.setOperator(searchLogParam.getOperator());
        dto.setType(searchLogParam.getType());
        String datePattern = "yyyy-MM-dd HH:mm:ss";
        try {
            if (StringUtils.isNotBlank(searchLogParam.getFromTime())) {
                dto.setFromTime(DateUtil.str2Date(searchLogParam.getFromTime(), datePattern));
            }

            if (StringUtils.isNotBlank(searchLogParam.getToTime())) {
                dto.setToTime(DateUtil.str2Date(searchLogParam.getToTime(), datePattern));
            }
        } catch (ParseException e) {
            throw new ParamException("传入的日期格式有问题，正确的格式应为：" + datePattern);
        }

        int count = sysLogMapper.countBySearchDto(dto, pageQuery);
        if (count > 0) {
            List<SysLogWithBLOBs> pageList = sysLogMapper.selectPageListBySearchDto(dto, pageQuery);
            PageResult<SysLogWithBLOBs> pageResult = new PageResult<>();
            pageResult.setData(pageList);
            pageResult.setTotal(count);
            return pageResult;
        }

        return null;
    }
}
