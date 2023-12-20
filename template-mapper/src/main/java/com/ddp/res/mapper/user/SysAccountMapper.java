package com.ddp.res.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ddp.res.pojo.user.SysAccount;

public interface SysAccountMapper extends BaseMapper<SysAccount> {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAccount record);

    int insertSelective(SysAccount record);

    SysAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAccount record);

    int updateByPrimaryKey(SysAccount record);
}