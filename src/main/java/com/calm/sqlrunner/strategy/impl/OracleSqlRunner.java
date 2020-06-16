package com.calm.sqlrunner.strategy.impl;

import com.calm.sqlrunner.strategy.AbstractSqlRunner;
import org.springframework.stereotype.Component;

/**
 * Oracle 数据库连接的策略模式实现类.
 *
 * @author gaozhirong on 2020/2/4
 * @version 1.0.0
 **/
@Component
public class OracleSqlRunner extends AbstractSqlRunner {

    /**
     * 加载 Oracle 驱动类的全路径名.
     */
    private static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";

    /**
     * 查询 Oracle 数据库是否可用的专属 SQL.
     */
    private static final String ORACLE_SQL = "SELECT 1 FROM dual";

    /**
     * Oracle 的数据库连接 URL.
     */
    private static final String ORACLE_URL = "jdbc:oracle:thin:@{}:{}:{}";

    /**
     * 默认的构造方法，需要重写父抽象类的部分属性.
     */
    public OracleSqlRunner() {
        super.driver = ORACLE_DRIVER;
        super.connectSql = ORACLE_SQL;
        super.url = ORACLE_URL;
    }

}
