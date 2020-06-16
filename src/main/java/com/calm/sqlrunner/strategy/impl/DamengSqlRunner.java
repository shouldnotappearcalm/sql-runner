package com.calm.sqlrunner.strategy.impl;

import com.calm.sqlrunner.strategy.AbstractSqlRunner;
import org.springframework.stereotype.Component;

/**
 * 达梦（Dameng）数据库连接的策略模式实现类.
 *
 * @author gaozhirong on 2020/2/4.
 */
@Component
public class DamengSqlRunner extends AbstractSqlRunner {

    /**
     * 加载达梦数据库驱动类的全路径名.
     */
    private static final String DAMENG_DRIVER = "dm.jdbc.driver.DmDriver";

    /**
     * 达梦数据库的连接 URL.
     */
    private static final String DAMENG_URL = "jdbc:dm://{}:{}/{}";

    /**
     * 默认的构造方法，重写了父抽象类的部分属性.
     */
    public DamengSqlRunner() {
        super.driver = DAMENG_DRIVER;
        super.url = DAMENG_URL;
    }

}
