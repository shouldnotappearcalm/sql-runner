package com.calm.sqlrunner.strategy;

import com.calm.sqlrunner.bean.dto.DatabaseInfoDto;
import com.calm.sqlrunner.bean.dto.SqlInfoDto;
import com.calm.sqlrunner.bean.vo.SqlExecResultVo;
import com.calm.sqlrunner.exception.SqlRunnerException;
import com.calm.sqlrunner.pool.ConnectionPool;
import com.calm.sqlrunner.strategy.impl.*;
import com.calm.sqlrunner.enums.DatabaseTypeEnum;
import com.calm.sqlrunner.strategy.impl.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.Map;

/**
 * 数据库sql执行策略模式上下文类.
 *
 * @author gaozhirong on 2020/02/04
 * @version 1.0.0
 */
@Component
public class DbSqlRunnerContext {

    /**
     * 保存若干数据库的 service 实例.
     */
    private static final Map<DatabaseTypeEnum, AbstractSqlRunner> SQL_RUNNER_MAP =
            new EnumMap<>(DatabaseTypeEnum.class);

    @Resource
    private PostgreSqlRunner postgreSqlRunner;

    @Resource
    private DamengSqlRunner damengSqlRunner;

    @Resource
    private MySqlRunner mySqlRunner;

    @Resource
    private SqlServerSqlRunner sqlServerSqlRunner;

    @Resource
    private OracleSqlRunner oracleSqlRunner;

    @Resource
    private SybaseSqlRunner sybaseSqlRunner;

    @Resource
    private ConnectionPool connectionPool;

    /**
     * 初始化数据库连接器
     */
    @PostConstruct
    public void init() {
        SQL_RUNNER_MAP.put(DatabaseTypeEnum.POSTGRESQL, postgreSqlRunner);
        SQL_RUNNER_MAP.put(DatabaseTypeEnum.KING_BASE, postgreSqlRunner);
        SQL_RUNNER_MAP.put(DatabaseTypeEnum.DAMENG, damengSqlRunner);
        SQL_RUNNER_MAP.put(DatabaseTypeEnum.MYSQL, mySqlRunner);
        SQL_RUNNER_MAP.put(DatabaseTypeEnum.ORACLE, oracleSqlRunner);
        SQL_RUNNER_MAP.put(DatabaseTypeEnum.SQL_SERVER, sqlServerSqlRunner);
        SQL_RUNNER_MAP.put(DatabaseTypeEnum.SYBASE, sybaseSqlRunner);
    }

    /**
     * 测试数据库的连通性.
     * @param databaseInfoDto 数据库信息传输对象
     * @return 是否能够连接成功
     * @throws Exception 异常信息对象
     */
    public boolean connect(DatabaseInfoDto databaseInfoDto) throws Exception {
        return SQL_RUNNER_MAP.get(DatabaseTypeEnum.findByCode(databaseInfoDto.getDatabaseType()))
                .connect(databaseInfoDto, connectionPool.getConnection(databaseInfoDto)
                        .orElseThrow(() -> new SqlRunnerException("获取数据库连接失败")));
    }

    /**
     * 在指定数据库中执行 sql 信息，返回结果.
     * @param sqlInfoDto sql 信息传输对象
     * @param databaseTypeEnum 数据库枚举值对象
     * @return sql 执行结果值对象
     * @throws Exception 异常信息对象
     */
    public SqlExecResultVo execSql(SqlInfoDto sqlInfoDto, DatabaseTypeEnum databaseTypeEnum) throws Exception {
        return SQL_RUNNER_MAP.get(databaseTypeEnum).execSql(sqlInfoDto, connectionPool.getConnection(sqlInfoDto
                .getDatabaseInfoDto()).orElseThrow(() -> new SqlRunnerException("获取数据库连接失败")));
    }

    /**
     * 根据数据库连接传输对象获取　Connection.
     * @param databaseInfoDto　数据库信息传输对象
     * @return 连接对象
     * @throws SQLException　SQL　异常
     */
    public Connection getConnection(DatabaseInfoDto databaseInfoDto) throws SQLException {
        return DriverManager.getConnection(SQL_RUNNER_MAP.get(DatabaseTypeEnum
                .findByCode(databaseInfoDto.getDatabaseType())).buildUrl(databaseInfoDto),
                databaseInfoDto.getDatabaseUser(), databaseInfoDto.getDatabasePassword());
    }

}
