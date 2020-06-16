package com.calm.sqlrunner.bean.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 数据库信息查询值对象.
 *
 * @author gaozhirong on 2020/02/06
 * @version 1.0.0
 */
@Getter
@Setter
@ApiModel("数据库信息查询对象")
public class DatabaseInfoVo extends PageBase {

    @ApiModelProperty("自定义数据库名")
    private String name;

    @ApiModelProperty("数据库类型")
    private String databaseType;

    @ApiModelProperty("数据库ip地址")
    private String databaseHost;

    @ApiModelProperty("连接的数据库")
    private String databaseName;

}
