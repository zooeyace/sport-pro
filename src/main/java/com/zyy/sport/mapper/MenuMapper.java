package com.zyy.sport.mapper;

import com.github.pagehelper.Page;
import com.zyy.sport.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {

    void insert(Menu menu);

    void update(Menu menu);

    void delete(Integer id);

    Page<Menu> findByPage(@Param("query") String query);

    List<Menu> findParent();

    List<Menu> findMenusByRoleId(Integer id);

    List<Menu> findSubMenusByRoleId(@Param("roleId") Integer roleId, @Param("parentId") Integer parentId);
}
