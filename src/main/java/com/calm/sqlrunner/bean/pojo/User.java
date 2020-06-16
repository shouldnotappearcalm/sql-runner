package com.calm.sqlrunner.bean.pojo;

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

/**
 * 用户信息.
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
@ApiModel(description = "用户信息表")
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_user")
public class User extends BaseEntity {

    @ApiModelProperty("用户登录名")
    @NotBlank(message = "用户登录名不为空")
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "password")
    @ApiModelProperty("用户密码")
    @NotBlank(message = "用户密码不为空")
    private String password;

    @ApiModelProperty("账户是否未过期")
    @Column(name = "account_non_expired")
    private boolean accountNonExpired = true;

    @ApiModelProperty("账户是否未锁定")
    @Column(name = "account_non_locked")
    private boolean accountNonLocked= true;

    @ApiModelProperty("凭证是否未过期")
    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired= true;

    @ApiModelProperty("账户是否可用")
    @Column(name = "enabled")
    private boolean enabled= true;

}
