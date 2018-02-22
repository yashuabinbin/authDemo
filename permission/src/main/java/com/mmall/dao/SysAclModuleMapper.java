package com.mmall.dao;

import com.mmall.model.SysAclModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclModuleMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysAclModule record);

    int insertSelective(SysAclModule record);

    SysAclModule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);

    boolean countByNameAndParentId(@Param("name") String name, @Param("parentId") Integer parentId, @Param("id") Integer id);

    List<SysAclModule> selectChildModuleListByLevel(@Param("level") String level);

    void batchUpdateLevel(@Param("moduleList") List<SysAclModule> moduleList);

    List<SysAclModule> selectAllAclModule();

    void deleteSelfAndChildrenByLevel(@Param("level") String level, @Param("id") Integer id);
}