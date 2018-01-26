package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.param.DeptParam;
import com.mmall.service.SysDeptService;
import com.mmall.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/sys/dept")
@Slf4j
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 保存部门
     *
     * @param deptParam
     * @return
     */
    @RequestMapping(value = "/save.json")
    @ResponseBody
    public JsonData saveDept(@RequestParam("sysDept") DeptParam deptParam) {
        try {
            sysDeptService.save(deptParam);
            return JsonData.success("保存部门成功");
        } catch (Exception e) {
            log.error("save dept error, deptParam:{}", JsonMapper.obj2String(deptParam));
            return JsonData.fail("保存部门出错");
        }
    }
}
