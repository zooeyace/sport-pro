package com.zyy.sport.service;

import com.zyy.sport.common.QueryInfo;
import com.zyy.sport.common.R;
import com.zyy.sport.entity.Menu;
import com.zyy.sport.entity.Permission;

public interface MenuService {

    R page(QueryInfo queryInfo);

    R findParent();

    R insert(Menu menu);

    R delete(Integer id);

    R update(Menu menu);
}
