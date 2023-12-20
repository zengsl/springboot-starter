package com.ddp.res.common.enums;

import com.ddp.res.common.enums.common.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserError implements ErrorCode {

    ACCOUNT_NOT_EXIST("100001", "账号不存在"),
    ACCOUNT_PASSWORD_ERROR("100003", "账号或密码错误"),
    ACCOUNT_DISABLE("100005", "账号已被禁用")
    ;
 /*   ACCOUNT_EXIST("100002", "账号已存在"),
    ACCOUNT_PASSWORD_ERROR("100003", "账号或密码错误"),
    ACCOUNT_LOCKED("100004", "账号已被锁定"),
    ACCOUNT_DISABLE("100005", "账号已被禁用"),*/;

    private final String code;
    private final String msg;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
