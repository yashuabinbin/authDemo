package com.mmall.dao;

import com.mmall.model.SysRoleUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleUserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleUser record);

    int insertSelective(SysRoleUser record);

    SysRoleUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleUser record);

    int updateByPrimaryKey(SysRoleUser record);

    List<Integer> selectRoleIdListByUserId(@Param("userId") Integer userId);

    List<Integer> selectUserIdListByRoleId(@Param("roleId") Integer roleId);

    int deleteByRoleId(@Param("roleId") Integer roleId);

    int insertBatch(@Param("roleUserList") List<SysRoleUser> roleUserList);

    List<Integer> selectUserIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);
}