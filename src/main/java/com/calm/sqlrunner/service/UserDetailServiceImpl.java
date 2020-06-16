package com.calm.sqlrunner.service;

import com.calm.sqlrunner.bean.pojo.User;
import com.calm.sqlrunner.bean.pojo.UserDetailsInfo;
import com.calm.sqlrunner.exception.SqlRunnerException;
import com.calm.sqlrunner.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * 用户相关service.
 *
 * @author gaozhirong on 2020/02/05
 * @version 1.0.0
 */
@Configuration
public class UserDetailServiceImpl implements ReactiveUserDetailsService {

    @Resource
    private UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        User user = userRepository.findByUserName(username).orElseThrow(() -> new SqlRunnerException("找不到对应的用户"));
        return Mono.just(new UserDetailsInfo(user.getId(), user.getUserName(), user.getPassword(), new ArrayList<>()));
    }

}
