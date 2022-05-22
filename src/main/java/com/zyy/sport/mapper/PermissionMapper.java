package com.zyy.sport.mapper;

import com.github.pagehelper.Page;
import com.zyy.sport.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionMapper {

    void insert(Permission permission);

    void update(Permission permission);

    void delete(Integer id);

    Page<Permission> findByPage(@Param("query") String query);

    List<Permission> findPermissionByRoleId(Integer id);

    List<Permission> findAll();
}
