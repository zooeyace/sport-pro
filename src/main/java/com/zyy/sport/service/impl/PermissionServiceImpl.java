package com.zyy.sport.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zyy.sport.common.PageResult;
import com.zyy.sport.common.QueryInfo;
import com.zyy.sport.common.R;
import com.zyy.sport.entity.Permission;
import com.zyy.sport.mapper.PermissionMapper;
import com.zyy.sport.service.PermissionService;
import com.zyy.sport.utils.RedisUtil;
import com.zyy.sport.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public R page(QueryInfo queryInfo) {
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<Permission> page = permissionMapper.findByPage(queryInfo.getQueryParam());
        long total = page.getTotal();
        List<Permission> result = page.getResult();
        return R.okOf(new PageResult<>(total, result), "分页数据查询完成");
    }

    @Override
    public R insert(Permission permission) {
        permissionMapper.insert(permission);
        redisUtil.delKey("userinfo_" + SecurityUtil.getUsername());
        return R.okOf("权限添加成功");
    }

    @Override
    public R delete(Integer id) {
        permissionMapper.delete(id);
        redisUtil.delKey("userinfo_" + SecurityUtil.getUsername());
        return R.okOf("权限删除成功");
    }

    @Override
    public R update(Permission permission) {
        permissionMapper.update(permission);
        redisUtil.delKey("userinfo_" + SecurityUtil.getUsername());
        return R.okOf("权限修改成功");
    }

    @Override
    public R findAll() {
        return R.okOf(permissionMapper.findAll(), "全部权限查询成功");
    }
}
