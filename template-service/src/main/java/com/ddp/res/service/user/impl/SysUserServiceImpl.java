package com.ddp.res.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ddp.res.pojo.user.SysUser;
import com.ddp.res.service.user.SysUserService;
import com.ddp.res.user.SysUserMapper;
import org.springframework.stereotype.Service;

/**
* @author zengsl
* @description 针对表【sys_user(人员表)】的数据库操作Service实现
* @createDate 2023-12-19 23:38:53
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService {

}




