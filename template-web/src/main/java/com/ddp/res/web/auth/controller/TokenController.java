package com.ddp.res.web.auth.controller;

import com.ddp.res.pojo.user.dto.LoginUser;
import com.ddp.res.service.login.SysLoginService;
import com.ddp.res.web.auth.dto.LoginReq;
import com.ddp.res.web.common.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zengsl
 * @throws
 * @date 2023/12/19 23:54
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class TokenController {

    private final SysLoginService sysLoginService;

    @PostMapping("login")
    public R<?> login(@RequestBody LoginReq loginReq) {
        LoginUser loginUser = this.sysLoginService.login(loginReq.getLoginName(), loginReq.getPassword(), loginReq.getVerifyCode());
        // 获取登录token
        return R.ok();
    }
}
