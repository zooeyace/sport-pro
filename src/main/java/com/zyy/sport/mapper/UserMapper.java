package com.zyy.sport.mapper;

import com.github.pagehelper.Page;
import com.zyy.sport.entity.EasyUser;
import com.zyy.sport.entity.Menu;
import com.zyy.sport.entity.Permission;
import com.zyy.sport.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     *  security登录时的验证
     */
    EasyUser findByUsername(@Param("value") String value);

    List<Role> findRoles(@Param("userid") Integer id);

    List<Menu> findMenus(@Param("userid") Integer id);

    List<Menu> findSubMenu(@Param("parentId") int parentId, @Param("userid") Integer userid);

    List<Permission> findPermissions(@Param("userid") Integer id);

    Page<EasyUser> findByPage(@Param("query") String query);

    void insert(EasyUser user);

    void update(EasyUser user);

    void delete(Integer id);

    void addUserRoles(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    void removeUserRoles(@Param("userId") Integer userId);

    EasyUser findById(Integer id);

    void updatePassword(@Param("email") String receiver, @Param("password") String newPassword);
}
