package com.ddp.res.pojo.user;

import com.ddp.res.pojo.common.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

//@Schema
@Data
@EqualsAndHashCode(callSuper=true)
public class SysAccount extends BaseEntity implements Serializable {
    /**
    * 主键
    */
    // @Schema(description="主键")
    @NotNull(message = "主键不能为null")
    private Long id;

    /**
    * 密码
    */
    // @Schema(description="密码")
    @Size(max = 100,message = "密码最大长度要小于 100")
    private String password;

    /**
    * 密码盐
    */
    // @Schema(description="密码盐")
    @Size(max = 100,message = "密码盐最大长度要小于 100")
    private String passwordSalt;

    /**
    * 用户id
    */
    // @Schema(description="用户id")
    private Long userId;

    /**
    * 头像地址
    */
    // @Schema(description="头像地址")
    @Size(max = 100,message = "头像地址最大长度要小于 100")
    private String profileImg;

    /**
    * 帐号状态（0正常 1停用）
    */
    // @Schema(description="帐号状态（0正常 1停用）")
    @Size(max = 1,message = "帐号状态（0正常 1停用）最大长度要小于 1")
    private String status;

    /**
    * 删除标志（0代表存在 2代表删除）
    */
    // @Schema(description="删除标志（0代表存在 2代表删除）")
    @Size(max = 1,message = "删除标志（0代表存在 2代表删除）最大长度要小于 1")
    private String delFlag;

    /**
    * 最后登录IP
    */
    // @Schema(description="最后登录IP")
    @Size(max = 128,message = "最后登录IP最大长度要小于 128")
    private String lastLoginIp;

    /**
    * 最后登录时间
    */
//    // @Schema(description="最后登录时间")
    private Date lastLoginDate;

    /**
    * 描述
    */
//    // @Schema(description="描述")
    @Size(max = 500,message = "描述最大长度要小于 500")
    private String intro;


    private static final long serialVersionUID = 1L;
}