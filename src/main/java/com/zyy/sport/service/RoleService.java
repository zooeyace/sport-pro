package com.zyy.sport.service;

import com.zyy.sport.common.QueryInfo;
import com.zyy.sport.common.R;
import com.zyy.sport.entity.Role;

public interface RoleService {

    R page(QueryInfo queryInfo);

    R insert(Role role);

    R delete(Integer id);

    R update(Role role);

    R all();
}
