package com.zyy.sport.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zyy.sport.common.PageResult;
import com.zyy.sport.common.QueryInfo;
import com.zyy.sport.common.R;
import com.zyy.sport.entity.Menu;
import com.zyy.sport.entity.Permission;
import com.zyy.sport.entity.Role;
import com.zyy.sport.mapper.MenuMapper;
import com.zyy.sport.mapper.PermissionMapper;
import com.zyy.sport.mapper.RoleMapper;
import com.zyy.sport.service.RoleService;
import com.zyy.sport.utils.RedisUtil;
import com.zyy.sport.utils.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public R page(QueryInfo queryInfo) {
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<Role> page = roleMapper.findByPage(queryInfo.getQueryParam());
        long total = page.getTotal();
        List<Role> result = page.getResult();
        result.forEach(ele -> {
            // role对应的menu和permission
            List<Menu> menus = menuMapper.findMenusByRoleId(ele.getId());
            menus.forEach(e -> {
                List<Menu> subMenus = menuMapper.findSubMenusByRoleId(ele.getId(), e.getId());
                e.setChildren(subMenus);
            });
            ele.setMenus(menus);

            List<Permission> permissions = permissionMapper.findPermissionByRoleId(ele.getId());
            ele.setPermissions(permissions);
        });
        return R.okOf(new PageResult<>(total, result), "分页查询成功");
    }

    /**
     *  新增角色
     *      同时操作与角色关联的 "permission" 和 "menu" (关系表)
     *      在这里 roleMapper.insert(role); 在xml设置了可以拿到主键id
     */
    @Override
    @Transactional
    public R insert(Role role) {
        roleMapper.insert(role); // mybatis设置了insert方法有返回id
        if (role.getPermissions().size() > 0) {
            role.getPermissions().forEach(e -> roleMapper.addPermissions(role.getId(), e.getId()));
        }
        if (role.getMenus().size() > 0) {
            role.getMenus().forEach(e -> roleMapper.addMenus(role.getId(), e.getId()));
        }
        redisUtil.delKey("userinfo_" + SecurityUtil.getUsername());
        return R.okOf("角色信息添加成功");
    }

    @Override
    public R delete(Integer id) {
        // 先判断当前的角色id下是否有权限信息和菜单信息
        List<Menu> menus = menuMapper.findMenusByRoleId(id);
        List<Menu> children = new ArrayList<>();
        // 子菜单
        menus.forEach(item -> {
            children.addAll(menuMapper.findSubMenusByRoleId(id, item.getId()));
        });
        if (menus.size() > 0 || children.size() > 0) {
            return R.errorOf("删除失败，该角色尚有关联菜单信息");
        }
        if (permissionMapper.findPermissionByRoleId(id).size() > 0) {
            return R.errorOf("删除失败，该角色尚有关联权限信息");
        }
        redisUtil.delKey("userinfo_" + SecurityUtil.getUsername());
        return R.okOf("删除成功");
    }

    @Override
    @Transactional
    public R update(Role role) {
        roleMapper.update(role);
        if (role.getPermissions().size() > 0) {
            roleMapper.removePermissionsByRoleId(role.getId()); // 先删除 再新增
            role.getPermissions().forEach(e -> roleMapper.addPermissions(role.getId(), e.getId()));
        }
        if (role.getMenus().size() > 0) {
            roleMapper.removeMenusByRoleId(role.getId());
            role.getMenus().forEach(e -> roleMapper.addMenus(role.getId(), e.getId()));
        }
        redisUtil.delKey("userinfo_" + SecurityUtil.getUsername());
        return R.okOf("角色信息修改成功");
    }

    @Override
    public R all() {
        return R.okOf(roleMapper.findAll(), "角色查询成功");
    }
}
