package com.calm.sqlrunner.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import java.util.UUID;

/**
 * 字符串工具类.
 *
 * @author gaozhirong on 2020/2/3
 **/
@UtilityClass
public class StrKit {

    /**
     * 分隔符 - 横杠.
     */
    private static final String SEP_HYPHEN = "-";

    /**
     * 使用 Slf4j 的方式来格式化字符串.
     *
     * @param pattern 字符串模式
     * @param args 不定长的参数值
     * @return 格式化后的字符串
     */
    public static String format(String pattern, Object...args) {
        return MessageFormatter.arrayFormat(pattern, args).getMessage();
    }

    /**
     * 获取UUID.
     *
     * @return 字符串
     */
    public static String getUuid() {
        return StringUtils.remove(UUID.randomUUID().toString(), SEP_HYPHEN);
    }

    /**
     * 将字符串转换为 16 进制字符串.
     *
     * @param text 字符串
     * @return 16 进制字符串
     */
    public static String getMd5(String text) {
        return DigestUtils.md5Hex(text);
    }

}
