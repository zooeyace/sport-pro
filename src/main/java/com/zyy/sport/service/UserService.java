package com.zyy.sport.service;

import com.zyy.sport.VO.LoginVO;
import com.zyy.sport.common.QueryInfo;
import com.zyy.sport.common.R;
import com.zyy.sport.entity.EasyUser;
import com.zyy.sport.entity.Role;

public interface UserService {

    R login(LoginVO loginVO);

    EasyUser findByUsername(String username);

    R page(QueryInfo queryInfo);

    R insert(EasyUser user);

    R delete(Integer id);

    R update(EasyUser user);

    /**
     *  通过邮箱找回密码
     * @param receiver 邮箱 email
     * @param newPassword 新密码
     */
    void updatePwdByMail(String receiver, String newPassword);

    /**
     *  小程序登录
     */
    R miniLogin(String openid, String sessionKey);

    /**
     *  小程序 根据openid修改信息
     */
    R updateInfoByOpenid(EasyUser user);
}
