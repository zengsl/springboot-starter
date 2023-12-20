package com.ddp.res.pojo.user.dto;

import com.ddp.res.pojo.user.SysUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author zengsl
 */
@Data
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 用户名id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipAddr;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 角色列表
     */
    private Set<String> roles;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 单位ID
     */
    private Long unitId;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 姓名
     */
    private String psnName;

    /**
     * 用户信息
     */
    private SysUser sysUser;


}
