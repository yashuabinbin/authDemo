package com.mmall.dao;

import com.mmall.model.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDeptMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);

    List<SysDept> selectAllDept();

    int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("depName") String depName, @Param("depId") Integer id);

    List<SysDept> getChildDeptListByLevel(@Param("level") String level);

    void batchUpdateLevel(@Param("deptList") List<SysDept> deptList);

    int countByParentId(@Param("deptId") Integer deptId);
}