package com.mmall.dao;

import com.mmall.beans.PageQuery;
import com.mmall.model.SysAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAcl record);

    int insertSelective(SysAcl record);

    SysAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAcl record);

    int updateByPrimaryKey(SysAcl record);

    int countByAclModuleId(@Param("aclModuleId") Integer aclModuleId);

    List<SysAcl> selectPageByAclModuleId(@Param("aclModuleId") Integer aclModuleId, @Param("pageQuery") PageQuery pageQuery);

    int countByNameAndAclModuleId(@Param("aclModuleId") int aclModuleId, @Param("name") String name, @Param("id") Integer id);

    List<SysAcl> selectAll();

    List<SysAcl> selectByIdList(@Param("idList") List<Integer> idList);

    List<SysAcl> selectByUrl(@Param("url") String url);
}