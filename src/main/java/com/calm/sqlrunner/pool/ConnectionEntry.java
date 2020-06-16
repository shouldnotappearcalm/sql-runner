package com.calm.sqlrunner.pool;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义池中的连接对象.
 *
 * @author gaozhirong on 2020/02/11
 * @version 1.0.0
 */
@Setter
@Getter
public class ConnectionEntry {

    /**
     * 当前的连接对象
     */
    private Connection connection;

    /**
     * 最后获取的时间
     */
    private Date lastUseTime;

    /**
     * 当前连接被多少个线程持有
     */
    private AtomicInteger holdThreads = new AtomicInteger(0);

    /**
     * 构造方法.
     * @param connection 连接对象
     */
    public ConnectionEntry(Connection connection) {
        this.lastUseTime = new Date();
        this.connection = connection;
    }

}
