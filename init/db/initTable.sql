create schema 'res_dev';

drop table sys_account;
create table sys_account
(
    ID              int auto_increment comment '主键'
        primary key,
    PASSWORD        varchar(100) default ''  null comment '密码',
    PASSWORD_SALT   varchar(100) default ''  null comment '密码盐',
    USER_ID         int                      null comment '用户id',
    PROFILE_IMG     varchar(100) default ''  null comment '头像地址',
    STATUS          char         default '0' null comment '帐号状态（0正常 1停用）',
    DEL_FLAG        char         default '0' null comment '删除标志（0代表存在 2代表删除）',
    LAST_LOGIN_IP   varchar(128) default ''  null comment '最后登录IP',
    LAST_LOGIN_DATE timestamp                null comment '最后登录时间',
    CREATE_BY       varchar(64)  default ''  null comment '创建者',
    CREATE_TIME     timestamp                null comment '创建时间',
    UPDATE_BY       varchar(64)  default ''  null comment '更新者',
    UPDATE_TIME     timestamp                null comment '更新时间',
    INTRO           varchar(500)             null comment '描述',
    constraint ID_SYS_ACCOUNT
        unique (ID)
);

drop table sys_login_account;
create table sys_login_account
(
    ID          int auto_increment comment '主键'
        primary key,
    LOGIN_NAME  varchar(30)               not null comment '用户账号',
    SOURCE      varchar(30) default 'add' null comment '来源',
    TYPE        varchar(30)               not null comment '手机、邮箱等',
    ACCOUNT_ID  int                       not null comment 'sys_account的id',
    CREATE_BY   varchar(64) default ''    null comment '创建者',
    CREATE_TIME timestamp                 null comment '创建时间',
    UPDATE_BY   varchar(64) default ''    null comment '更新者',
    UPDATE_TIME timestamp                 null comment '更新时间',
    INTRO       varchar(500)              null comment '描述',
    constraint ID_SYS_LOGIN_ACCOUNT
        unique (ID)
)
    comment '登录账号表' row_format = DYNAMIC;

drop table sys_user;

create table sys_user
(
    ID             int auto_increment comment '主键'
        primary key,
    NAME           varchar(30)  default ''   null comment '姓名',
    NICK_NAME      varchar(30)  default ''   null comment '用户昵称',
    USER_TYPE      varchar(2)   default '00' null comment '用户类型（00系统用户）',
    ACCOUNT_STATUS char         default '0'  null comment 'sys_account表关联状态',
    USER_NO        varchar(64)  default ''   null comment '用户编号',
    SEX            char         default '2'  null comment '用户性别（0男 1女 2未知）',
    EMAIL          varchar(50)  default ''   null comment '用户邮箱',
    PHONE          varchar(11)               null comment '手机号码',
    CARD_TYPE      varchar(2)   default ''   null comment '证件类型',
    CARD_CODE      varchar(30)  default ''   null comment '证件号码',
    IS_COMPLETE    char         default '0'  null comment '信息是否完整(0,不完整，1完整)',
    EMAIL_ENABLE   char         default '0'  null comment '邮箱是否激活',
    PHONE_ENABLE   char         default '0'  null comment '手机是否激活',
    CREATE_BY      varchar(64)  default ''   null comment '创建者',
    CREATE_TIME    timestamp                 null comment '创建时间',
    UPDATE_BY      varchar(64)  default ''   null comment '更新者',
    UPDATE_TIME    timestamp                 null comment '更新时间',
    INTRO          varchar(500)              null comment '描述',
    BIRTHDAY       varchar(32)  default ''   null comment '生日',
    avatar         varchar(100) default ''   null comment '头像地址',
    constraint IDX_SYS_USER_ID
        unique (ID),
    constraint unique_phone
        unique (PHONE),
    constraint unique_email
        unique (EMAIL)
)
    comment '人员表' row_format = DYNAMIC;