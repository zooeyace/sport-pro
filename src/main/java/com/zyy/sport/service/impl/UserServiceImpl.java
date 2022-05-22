package com.zyy.sport.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zyy.sport.VO.LoginVO;
import com.zyy.sport.common.PageResult;
import com.zyy.sport.common.QueryInfo;
import com.zyy.sport.common.R;
import com.zyy.sport.entity.EasyUser;
import com.zyy.sport.entity.Role;
import com.zyy.sport.mapper.UserMapper;
import com.zyy.sport.service.UserService;
import com.zyy.sport.utils.JwtTokenUtil;
import com.zyy.sport.utils.MD5Util;
import com.zyy.sport.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private JwtTokenUtil tokenUtil;

    @Resource
    private RedisUtil redisUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public R login(LoginVO loginVO) {
        UserDetails userDetails;
        if (loginVO.getType() == null) return R.errorOf("未选择登录类型");
        if (loginVO.getType() == 2) {
            // 验证码登录
            String code = loginVO.getSmsCode();
            // 验证码为空，返回
            if (!StringUtils.isNotBlank(code) || !StringUtils.isNotBlank(loginVO.getPhoneNumber()))
                return R.errorOf("填写信息不完整");
            // 验证码检验
            Object _code = redisUtil.getObject(loginVO.getPhoneNumber() + "_sms_code");
            if (_code == null) return R.errorOf("验证码已过期");
            if (!_code.toString().equals(code)) return R.errorOf("验证码错误");
            // 验证码没问题,开始校验权限信息(用手机号)
            userDetails = userDetailsService.loadUserByUsername(loginVO.getPhoneNumber());
        } else {
            if (!StringUtils.isNotBlank(loginVO.getUsername()) || !StringUtils.isNotBlank(loginVO.getPassword()))
                return R.errorOf("填写信息不完整");
            userDetails = userDetailsService.loadUserByUsername(loginVO.getUsername());
            if (null == userDetails || !passwordEncoder.matches(MD5Util.md5(loginVO.getPassword()), userDetails.getPassword()))
                return R.errorOf("用户名或密码错误");
        }
        if (!userDetails.isEnabled()) return R.errorOf("帐号状态异常，无法登录");

        log.info("----验证通过，尝试往security容器中存放用户信息----");

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 根据信息生成token
        String token = tokenUtil.generateToken(userDetails);
        Map<String, String> map = new HashMap<>();
        map.put("tokenHead", tokenHead);
        map.put("token", token);
        return R.okOf(map);
    }

    @Override
    public EasyUser findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public R page(QueryInfo queryInfo) {
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<EasyUser> page = userMapper.findByPage(queryInfo.getQueryParam());
        long total = page.getTotal();
        List<EasyUser> userList = page.getResult();
        userList.forEach(user -> {
            user.setRoles(userMapper.findRoles(user.getId()));
            user.setPassword(null);
        });
        return R.okOf(new PageResult<>(total, userList), "角色分页查询成功");
    }

    @Override
    @Transactional
    public R insert(EasyUser user) {
        user.setPassword(passwordEncoder.encode(MD5Util.md5(user.getPassword())));
        user.setNickname("用户昵称_" + RandomStringUtils.randomAlphabetic(6));
        userMapper.insert(user);
        // 对于该实体有角色信息的情况，要操作关系表
        List<Role> roles = user.getRoles();
        if (roles.size() > 0) {
            // userId通过mybatis keyProperty回传；roleId先通过前端查出实体集合，再传递过来
            roles.forEach(role -> userMapper.addUserRoles(user.getId(), role.getId()));
        }
        return R.okOf("用户新增成功");
    }

    @Override
    public R delete(Integer id) {
        EasyUser user = userMapper.findById(id);
        if (null == user) return R.errorOf("用户不存在，无法删除");
        userMapper.removeUserRoles(id); // role
        userMapper.delete(id);
        return R.okOf("用户删除成功");
    }

    @Override
    @Transactional
    public R update(EasyUser user) {
        // 旧有角色删除
        userMapper.removeUserRoles(user.getId());
        // 重新添加角色
        List<Role> roles = user.getRoles();
        if (roles.size() > 0) {
            roles.forEach(role -> userMapper.addUserRoles(user.getId(), role.getId()));
        }
        // 更新
        String pwd = user.getPassword();
        if (StringUtils.isNotEmpty(pwd)) {
            user.setPassword(passwordEncoder.encode(MD5Util.md5(pwd)));
        }
        userMapper.update(user);
        return R.okOf("用户信息修改成功");
    }

    @Override
    public void updatePwdByMail(String receiver, String newPassword) {
        userMapper.updatePassword(receiver, newPassword);
    }
}
