package com.calm.sqlrunner.repository;

import com.calm.sqlrunner.bean.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 用户持久化层.
 *
 * @author gaozhirong on 2020/02/05
 * @version 1.0.0
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 根据用户名查找用户.
     * @param userName 用户名
     * @return 用户信息对象
     */
    Optional<User> findByUserName(String userName);

}
