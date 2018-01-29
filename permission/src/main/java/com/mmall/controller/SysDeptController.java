package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.dto.DeptLevelDto;
import com.mmall.param.DeptParam;
import com.mmall.service.SysDeptService;
import com.mmall.service.SysTreeService;
import com.mmall.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/sys/dept")
@Slf4j
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysTreeService sysTreeService;

    /**
     * 保存部门
     *
     * @param deptParam
     * @return
     */
    @RequestMapping(value = "/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam deptParam) {
        try {
            sysDeptService.save(deptParam);
            return JsonData.success("保存部门成功");
        } catch (Exception e) {
            log.error("save dept error, deptParam:{}", JsonMapper.obj2String(deptParam));
            return JsonData.fail("保存部门出错");
        }
    }

    /**
     * 获取部门树
     *
     * @return
     */
    @RequestMapping(value = "/tree.json")
    @ResponseBody
    public JsonData tree() {
        List<DeptLevelDto> dtoList = sysTreeService.deptTree();
        return JsonData.success(dtoList);
    }

    /**
     * 更新部门
     *
     * @param deptParam
     * @return
     */
    @RequestMapping(value = "/update.json")
    @ResponseBody
    public JsonData update(DeptParam deptParam) {
        try {
            sysDeptService.update(deptParam);
            return JsonData.success("修改部门成功");
        } catch (Exception e) {
            log.error("update dept error, deptParam:{}", JsonMapper.obj2String(deptParam));
            return JsonData.fail("修改部门出错");
        }
    }
}
