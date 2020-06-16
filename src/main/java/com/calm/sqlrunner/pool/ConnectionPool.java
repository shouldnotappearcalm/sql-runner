package com.calm.sqlrunner.pool;

import com.calm.sqlrunner.bean.dto.DatabaseInfoDto;
import com.calm.sqlrunner.consts.Const;
import com.calm.sqlrunner.strategy.DbSqlRunnerContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 自定义 Connection 连接池.
 *
 * @author gaozhirong on 2020/02/11
 * @version 1.0.0
 */
@Slf4j
@Component
public class ConnectionPool {

    /**
     * 数据库连接存活的分钟数.
     */
    @Value("${sqlrunner.connection.live-time}")
    private int connectionLiveMinutes;

    /**
     * 数据库运行对象策略上下文.
     */
    @Resource
    private DbSqlRunnerContext sqlRunnerContext;

    /**
     * connection 连接池.
     */
    @Getter
    private Map<String, Queue<ConnectionEntry>> pool = new ConcurrentHashMap<>(Const.CONNECTION_POOL_SIZE);

    /**
     * 添加 connection.
     *
     * @param databaseInfoDto 数据库信息传输对象
     * @param connection      连接对象
     */
    public void addConnection(DatabaseInfoDto databaseInfoDto, Connection connection) {
        String databaseUniqueIdentification = databaseInfoDto.getUniqueIdentification();
        // 从连接池中获取连接对象
        Queue<ConnectionEntry> connectionEntryQueue = pool.getOrDefault(databaseUniqueIdentification,
                new ConcurrentLinkedQueue<>());

        boolean isExistConnection = connectionEntryQueue
                .stream()
                .anyMatch(connectionEntry -> connectionEntry.getConnection() == connection);
        if (!isExistConnection) {
            ConnectionEntry connectionEntry = new ConnectionEntry(connection);
            connectionEntryQueue.add(connectionEntry);
        }
        pool.put(databaseInfoDto.getUniqueIdentification(), connectionEntryQueue);
    }

    /**
     * 根据数据库连接信息获取 Connection.
     *
     * @param databaseInfoDto 数据库信息传输对象
     * @return 数据库连接对象
     */
    public Optional<Connection> getConnection(DatabaseInfoDto databaseInfoDto) throws SQLException {
        String databaseUniqueIdentification = databaseInfoDto.getUniqueIdentification();

        Queue<ConnectionEntry> connectionEntryQueue = pool.get(databaseUniqueIdentification);
        if (CollectionUtils.isEmpty(connectionEntryQueue)) {
            Connection connection = sqlRunnerContext.getConnection(databaseInfoDto);
            this.addConnection(databaseInfoDto, connection);
            return Optional.of(connection);
        }
        //　如果存在 connection　暂时先返回连接对象
        return connectionEntryQueue.stream()
                .findFirst()
                .flatMap(connectionEntry -> {
                    connectionEntry.setLastUseTime(new Date());
                    return Optional.of(connectionEntry.getConnection());
                });

    }

    @Scheduled(cron = "${sqlrunner.corn.clean-pool}")
    public void cleanPool() {
        Date now = new Date();
        pool.forEach((key, connectionEntryQueue) -> {
            connectionEntryQueue.stream()
                    .filter(connectionEntry -> DateUtils.addMinutes(connectionEntry.getLastUseTime(), connectionLiveMinutes).before(now))
                    .forEach(connectionEntry -> {
                        Connection connection = connectionEntry.getConnection();
                        if (connection != null) {
                            try {
                                connection.close();
                            } catch (SQLException e) {
                                log.error("关闭 connection 失败", e);
                            }
                        }
                        connectionEntryQueue.remove(connectionEntry);
                    });
        });
    }

}
