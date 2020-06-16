package com.calm.sqlrunner.strategy.impl;

import com.calm.sqlrunner.strategy.AbstractSqlRunner;
import org.springframework.stereotype.Component;

/**
 * postgres sql执行实现类.
 *
 * @author gaozhirong on 2020/02/03
 * @version 1.0.0
 */
@Component
public class PostgreSqlRunner extends AbstractSqlRunner {

    /**
     * 加载 PostgreSQL 驱动类的全路径名.
     */
    private static final String PG_DRIVER = "org.postgresql.Driver";

    /**
     * PostgreSQL 的数据库连接 URL.
     */
    private static final String PG_URL = "jdbc:postgresql://{}:{}/{}";

    /**
     * 构造方法替换 driver 和 url
     */
    public PostgreSqlRunner() {
        super.driver = PG_DRIVER;
        super.url = PG_URL;
    }

}
