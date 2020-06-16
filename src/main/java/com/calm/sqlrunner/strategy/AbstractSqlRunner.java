package com.calm.sqlrunner.strategy;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.calm.sqlrunner.bean.dto.DatabaseInfoDto;
import com.calm.sqlrunner.bean.dto.SqlInfoDto;
import com.calm.sqlrunner.bean.vo.SqlExecResultVo;
import com.calm.sqlrunner.consts.Const;
import com.calm.sqlrunner.utils.StrKit;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author gaozhirong
 * @date 2020-02-03 9:32 下午
 */
public class AbstractSqlRunner {

    /**
     * 默认的用于测试数据库连接的万能 SQL.
     */
    private static final String DEFAULT_SQL = "select 1";

    /**
     * 是否已加载过数据库驱动.
     */
    private AtomicBoolean isLoad = new AtomicBoolean(false);

    /**
     * 数据库驱动类全路径名.
     */
    protected String driver;

    /**
     * 执行数据库连接的 SQL 语句.
     */
    protected String connectSql = DEFAULT_SQL;

    /**
     * 数据库连接的 URL.
     */
    protected String url;

    /**
     * 连接数据库的抽象方法.
     *
     * @param databaseInfoDto 数据库信息传输对象
     * @return 布尔值
     * @throws Exception 异常
     */
    public boolean connect(DatabaseInfoDto databaseInfoDto, Connection connection) throws Exception {
        SqlInfoDto sqlInfoDto = new SqlInfoDto().setDatabaseInfoDto(databaseInfoDto).setSql(this.connectSql);
        return this.execSql(sqlInfoDto, connection).getRow() > 0;
    }

    /**
     * 执行 sql 语句
     * @param sqlInfoDto sql 信息传输对象
     */
    public SqlExecResultVo execSql(SqlInfoDto sqlInfoDto, Connection connection) throws Exception {
        this.lazyLoadDriver();
        DriverManager.setLoginTimeout(Const.DATABASE_TIME_OUT);

        DatabaseInfoDto databaseInfoDto = sqlInfoDto.getDatabaseInfoDto();
        try (PreparedStatement ps = connection.prepareStatement(sqlInfoDto.getSql())) {
            SqlExecResultVo sqlExecResultVo = new SqlExecResultVo();

            //执行 sql 判断是否是查询语句
            boolean isSelectSql = ps.execute();
            if (isSelectSql) {
                try (ResultSet resultSet = ps.getResultSet()) {
                    sqlExecResultVo.setDataArray(this.resultSet2JsonArray(resultSet));
                }
                sqlExecResultVo.setRow(sqlExecResultVo.getDataArray().size());
            } else {
                sqlExecResultVo.setRow(ps.getUpdateCount());
            }
            return sqlExecResultVo;
        }
    }

    /**
     * 构建生成连接数据库的 URL，该方法是默认构建方法，子类可重写.
     *
     * @param databaseInfoDto 数据库信息传输对象
     * @return URL
     */
    public String buildUrl(DatabaseInfoDto databaseInfoDto) {
        return StrKit.format(url, databaseInfoDto.getDatabaseHost(), databaseInfoDto.getDatabasePort(),
                StringUtils.isBlank(databaseInfoDto.getDatabaseName()) ? "" : databaseInfoDto.getDatabaseName());
    }

    /**
     * 加载数据库驱动，子类可重写此方法.
     *
     * @throws ClassNotFoundException 类未找到异常
     */
    protected void lazyLoadDriver() throws ClassNotFoundException {
        if (!isLoad.get()) {
            Class.forName(this.driver);
            isLoad.set(true);
        }
    }

    /**
     * 将 sql 执行结果的 ResultSet 转换为 Json 数组
     * @param resultSet sql 执行的结果集
     * @return 转换后的 Json 数组
     * @throws SQLException 操作结果集的异常
     */
    private JSONArray resultSet2JsonArray(ResultSet resultSet) throws SQLException {
        JSONArray resultJsonArray = new JSONArray();
        while (resultSet.next()) {
            resultJsonArray.add(resultRow2JsonObject(resultSet));
        }
        return resultJsonArray;
    }

    /**
     * 将结果行转换为 json 对象.
     * @param resultSet 结果对象
     * @return 转换后的 json object
     * @throws SQLException 执行过程中的 sql 异常
     */
    private JSONObject resultRow2JsonObject(ResultSet resultSet) throws SQLException {
        JSONObject rowObj = new JSONObject();

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
            String columnName = resultSetMetaData.getColumnName(i);
            switch (resultSetMetaData.getColumnType(i)) {
                case Types.ARRAY:
                    Array array = resultSet.getArray(columnName);
                    // 取出数组真实的值
                    if (array != null) {
                        rowObj.put(columnName, array.getArray());
                    }
                    break;
                case Types.SMALLINT:
                case Types.INTEGER:
                case Types.TINYINT:
                    rowObj.put(columnName, resultSet.getInt(columnName));
                    break;
                case Types.BOOLEAN:
                    rowObj.put(columnName, resultSet.getBoolean(columnName));
                    break;
                case Types.BLOB:
                    rowObj.put(columnName, resultSet.getBlob(columnName));
                    break;
                case Types.DOUBLE:
                    rowObj.put(columnName, resultSet.getDouble(columnName));
                    break;
                case Types.FLOAT:
                    rowObj.put(columnName, resultSet.getFloat(columnName));
                    break;
                case Types.NVARCHAR:
                    rowObj.put(columnName, resultSet.getNString(columnName));
                    break;
                case Types.BIGINT:
                case Types.VARCHAR:
                    rowObj.put(columnName, resultSet.getString(columnName));
                    break;
                case Types.DATE:
                    rowObj.put(columnName, resultSet.getDate(columnName));
                    break;
                case Types.TIMESTAMP:
                    rowObj.put(columnName, resultSet.getTimestamp(columnName));
                    break;
                default:
                    rowObj.put(columnName, resultSet.getObject(columnName));
            }
        }
        return rowObj;
    }


}
