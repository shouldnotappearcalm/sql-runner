package com.calm.sqlrunner.consts;

import lombok.experimental.UtilityClass;

/**
 * 常量类.
 *
 * @author gaozhirong on 2020/02/03
 * @version 1.0.0
 */
@UtilityClass
public class Const {

    /**
     * API 版本1的前缀.
     */
    public static final String PREFIX_V1 = "/api/v1";

    /**
     * 数据连接超时时间，统一设置为 3 秒.
     */
    public static final int DATABASE_TIME_OUT = 3;

    /**
     * 默认的 connection 连接池大小.
     */
    public static final int CONNECTION_POOL_SIZE = 16;

}
