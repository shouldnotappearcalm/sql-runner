package com.calm.sqlrunner.service;

import com.calm.sqlrunner.bean.dto.DatabaseInfoDto;
import com.calm.sqlrunner.bean.dto.SqlInfoDto;
import com.calm.sqlrunner.bean.pojo.DatabaseInfo;
import com.calm.sqlrunner.bean.vo.SqlExecResultVo;
import com.calm.sqlrunner.exception.SqlRunnerException;
import com.calm.sqlrunner.repository.DatabaseInfoRepository;
import com.calm.sqlrunner.strategy.DbSqlRunnerContext;
import com.calm.sqlrunner.bean.query.DatabaseInfoVo;
import com.calm.sqlrunner.bean.query.PageResponseVo;
import com.calm.sqlrunner.enums.DatabaseTypeEnum;
import com.calm.sqlrunner.utils.StrKit;
import com.calm.sqlrunner.utils.UserKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * 数据库操作相关服务类.
 *
 * @author gaozhirong on 2020/02/04
 * @version 1.0.0
 */
@Slf4j
@Service
public class DatabaseService {

    @Resource
    private DbSqlRunnerContext dbSqlRunnerContext;

    @Resource
    private DatabaseInfoRepository databaseInfoRepository;

    /**
     * 尝试连接数据库.
     * @param databaseInfoDto 数据库连接信息传输对象
     * @return 是否连接成功
     */
    public Mono<Void> tryConnectDatabase(DatabaseInfoDto databaseInfoDto) {
        try {
            return Mono.just(dbSqlRunnerContext.connect(databaseInfoDto))
                    .doOnNext(isSuccess -> {
                        if (Boolean.FALSE.equals(isSuccess)) {
                            throw new SqlRunnerException(StrKit.format("连接数据库失败，数据库信息：【{}】",
                                    databaseInfoDto));
                        }
                    })
                    .then();
        } catch (Exception e) {
            throw new SqlRunnerException(StrKit.format("连接数据库失败，数据库信息：【{}】", databaseInfoDto), e);
        }
    }

    /**
     * 根据传输对象的数据库信息执行 sql
     * @param sqlInfoDto sql 信息传输对象
     * @return sql执行结果值对象
     */
    public Mono<SqlExecResultVo> execSql(SqlInfoDto sqlInfoDto) {
        try {
            return Mono.just(dbSqlRunnerContext.execSql(sqlInfoDto, DatabaseTypeEnum.findByCode(
                    sqlInfoDto.getDatabaseInfoDto().getDatabaseType())));
        } catch (Exception e) {
            throw new SqlRunnerException(StrKit.format("数据库【{}】执行sql语句【{}】失败",
                    sqlInfoDto.getDatabaseInfoDto(), sqlInfoDto.getSql()), e);
        }
    }

    /**
     * 根据查询条件查询数据库列表.
     * @param databaseInfoVo 数据库筛选信息对象
     * @return 页面响应对象
     */
    public Mono<PageResponseVo> listDatabase(DatabaseInfoVo databaseInfoVo) {
        return UserKit.getCurUserInfo().map(userDetailsInfo -> {
            DatabaseInfo databaseInfo = new DatabaseInfo();
            BeanUtils.copyProperties(databaseInfoVo, databaseInfo);
            // 设置当前用户 id
            databaseInfo.setUserId(userDetailsInfo.getId());
            return new PageResponseVo(databaseInfoRepository.findAll(Example.of(databaseInfo),
                    PageRequest.of(databaseInfoVo.getPage(), databaseInfoVo.getLimit(), Sort.by("updateTime").descending())));
        }).cast(PageResponseVo.class);
    }

    /**
     * 根据数据库 id 查找数据库信息.
     * @param id 数据库记录 id
     * @return 数据库信息对象
     */
    public Mono<DatabaseInfo> getDatabaseInfoById(Long id) {
        return UserKit.getCurUserInfo()
                .flatMap(userDetailsInfo ->
                        Mono.justOrEmpty(databaseInfoRepository.findByIdAndUserId(id, userDetailsInfo.getId())));
    }

    /**
     * 插入数据库连接记录.
     * @param databaseInfo 数据库连接信息
     * @return 无
     */
    public Mono<Void> insertDatabaseInfo(DatabaseInfo databaseInfo) {
        return UserKit.getCurUserInfo().doOnNext(userDetailsInfo -> {
            tryConnectDatabase(databaseInfo);
            databaseInfo.setUserId(userDetailsInfo.getId());
            databaseInfoRepository.save(databaseInfo);
        }).then();
    }

    /**
     * 更新数据库连接记录.
     * @param databaseInfo 数据库连接信息
     * @return 无
     */
    public Mono<Void> updateDatabaseInfo(DatabaseInfo databaseInfo) {
        return UserKit.getCurUserInfo()
                .filter(userDetailsInfo -> userDetailsInfo.getId().equals(databaseInfo.getUserId()))
                .doOnNext(userDetailsInfo -> {
                    tryConnectDatabase(databaseInfo);
                    databaseInfoRepository.save(databaseInfo);
                })
                .then();
    }

    /**
     * 根据主键　id　删除数据库连接信息记录.
     * @param id 数据库连接信息　id
     * @return 无
     */
    public Mono<Void> deleteDatabaseInfo(Long id) {
        return UserKit.getCurUserInfo()
                .filter(userDetailsInfo -> {
                    DatabaseInfo originDatabaseInfo = databaseInfoRepository.findById(id)
                            .orElseThrow(() -> new SqlRunnerException("找不到对应的数据库库连接记录"));
                    return userDetailsInfo.getId().equals(originDatabaseInfo.getUserId());
                })
                .doOnNext(userDetailsInfo -> databaseInfoRepository.deleteById(id))
                .then();
    }

    /**
     * 获取当前用户的所有数据库连接信息.
     * @return 数据库连接信息列表
     */
    public Flux<DatabaseInfo> listAllDatabaseInfo() {
        return UserKit.getCurUserInfo()
                .flatMapIterable(userDetailsInfo -> databaseInfoRepository.findAllByUserId(userDetailsInfo.getId()));
    }


    /**
     * 尝试连接数据库.
     * @param databaseInfo 数据库连接信息对象
     */
    private void tryConnectDatabase(DatabaseInfo databaseInfo) {
        DatabaseInfoDto databaseInfoDto = new DatabaseInfoDto();
        BeanUtils.copyProperties(databaseInfo, databaseInfoDto);
        tryConnectDatabase(databaseInfoDto);
    }

}
