package com.zyy.sport.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zyy.sport.common.PageResult;
import com.zyy.sport.common.QueryInfo;
import com.zyy.sport.common.R;
import com.zyy.sport.entity.Menu;
import com.zyy.sport.mapper.MenuMapper;
import com.zyy.sport.service.MenuService;
import com.zyy.sport.utils.RedisUtil;
import com.zyy.sport.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public R page(QueryInfo queryInfo) {
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<Menu> page = menuMapper.findByPage(queryInfo.getQueryParam());
        long total = page.getTotal();
        List<Menu> result = page.getResult();
        return R.okOf(new PageResult<>(total, result), "分页查询成功");
    }

    @Override
    public R findParent() {
        return R.okOf(menuMapper.findParent(), "父级菜单查询成功");
    }

    @Override
    public R insert(Menu menu) {
        menuMapper.insert(menu);
        redisUtil.delKey("userinfo_" + SecurityUtil.getUsername());
        return R.okOf("menu insert ok");
    }

    @Override
    public R delete(Integer id) {
        menuMapper.delete(id);
        redisUtil.delKey("userinfo_" + SecurityUtil.getUsername());
        return R.okOf("menu del ok");
    }

    @Override
    public R update(Menu menu) {
        menuMapper.update(menu);
        redisUtil.delKey("userinfo_" + SecurityUtil.getUsername());
        return R.okOf("menu update ok");
    }
}
