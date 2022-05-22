package com.zyy.sport.mapper;

import com.github.pagehelper.Page;
import com.zyy.sport.common.R;
import com.zyy.sport.entity.Permission;
import com.zyy.sport.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {

    void insert(Role role);

    void update(Role role);

    void delete(Integer id);

    Page<Role> findByPage(@Param("query") String query);

    void removeMenusByRoleId(Integer id);

    void removePermissionsByRoleId(Integer id);

    Role findById(Integer id);

    /**
     *  关系表 roles_permissions新增 记录
     */
    void addPermissions(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);

    /**
     *  关系表 roles_menus新增 记录
     */
    void addMenus(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

    List<Role> findAll();
}
