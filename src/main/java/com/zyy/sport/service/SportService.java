package com.zyy.sport.service;

import com.zyy.sport.common.QueryInfo;
import com.zyy.sport.common.R;
import com.zyy.sport.entity.Sport;

public interface SportService {

    R delete(Long id);

    R findInfo(Long id);

    R update(Sport sport);

    R insert(Sport sport);

    R findPage(QueryInfo queryInfo);

//    R insertStep(List<WxRun> runs);

//    R stepReport();

}