package com.calm.sqlrunner.controller;

import com.calm.sqlrunner.bean.pojo.User;
import com.calm.sqlrunner.bean.pojo.UserDetailsInfo;
import com.calm.sqlrunner.consts.Const;
import com.calm.sqlrunner.service.UserService;
import com.calm.sqlrunner.utils.UserKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * 用户接口.
 *
 * @author gaozhirong on 2020/02/06
 * @version 1.0.0
 */
@CrossOrigin
@RestController
@Api(tags = "用户相关接口")
@RequestMapping(Const.PREFIX_V1 + "/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 获取用户信息的接口
     * @return 用户信息对象
     */
    @GetMapping("/info")
    @ApiModelProperty("获取当前登陆用户信息")
    public Mono<UserDetailsInfo> userInfo() {
        return UserKit.getCurUserInfo();
    }

    /**
     * 创建用户.
     * @param user 用户信息
     * @return 无内容返回值
     */
    @PostMapping
    @ApiOperation("创建用户信息")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createUser(@Validated @RequestBody User user) {
        return userService.createUser(user);
    }

}
