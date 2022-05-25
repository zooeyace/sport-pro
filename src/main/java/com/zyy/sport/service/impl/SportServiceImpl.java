package com.zyy.sport.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zyy.sport.common.PageResult;
import com.zyy.sport.common.QueryInfo;
import com.zyy.sport.common.R;
import com.zyy.sport.entity.Sport;
import com.zyy.sport.mapper.SportMapper;
import com.zyy.sport.service.SportService;
import com.zyy.sport.utils.DateUtil;
import com.zyy.sport.utils.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class SportServiceImpl implements SportService {

    @Resource
    private SportMapper sportMapper;


    @Override
    public R delete(Long id) {
        if (null != id) {
            sportMapper.delete(id);
            return R.okOf("删除成功");
        }
        return R.errorOf("请传入id");
    }

    @Override
    public R findInfo(Long id) {
        return null;
    }

    @Override
    @Transactional
    public R update(Sport sport) {
        if (null != sport) {
            sport.setUpdateTime(DateUtil.getDateWithTime());
            sport.setUpdateName(SecurityUtil.getUsername());
            sportMapper.update(sport);
            return R.okOf("修改成功");
        }
        return R.errorOf("请选择修改对象");
    }

    @Override
    @Transactional
    public R insert(Sport sport) {
        if (null != sport) {
            sport.setCreateTime(DateUtil.getDateWithTime());
            sport.setCreateName(SecurityUtil.getUsername());
            sportMapper.insert(sport);
            return R.okOf("添加成功");
        }
        return R.errorOf("不可为空");
    }

    @Override
    public R findPage(QueryInfo queryInfo) {
        PageHelper.startPage(queryInfo.getPageNumber(), queryInfo.getPageSize());
        Page<Sport> page = sportMapper.findPage(queryInfo.getQueryParam());
        return R.okOf(new PageResult<>(page.getTotal(), page.getResult()), "分页查询成功");
    }
}
