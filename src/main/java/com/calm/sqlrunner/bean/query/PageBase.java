package com.calm.sqlrunner.bean.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;

/**
 * 基础公用的分页对象.
 *
 * @author gaozhirong on 2020/2/6
 **/
@Getter
@ApiModel(value = "分页信息对象", description = "页面显示分页信息组装对象")
public class PageBase {

    /**
     * 每页条数.
     */
    @Setter
    @ApiModelProperty(value = "分页条数", example = "10")
    private Integer limit;

    /**
     * 当前页.
     */
    @Setter
    @ApiModelProperty(value = "当前页", example = "1")
    private Integer offset;

    /**
     * 计算页面需要显示的总页数.
     * @return 显示的总页数
     */
    @ApiOperation(value = "总页数")
    public Integer getPage() {
        return this.getOffset() / this.getLimit();
    }

}
