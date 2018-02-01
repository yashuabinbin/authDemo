package com.mmall.dao;

import com.mmall.beans.PageQuery;
import com.mmall.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser findByKeyword(@Param("keyword") String keyword);

    int countByEmail(@Param("mail") String mail, @Param("id") Integer id);

    int countByTelephone(@Param("telephone") String telephone, @Param("id") Integer id);

    int countByDepId(@Param("depId") Integer depId);

    List<SysUser> selectPageByDepId(@Param("depId") Integer depId, @Param("pageQuery") PageQuery pageQuery);
}