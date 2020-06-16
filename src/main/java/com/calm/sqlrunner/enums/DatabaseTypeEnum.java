package com.calm.sqlrunner.enums;

import com.calm.sqlrunner.exception.SqlRunnerException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据库类型枚举类.
 *
 * @author gaozhirong on 2020/02/04
 * @version 1.0.0
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum DatabaseTypeEnum {

    /**
     * MySQL.
     */
    MYSQL("MySQL", "MySQL"),

    /**
     * PostgreSQL.
     */
    POSTGRESQL("PostgreSQL", "PostgreSQL"),

    /**
     * Sybase.
     */
    SYBASE("Sybase", "Sybase"),

    /**
     * Oracle.
     */
    ORACLE("Oracle", "Oracle"),

    /**
     * 达梦数据库 (Dameng).
     */
    DAMENG("DaMeng", "达梦数据库"),

    /**
     * SQL Server.
     */
    SQL_SERVER("SQLServer", "SQLServer"),

    /**
     * 人大金仓数据库（RDJC）.
     */
    KING_BASE("KingBase", "人大金仓");

    /**
     * 代码值.
     */
    private String code;

    /**
     * 数据库类型的名称.
     */
    private String name;

    /**
     * 根据数据库代码查找枚举.
     * @param code 数据库代码
     * @return 数据库枚举
     */
    public static DatabaseTypeEnum findByCode(String code) {
        for (DatabaseTypeEnum databaseTypeEnum : DatabaseTypeEnum.values()) {
            if (databaseTypeEnum.code.equalsIgnoreCase(code) || databaseTypeEnum.name.equalsIgnoreCase(code)) {
                return databaseTypeEnum;
            }
        }

        throw new SqlRunnerException("未找到该代码值【" + code + "】对应的数据库类型.");
    }

}
