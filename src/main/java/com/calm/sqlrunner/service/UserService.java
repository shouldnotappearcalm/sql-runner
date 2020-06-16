package com.calm.sqlrunner.service;

import com.calm.sqlrunner.bean.pojo.User;
import com.calm.sqlrunner.exception.SqlRunnerException;
import com.calm.sqlrunner.repository.UserRepository;
import com.calm.sqlrunner.utils.StrKit;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * 用户相关逻辑操作类.
 *
 * @author gaozhirong on 2020/02/07
 * @version 1.0.0
 */
@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 创建用户.
     *
     * @param user 用户信息
     * @return 无内容
     */
    public Mono<Void> createUser(User user) {
        return Mono.just(user)
                .doOnNext(userObj -> userRepository.findByUserName(user.getUserName()).ifPresent(databaseUser -> {
                    throw new SqlRunnerException(StrKit.format("用户【{}】已存在", databaseUser.getUserName()));
                }))
                .doOnNext(userObj -> {
                    userObj.setPassword(passwordEncoder.encode(userObj.getPassword()));
                    userRepository.save(userObj);
                }).then();
    }

}
