package com.calm.sqlrunner.bean.pojo;

import com.calm.sqlrunner.bean.validate.Insert;
import com.calm.sqlrunner.bean.validate.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 数据库信息对象.
 *
 * @author gaozhirong on 2020/02/05
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "数据库连接信息表")
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_database_info")
public class DatabaseInfo extends BaseEntity {

    @Column(name = "name", unique = true)
    @ApiModelProperty("连接名")
    @NotBlank(message = "连接名不为空", groups = {Insert.class, Update.class})
    private String name;

    @Column(name = "user_id")
    @NotNull(message = "用户不为空", groups = {Update.class})
    @ApiModelProperty("关联用户表主键")
    private Long userId;

    @Column(name = "database_type")
    @ApiModelProperty("数据库类型")
    @NotBlank(message = "数据库类型不为空", groups = {Insert.class, Update.class})
    private String databaseType;

    @Column(name = "database_host")
    @ApiModelProperty("数据库ip地址")
    @NotBlank(message = "数据库ip地址不为空", groups = {Insert.class, Update.class})
    private String databaseHost;

    @Column(name = "database_port")
    @ApiModelProperty("数据库端口")
    @NotBlank(message = "数据库端口不为空", groups = {Insert.class, Update.class})
    private String databasePort;

    @Column(name = "database_user")
    @ApiModelProperty("数据库用户")
    @NotBlank(message = "数据库用户不为空", groups = {Insert.class, Update.class})
    private String databaseUser;

    @Column(name = "database_password")
    @ApiModelProperty("数据库密码")
    @NotBlank(message = "数据库密码不为空", groups = {Insert.class, Update.class})
    private String databasePassword;

    @Column(name = "database_name")
    @ApiModelProperty("连接的数据库")
    @NotBlank(message = "连接的数据库不为空", groups = {Insert.class, Update.class})
    private String databaseName;

}
