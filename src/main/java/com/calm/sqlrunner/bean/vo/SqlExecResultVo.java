package com.calm.sqlrunner.bean.vo;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * sql执行后的结果值对象.
 *
 * @author gaozhirong on 2020/02/04
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@ApiModel("sql执行结果的值对象")
public class SqlExecResultVo {

    @ApiModelProperty("查询/影响 的行数")
    private int row;

    @ApiModelProperty("查询到的 json 数组")
    private JSONArray dataArray;

}
