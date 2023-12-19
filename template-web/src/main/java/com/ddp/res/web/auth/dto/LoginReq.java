package com.ddp.res.web.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *
 * @author zengsl
 * @date 2023/12/19 23:52
 */
@Data
public class LoginReq {

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码
     */
    @JsonProperty(value = "code")
    private String verifyCode;

}
