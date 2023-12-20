package com.ddp.res.service.user.impl;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddp.res.common.enums.DataStatus;
import com.ddp.res.common.enums.UserError;
import com.ddp.res.common.exception.SystemException;
import com.ddp.res.mapper.user.SysAccountMapper;
import com.ddp.res.pojo.user.SysAccount;
import com.ddp.res.pojo.user.SysLoginAccount;
import com.ddp.res.service.user.SysAccountService;
import com.ddp.res.service.user.SysLoginAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zengsl
 * @description 针对表【sys_account】的数据库操作Service实现
 * @createDate 2023-12-19 23:38:53
 */
@Service
@RequiredArgsConstructor
public class SysAccountServiceImpl extends ServiceImpl<SysAccountMapper, SysAccount>
        implements SysAccountService {

    private final SysLoginAccountService sysLoginAccountService;

    @Transactional(readOnly = true)
    @Override
    public SysAccount getEnableUser(String loginName) {
        SysLoginAccount sysLoginAccount = this.sysLoginAccountService.getSysLoginAccount(loginName);
        Assert.notNull(sysLoginAccount, SystemException.withExSupplier(UserError.ACCOUNT_NOT_EXIST));
        return this.getEnableUser(sysLoginAccount.getAccountId());
    }

    @Transactional(readOnly = true)
    @Override
    public SysAccount getEnableUser(Integer accountId) {
        SysAccount sysAccount = this.getById(accountId);
        Assert.notNull(sysAccount, SystemException.withExSupplier(UserError.ACCOUNT_NOT_EXIST));
        if (sysAccount.getStatus().equals(DataStatus.NORMAL.getValue())) {
            throw new SystemException(UserError.ACCOUNT_DISABLE);
        }
        return sysAccount;
    }
}




