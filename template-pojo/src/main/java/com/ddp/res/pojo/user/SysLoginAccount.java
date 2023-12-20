package com.ddp.res.pojo.user;

import com.ddp.res.pojo.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
    * 登录账号表
    */
//@Schema(description="登录账号表")
@Data
@EqualsAndHashCode(callSuper=true)
public class SysLoginAccount extends BaseEntity implements Serializable {
    /**
    * 主键
    */
    // @Schema(description="主键")
    @NotNull(message = "主键不能为null")
    private Long id;

    /**
    * 用户账号
    */
    // @Schema(description="用户账号")
    @Size(max = 30,message = "用户账号最大长度要小于 30")
    @NotBlank(message = "用户账号不能为空")
    private String loginName;

    /**
    * 来源
    */
    // @Schema(description="来源")
    @Size(max = 30,message = "来源最大长度要小于 30")
    private String source;

    /**
    * 手机、邮箱等
    */
    // @Schema(description="手机、邮箱等")
    @Size(max = 30,message = "手机、邮箱等最大长度要小于 30")
    @NotBlank(message = "手机、邮箱等不能为空")
    private String type;

    /**
    * sys_account的id
    */
    // @Schema(description="sys_account的id")
    @NotNull(message = "sys_account的id不能为null")
    private Integer accountId;

    /**
    * 描述
    */
    // @Schema(description="描述")
    @Size(max = 500,message = "描述最大长度要小于 500")
    private String intro;



    private static final long serialVersionUID = 1L;
}