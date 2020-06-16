package com.calm.sqlrunner.bean.dto;

import dm.jdbc.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;

/**
 * 数据库传输对象.
 *
 * @author gaozhirong on 2020/02/03
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@ApiModel("数据库信息传输对象")
public class DatabaseInfoDto {

    @ApiModelProperty("数据库类型")
    @NotBlank(message = "数据库类型不为空")
    private String databaseType;

    @ApiModelProperty("数据库ip地址")
    @NotBlank(message = "数据库ip地址不为空")
    private String databaseHost;

    @ApiModelProperty("数据库端口")
    @NotBlank(message = "数据库端口不为空")
    private String databasePort;

    @ApiModelProperty("数据库用户")
    @NotBlank(message = "数据库用户不为空")
    private String databaseUser;

    @ApiModelProperty("数据库密码")
    @NotBlank(message = "数据库密码不为空")
    private String databasePassword;

    @ApiModelProperty("连接的数据库")
    @NotBlank(message = "连接数据库名不为空")
    private String databaseName;

    @ApiModelProperty("项目id")
    private String projectId;

    /**
     * 获取数据库传输对象信息唯一标识.
     * @return 唯一标识
     */
    public String getUniqueIdentification() {
        return String.valueOf(this.hashCode());
    }

}
