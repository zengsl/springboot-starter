package com.ddp.res.common.redis;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RedisKeyDefine} 注册表
 *
 * @author XiongQi
 * @date 2022/11/11 10:23
 */
public class RedisKeyRegistry {

    /**
     * Redis RedisKeyDefine 数组
     */
    private static final List<RedisKeyDefine> DEFINES = new ArrayList<>();

    public static void add(RedisKeyDefine define) {
        DEFINES.add(define);
    }

    public static List<RedisKeyDefine> list() {
        return DEFINES;
    }

    public static int size() {
        return DEFINES.size();
    }

}
