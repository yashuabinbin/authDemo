package com.mmall.dao;

import com.mmall.model.SysRoleAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleAclMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleAcl record);

    int insertSelective(SysRoleAcl record);

    SysRoleAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleAcl record);

    int updateByPrimaryKey(SysRoleAcl record);

    List<Integer> selectAclIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);

    int deleteByRoleId(@Param("roleId") Integer roleId);

    int insertBatch(@Param("roleAclList") List<SysRoleAcl> roleAclList);

    List<Integer> selectRoleIdListByAclId(@Param("aclId") Integer aclId);

}