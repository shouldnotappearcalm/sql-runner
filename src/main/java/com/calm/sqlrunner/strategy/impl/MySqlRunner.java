package com.calm.sqlrunner.strategy.impl;

import com.calm.sqlrunner.strategy.AbstractSqlRunner;
import org.springframework.stereotype.Component;

/**
 *  mysql执行实现类.
 *
 * @author gaozhirong on 2020/02/04
 * @version 1.0.0
 */
@Component
public class MySqlRunner extends AbstractSqlRunner {

    /**
     * 加载 MySQL 驱动类的全路径名.
     */
    private static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * MySql 的数据库连接 URL.
     */
    private static final String MYSQL_URL = "jdbc:mysql://{}:{}/{}";

    /**
     * 构造方法替换 driver 和 url
     */
    public MySqlRunner() {
        super.driver = MYSQL_DRIVER;
        super.url = MYSQL_URL;
    }

}
