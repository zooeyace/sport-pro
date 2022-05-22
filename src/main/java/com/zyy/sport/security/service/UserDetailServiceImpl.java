package com.zyy.sport.security.service;

import com.zyy.sport.entity.EasyUser;
import com.zyy.sport.entity.Menu;
import com.zyy.sport.mapper.UserMapper;
import com.zyy.sport.utils.RedisUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 重写 UserDetailService
 */

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EasyUser user;
        String redisKey = "userinfo_" + username;

        if (redisUtil.hasKey(redisKey)) {
            user = (EasyUser) redisUtil.getObject(redisKey);
            redisUtil.expire(redisKey, 5);
        } else {
            user = userMapper.findByUsername(username);
            if (user == null) throw new UsernameNotFoundException("用户名或密码错误");
            if (user.isAdmin()) {
                // 是管理员
                // SQL语句根据传入的用户id是否为空判断
                user.setRoles(userMapper.findRoles(null));
                user.setPermissions(userMapper.findPermissions(null));
                List<Menu> menus = userMapper.findMenus(null);
                menus.forEach(item -> item.setChildren(userMapper.findSubMenu(item.getId(), null)));
                user.setMenus(menus);
            } else {
                // 不是管理员
                user.setRoles(userMapper.findRoles(user.getId()));
                user.setPermissions(userMapper.findPermissions(user.getId()));
                List<Menu> menus = userMapper.findMenus(user.getId());
                menus.forEach(item -> item.setChildren(userMapper.findSubMenu(item.getId(), user.getId())));
                user.setMenus(menus);
            }
            redisUtil.setObjectWithTime(redisKey, user, 5);
        }
        return user;
    }
}
