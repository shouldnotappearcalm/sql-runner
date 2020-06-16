package com.calm.sqlrunner.repository;

import com.calm.sqlrunner.bean.pojo.DatabaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 数据库信息持久化层.
 *
 * @author gaozhirong on 2020/02/05
 * @version 1.0.0
 */
public interface DatabaseInfoRepository extends JpaRepository<DatabaseInfo, Long> {

    /**
     * 根据数据库 id 和用户 id 查找数据库连接信息对象.
     * @param id 数据库 id
     * @param userId 用户 id
     * @return 数据库连接信息
     */
    Optional<DatabaseInfo> findByIdAndUserId(Long id, Long userId);

    /**
     * 根绝 userId 查询所有的数据库连接信息记录.
     * @param userId 用户 id
     * @return 数据库连接信息列表
     */
    List<DatabaseInfo> findAllByUserId(Long userId);

}
