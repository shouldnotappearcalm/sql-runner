package com.calm.sqlrunner.strategy.impl;

import com.calm.sqlrunner.strategy.AbstractSqlRunner;
import org.springframework.stereotype.Component;

/**
 *  sysbase 执行实现类.
 *
 * @author gaozhirong on 2020/02/04
 * @version 1.0.0
 */
@Component
public class SybaseSqlRunner extends AbstractSqlRunner {

    /**
     * 加载 sybase 驱动类的全路径名.
     */
    private static final String SYBASE_DRIVER = "net.sourceforge.jtds.jdbc.Driver";

    /**
     * sybase 的数据库连接 URL.
     */
    private static final String SYBASE_URL = "jdbc:jtds:sybase://{}:{}/{}";

    /**
     * 构造方法替换 driver 和 url
     */
    public SybaseSqlRunner() {
        super.driver = SYBASE_DRIVER;
        super.url = SYBASE_URL;
    }

}
