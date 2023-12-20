package com.ddp.res.service.user.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddp.res.pojo.user.SysLoginAccount;
import com.ddp.res.service.user.SysLoginAccountService;
import com.ddp.res.mapper.user.SysLoginAccountMapper;
import org.springframework.stereotype.Service;

/**
* @author zengsl
* @description 针对表【sys_login_account(登录账号表)】的数据库操作Service实现
* @createDate 2023-12-19 23:38:53
*/
@Service
public class SysLoginAccountServiceImpl extends ServiceImpl<SysLoginAccountMapper, SysLoginAccount>
    implements SysLoginAccountService {
    @Override
    public SysLoginAccount getSysLoginAccount(String loginName) {
        return this.getOne(lambdaQuery().eq(SysLoginAccount::getLoginName, loginName));
    }
}




