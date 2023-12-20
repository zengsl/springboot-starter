package com.ddp.res.service.login;

import com.ddp.res.pojo.user.dto.LoginUser;

public interface SysLoginService {

    LoginUser login(String loginName, String password, String verifyCode);
    LoginUser login(String loginName, String password);
}
