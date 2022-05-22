package com.zyy.sport.service;

import com.zyy.sport.common.QueryInfo;
import com.zyy.sport.common.R;
import com.zyy.sport.entity.Permission;

public interface PermissionService {

    /**
     *  分页查询
     */
    R page(QueryInfo queryInfo);

    R insert(Permission permission);


    R delete(Integer id);


    R update(Permission permission);

    R findAll();
}
