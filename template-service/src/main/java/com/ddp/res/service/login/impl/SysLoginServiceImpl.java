package com.ddp.res.service.login.impl;

import cn.hutool.core.lang.Assert;
import com.ddp.res.common.enums.UserError;
import com.ddp.res.common.exception.SystemException;
import com.ddp.res.common.utils.CryptUtil;
import com.ddp.res.pojo.user.SysAccount;
import com.ddp.res.pojo.user.SysUser;
import com.ddp.res.pojo.user.dto.LoginUser;
import com.ddp.res.service.login.SysLoginService;
import com.ddp.res.service.user.SysAccountService;
import com.ddp.res.service.user.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author zengsl
 */
@Service
@RequiredArgsConstructor
public class SysLoginServiceImpl implements SysLoginService {

    private final SysAccountService sysAccountService;
    private final SysUserService sysUserService;

    @Override
    public LoginUser login(String loginName, String password, String verifyCode) {
        // TODO check verifyCode

        return this.login(loginName, password);
    }

    @Override
    public LoginUser login(String loginName, String password) {
        this.preCheck(loginName, password);
        SysAccount enableUser = this.sysAccountService.getEnableUser(loginName);
        // 匹配密码是否相等
        if (!CryptUtil.digest(password, enableUser.getPasswordSalt()).equals(enableUser.getPassword())) {
            throw new SystemException(UserError.ACCOUNT_PASSWORD_ERROR);
        }
        SysUser sysUser = this.sysUserService.getById(enableUser.getUserId());
        Assert.notNull(sysUser, SystemException.withExSupplier(UserError.ACCOUNT_NOT_EXIST));
        LoginUser loginUser = new LoginUser();
        loginUser.setLoginName(loginName);
//        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setSysUser(sysUser);
        loginUser.setUserId(sysUser.getUserId());

        // 角色集合
//        Set<String> roles = permissionService.getRolePermission(sysUser);
        // 权限集合
//        Set<String> permissions = permissionService.getMenuPermission(sysUser);

        return loginUser;
    }

    private void preCheck(String loginName, String password) {
      // TODO
    }
}
