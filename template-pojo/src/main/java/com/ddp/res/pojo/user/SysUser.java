package com.ddp.res.pojo.user;

import com.ddp.res.pojo.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 人员表
 */
//@Schema(description="人员表")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    // @Schema(description="主键")
    @NotNull(message = "主键不能为null")
    private Integer id;

    /**
     * 姓名
     */
    // @Schema(description="姓名")
    @Size(max = 30, message = "姓名最大长度要小于 30")
    private String name;

    /**
     * 用户昵称
     */
    // @Schema(description="用户昵称")
    @Size(max = 30, message = "用户昵称最大长度要小于 30")
    private String nickName;

    /**
     * 用户类型（00系统用户）
     */
    // @Schema(description="用户类型（00系统用户）")
    @Size(max = 2, message = "用户类型（00系统用户）最大长度要小于 2")
    private String userType;

    /**
     * sys_account表关联状态
     */
    // @Schema(description="sys_account表关联状态")
    @Size(max = 1, message = "sys_account表关联状态最大长度要小于 1")
    private String accountStatus;

    /**
     * 用户编号
     */
    // @Schema(description="用户编号")
    @Size(max = 64, message = "用户编号最大长度要小于 64")
    private String userNo;

    /**
     * 用户性别（0男 1女 2未知）
     */
    // @Schema(description="用户性别（0男 1女 2未知）")
    @Size(max = 1, message = "用户性别（0男 1女 2未知）最大长度要小于 1")
    private String sex;

    /**
     * 用户邮箱
     */
    // @Schema(description="用户邮箱")
    @Size(max = 50, message = "用户邮箱最大长度要小于 50")
    private String email;

    /**
     * 手机号码
     */
    // @Schema(description="手机号码")
    @Size(max = 11, message = "手机号码最大长度要小于 11")
    private String phone;

    /**
     * 证件类型
     */
    // @Schema(description="证件类型")
    @Size(max = 2, message = "证件类型最大长度要小于 2")
    private String cardType;

    /**
     * 证件号码
     */
    // @Schema(description="证件号码")
    @Size(max = 30, message = "证件号码最大长度要小于 30")
    private String cardCode;

    /**
     * 信息是否完整(0,不完整，1完整)
     */
    // @Schema(description="信息是否完整(0,不完整，1完整)")
    @Size(max = 1, message = "信息是否完整(0,不完整，1完整)最大长度要小于 1")
    private String isComplete;

    /**
     * 邮箱是否激活
     */
    // @Schema(description="邮箱是否激活")
    @Size(max = 1, message = "邮箱是否激活最大长度要小于 1")
    private String emailEnable;

    /**
     * 手机是否激活
     */
    // @Schema(description="手机是否激活")
    @Size(max = 1, message = "手机是否激活最大长度要小于 1")
    private String phoneEnable;

    /**
     * 描述
     */
    // @Schema(description="描述")
    @Size(max = 500, message = "描述最大长度要小于 500")
    private String intro;

    /**
     * 生日
     */
    // @Schema(description="生日")
    @Size(max = 32, message = "生日最大长度要小于 32")
    private String birthday;

    /**
     * 头像地址
     */
    // @Schema(description="头像地址")
    @Size(max = 100, message = "头像地址最大长度要小于 100")
    private String avatar;

    private static final long serialVersionUID = 1L;
}