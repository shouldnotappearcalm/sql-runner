package com.calm.sqlrunner.strategy.impl;

import com.calm.sqlrunner.strategy.AbstractSqlRunner;
import org.springframework.stereotype.Component;

/**
 * SQL Server 数据库连接的策略模式实现类.
 *
 * @author gaozhirong on 2020/2/4.
 **/
@Component
public class SqlServerSqlRunner extends AbstractSqlRunner {

    /**
     * 加载 SQL Server 驱动类的全路径名.
     */
    private static final String SQL_SERVER_DRIVER = "net.sourceforge.jtds.jdbc.Driver";

    /**
     * SQL Server 的数据库连接 URL.
     */
    private static final String SQL_SERVER_URL = "jdbc:jtds:sqlserver://{}:{};databaseName={}";

    /**
     * 默认的构造方法，需要重写父抽象类的部分属性.
     */
    public SqlServerSqlRunner() {
        super.driver = SQL_SERVER_DRIVER;
        super.url = SQL_SERVER_URL;
    }

}
