package com.ddp.res.common.redis;



import java.awt.*;
import java.time.Duration;
import java.util.Map;

/**
 * 缓存常量信息
 *
 * @author ruoyi
 */
public interface CacheConstants {


    /**
     * 缓存刷新时间，默认120（分钟）
     */
    long REFRESH_TIME = 120;

    /**
     * 密码最大错误次数
     */
    int PASSWORD_MAX_RETRY_COUNT = 5;

    /**
     * 权限缓存前缀
     */
    RedisKeyDefine LOGIN_TOKEN = new RedisKeyDefine("用户信息", "login_tokens:", "%s", RedisKeyDefine.KeyTypeEnum.STRING, Object.class, Duration.ofHours(12));

    /**
     * 验证码 redis key
     */
    RedisKeyDefine CAPTCHA_CODE = new RedisKeyDefine("验证码", "captcha_codes:", "%s", RedisKeyDefine.KeyTypeEnum.STRING, String.class, Duration.ofMinutes(2));

    /**
     * 参数管理 cache key
     */
    RedisKeyDefine SYS_CONFIG = new RedisKeyDefine("配置信息", "sys_config:", "%s", RedisKeyDefine.KeyTypeEnum.STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.FOREVER);

    /**
     * 字典选项管理 cache key
     */
    RedisKeyDefine SYS_DICT_OPTION = new RedisKeyDefine("数据字典", "sys_dict_opn:", "%s", RedisKeyDefine.KeyTypeEnum.STRING, List.class, RedisKeyDefine.TimeoutTypeEnum.FOREVER);

    /**
     * 登录账户密码错误次数 redis key
     */
    RedisKeyDefine PWD_ERR_CNT = new RedisKeyDefine("登录账户密码错误次数", "pwd_err_cnt:", "%s", RedisKeyDefine.KeyTypeEnum.STRING, String.class, Duration.ofMinutes(10));

    /**
     * 登录IP黑名单 cache key
     */
    RedisKeyDefine LOGIN_BLACK_IP_LIST = new RedisKeyDefine("登录IP黑名单", "sys.login.blackIPList:", "%s", RedisKeyDefine.KeyTypeEnum.STRING, String.class, Duration.ofMinutes(10));

    /**
     * pdf生成标志
     */
    RedisKeyDefine PDF_GEN_FLAG = new RedisKeyDefine("pdf生成标志", "pdf_gen_flag:", "%s_%s", RedisKeyDefine.KeyTypeEnum.STRING, String.class, Duration.ofMinutes(1));

    /**
     * 申报类别类别缓存
     * top:  List<GrantSettingVO>
     * child：  List<ApplyGrantMappingVO>
     */
    RedisKeyDefine APPLY_GRANT_LIST = new RedisKeyDefine("申报类别缓存", "apply_grant_list:", "%s", RedisKeyDefine.KeyTypeEnum.LIST, List.class, Duration.ofDays(7));

    /**
     * 个人单位授权申报类别缓存 org_auth_apply_grant_list:userId:orgId
     */
    RedisKeyDefine APPLY_GRANT_LIST_PERSON = new RedisKeyDefine("个人单位授权申报类别缓存", "org_auth_apply_grant_list:", "%s_%s", RedisKeyDefine.KeyTypeEnum.LIST, List.class, Duration.ofDays(7));
    /**
     * /**
     * 消息验证码
     */
    RedisKeyDefine MESSAGE_VERIFY_CODE = new RedisKeyDefine("消息验证码", "message_verify_code:", "%s", RedisKeyDefine.KeyTypeEnum.STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);

    /**
     * pdf生成标志
     */
    RedisKeyDefine PDF_RESET_FLAG = new RedisKeyDefine("pdf重置标志", "pdf_reset_flag:", "%s_%s", RedisKeyDefine.KeyTypeEnum.STRING, String.class, Duration.ofSeconds(30));

    /**
     * 业务审核意见（biz_audit_comment:bizType:bizId）
     */
    RedisKeyDefine BIZ_AUDIT_COMMENT = new RedisKeyDefine("业务审核意见", "biz_audit_comment:", "%s_%s", RedisKeyDefine.KeyTypeEnum.STRING, String.class, Duration.ofHours(12));

    /**
     * 业务自定义审核意见（biz_custom_audit_comment:bizType:userId:roleId）
     */
    RedisKeyDefine BIZ_CUSTOM_AUDIT_COMMENT = new RedisKeyDefine("自定义业务审核意见", "biz_custom_audit_comment:", "%s_%s_%s", RedisKeyDefine.KeyTypeEnum.STRING, String.class, Duration.ofHours(12));
    /**
     * 申报书受理编号序列（prp_accept_no_seq:前缀）
     */
    RedisKeyDefine PRP_ACCEPT_NO_SEQ = new RedisKeyDefine("申报书受理编号序列", "prp_accept_no_seq:", "%s", RedisKeyDefine.KeyTypeEnum.STRING, Integer.class, RedisKeyDefine.TimeoutTypeEnum.FOREVER);

    /**
     * 首页通知公告缓存
     * eg： key:通知类型
     */
    RedisKeyDefine HOME_NOTICE = new RedisKeyDefine("首页通知公告缓存", "home_notice:", "%s", RedisKeyDefine.KeyTypeEnum.LIST, List.class, Duration.ofDays(7));
    /**
     * 通知信息
     * eg: notice_info:通知id
     */
    RedisKeyDefine NOTICE_INFO = new RedisKeyDefine("通知公告内容缓存", "notice_info:", "%s", RedisKeyDefine.KeyTypeEnum.STRING, Object.class, Duration.ofDays(7));

    /**
     * 通知列表缓存
     * eg: notice_list:通知类型
     */
    RedisKeyDefine NOTICE_LIST = new RedisKeyDefine("通知公告列表缓存", "notice_list:", "%s", RedisKeyDefine.KeyTypeEnum.STRING, Object.class, Duration.ofDays(7));


    /**
     * 帮助中心启用的数据
     * help_center:list 所有数据
     * help_center:id  具体详情
     */
    RedisKeyDefine HELP_CENTER = new RedisKeyDefine("帮助中心启用的数据", "help_center:", "%s", RedisKeyDefine.KeyTypeEnum.STRING, Object.class, RedisKeyDefine.TimeoutTypeEnum.FOREVER);
    /**
     * 帮助中心查看数量
     * help_center_view_num:total  总数量
     * help_center_view_num:anonymity  匿名查看总数量
     * help_center_view_num:login  登录后总数量
     */
    RedisKeyDefine HELP_CENTER_VIEW_COUNT = new RedisKeyDefine("帮助中心查看数量", "help_center_view_num:", "%s", RedisKeyDefine.KeyTypeEnum.SET, Double.class, RedisKeyDefine.TimeoutTypeEnum.FOREVER);
    String HELP_CENTER_VIEW_TOTAL_COUNT = HELP_CENTER_VIEW_COUNT.formatKey("total");
    String HELP_CENTER_ANONYMITY_VIEW_COUNT = HELP_CENTER_VIEW_COUNT.formatKey("anonymity");
    String HELP_CENTER_LOGIN_VIEW_COUNT = HELP_CENTER_VIEW_COUNT.formatKey("login");

    /**
     * WEB登录通知连接情况
     * web_message_connect:userId
     */
    RedisKeyDefine WEB_LOGIN_MESSAGE_CONNECT_INFO = new RedisKeyDefine("WEB登录消息连接情况", "web_message_connect:", "%s", RedisKeyDefine.KeyTypeEnum.STRING, Map.class, RedisKeyDefine.TimeoutTypeEnum.FOREVER);

    /**
     * WEB登录通知连接 用户连接数量
     */
    String WEB_LOGIN_MESSAGE_USER_CONNECT_NUM = WEB_LOGIN_MESSAGE_CONNECT_INFO.formatKey("uid");

    /**
     * WEB登录通知连接 指定可以
     */
    String WEB_LOGIN_MESSAGE_USER_CONNECT_USER_INFO = WEB_LOGIN_MESSAGE_CONNECT_INFO.formatKey("info");
    /**
     * 模板内容缓存
     * eg: template_content:id
     */
    RedisKeyDefine TEMPLATE_CONTENT = new RedisKeyDefine("模板内容缓存", "template_content:", "%s", RedisKeyDefine.KeyTypeEnum.STRING, Object.class, Duration.ofDays(7));

    /**
     * 业务文件映射
     * key: biz_file_mapping:业务类型:业务id
     * value:Map<String,SysFileBaseVO> 即文件code和SysFileBaseVO映射
     */
    RedisKeyDefine BIZ_FILE_MAPPING = new RedisKeyDefine("申报指南文件信息", "biz_file_mapping:", "%s%s", RedisKeyDefine.KeyTypeEnum.STRING, Map.class, Duration.ofDays(180));

    /**
     * 申请书重复新增检查 eg:  prp_double_apply_new: 单位id:uuid
     */
    RedisKeyDefine PRP_APPLY_NEW_CHECK_DOUBLE_APPLY = new RedisKeyDefine("申请书重复新增检查", "prp_double_apply_new:", "%s%s", RedisKeyDefine.KeyTypeEnum.STRING, Map.class, Duration.ofDays(1));

    /**
     * 专家默认密码
     */
    RedisKeyDefine EXPERT_DEFAULT_PASSWORD = new RedisKeyDefine("专家默认密码", "expert_default_password:", "%s_%s", RedisKeyDefine.KeyTypeEnum.STRING, String.class, Duration.ofSeconds(5));


    /**
     * 分布式序列 <br/>
     * key:  distribute_seq:序列名称:机器码_大写字母转int值，  <br/>
     * value= long 时间戳
     */
    RedisKeyDefine DISTRIBUTED_SEQ = new RedisKeyDefine("分布式序列", "distribute_seq:", "%s:%s_%s", RedisKeyDefine.KeyTypeEnum.STRING, String.class, Duration.ofHours(1));

    /**
     * 申报指南key BIZ_FILE_MAPPING
     *
     * @param key key
     * @return String
     */
    static String buildApplyGuideBizFileMapping(String key) {
        return CacheConstants.BIZ_FILE_MAPPING.formatKey("grantSchedule", key);
    }

}
