package com.calm.sqlrunner.bean.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 执行的sql信息传输类.
 *
 * @author gaozhirong on 2020/02/04
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@ApiModel("sql信息传输对象")
public class SqlInfoDto {

    @NotBlank(message = "提交sql为空")
    @ApiModelProperty("需要执行的 sql 信息")
    private String sql;

    @Valid
    @NotNull(message = "数据库信息对象为空")
    @ApiModelProperty("关联的数据库信息传输类")
    private DatabaseInfoDto databaseInfoDto;

}
