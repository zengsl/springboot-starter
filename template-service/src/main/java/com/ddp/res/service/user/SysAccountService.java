package com.ddp.res.service.user;


import com.ddp.res.pojo.user.SysAccount;

/**
* @author zengsl
* @description 针对表【sys_account】的数据库操作Service
* @createDate 2023-12-19 23:38:53
*/
public interface SysAccountService  {

    SysAccount getEnableUser(String loginName);

    SysAccount getEnableUser(Integer accountId);


}
