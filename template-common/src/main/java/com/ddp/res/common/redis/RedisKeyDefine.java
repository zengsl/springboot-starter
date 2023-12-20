package com.ddp.res.common.redis;

import com.ddp.res.common.constants.Constants;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.Duration;

/**
 * Redis Key 定义类
 *
 * @author ruoyi
 */
@Data
public class RedisKeyDefine {

    /**
     * Key
     */
    private final String key;

    /**
     * Key 模板
     */
    private final String keyTemplate;

    /**
     * Key 类型的枚举
     */
    private final KeyTypeEnum keyType;

    /**
     * Value 类型
     * <p>
     * 如果是使用分布式锁，设置为 {@link java.util.concurrent.locks.Lock} 类型
     */
    private final Class<?> valueType;

    /**
     * 超时类型
     */
    private final TimeoutTypeEnum timeoutType;

    /**
     * 过期时间
     */
    private final Duration timeout;

    /**
     * 备注
     */
    private final String intro;

    private RedisKeyDefine(String intro, String key, String keyTemplate, KeyTypeEnum keyType, Class<?> valueType, TimeoutTypeEnum timeoutType, Duration timeout) {
        this.intro = intro;
        this.key = key;
        this.keyTemplate = keyTemplate;
        this.keyType = keyType;
        this.valueType = valueType;
        this.timeout = timeout;
        this.timeoutType = timeoutType;
        // 添加注册表
        RedisKeyRegistry.add(this);
    }

    public RedisKeyDefine(String intro, String key, String keyTemplate, KeyTypeEnum keyType, Class<?> valueType, Duration timeout) {
        this(intro, key, keyTemplate, keyType, valueType, TimeoutTypeEnum.FIXED, timeout);
    }

    public RedisKeyDefine(String intro, String key, String keyTemplate, KeyTypeEnum keyType, Class<?> valueType, TimeoutTypeEnum timeoutType) {
        this(intro, key, keyTemplate, keyType, valueType, timeoutType, null);
    }

    /**
     * 格式化 Key
     * <p>
     * 注意，内部采用 {@link String#format(String, Object...)} 实现
     *
     * @param args 格式化的参数
     * @return Key
     */
    public String formatKey(Object... args) {
        return String.format(key + keyTemplate, args);
    }

    public String matchAllKey() {
        return String.format(key + Constants.ASTERISK);
    }


    /**
     * key类型
     */
    @Getter
    @AllArgsConstructor
    public enum KeyTypeEnum {
        /**
         * STRING
         */
        STRING("String"), LIST("List"), HASH("Hash"), SET("Set"), Z_SET("Sorted Set"), STREAM("Stream"), PUBSUB("Pub/Sub"), OJBECT("OJBECT");

        /**
         * 类型
         */
        @JsonValue
        private final String type;

    }

    @Getter
    @AllArgsConstructor
    public enum TimeoutTypeEnum {
        /**
         * 永不超时
         */
        FOREVER(1),
        /**
         * 动态超时
         */
        DYNAMIC(2),

        /**
         * 固定超时
         */
        FIXED(3);

        /**
         * 类型
         */
        @JsonValue
        private final Integer type;
    }

}
