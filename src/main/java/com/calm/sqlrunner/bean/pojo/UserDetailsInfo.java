package com.calm.sqlrunner.bean.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 用户信息传输对象.
 *
 * @author gaozhirong on 2020/02/06
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@ApiModel("用户信息传输对象")
public class UserDetailsInfo extends User {

    @ApiModelProperty("用户表主键信息")
    private Long id;

    /**
     * 构造函数.
     * @param id 用户表主键信息
     * @param username 用户名
     * @param password 用户密码
     * @param authorities 权限列表
     */
    public UserDetailsInfo(Long id, String username, String password,Collection<? extends GrantedAuthority>
            authorities) {
        super(username, password, authorities);
        this.id = id;
    }

}
