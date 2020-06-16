package com.calm.sqlrunner.controller;

import com.calm.sqlrunner.bean.dto.DatabaseInfoDto;
import com.calm.sqlrunner.bean.pojo.DatabaseInfo;
import com.calm.sqlrunner.bean.validate.Insert;
import com.calm.sqlrunner.bean.validate.Update;
import com.calm.sqlrunner.bean.query.DatabaseInfoVo;
import com.calm.sqlrunner.bean.query.PageResponseVo;
import com.calm.sqlrunner.consts.Const;
import com.calm.sqlrunner.service.DatabaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * 数据库逻辑控制器类.
 *
 * @author gaozhirong on 2020/02/03
 * @version 1.0.0
 */
@CrossOrigin
@RestController
@Api(tags = "数据库接口类")
@RequestMapping(Const.PREFIX_V1 + "/database")
public class DatabaseController {

    @Resource
    private DatabaseService databaseService;

    /**
     * 测试数据库连接的正确性
     * @param databaseInfoDto 数据库传输对象
     * @return 无
     */
    @PostMapping("/actions/connect")
    @ApiOperation(value = "测试数据库连接正确性", response = Boolean.class)
    public Mono<Void> connectDatabase(@Validated @RequestBody DatabaseInfoDto databaseInfoDto) {
        return databaseService.tryConnectDatabase(databaseInfoDto);
    }

    /**
     * 根据数据库分页信息对象查询数据库列表.
     * @param databaseInfoVo 数据库分页信息查询对象
     * @return 数据库列表对象
     */
    @GetMapping
    @ApiOperation(value = "查询数据库列表", response = PageResponseVo.class)
    public Mono<PageResponseVo> listDatabase(DatabaseInfoVo databaseInfoVo) {
        return databaseService.listDatabase(databaseInfoVo);
    }

    /**
     * 根据 id 查找数据库记录id.
     * @param id 数据库记录 id
     * @return 数据库记录
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询数据库记录id", response = DatabaseInfo.class)
    public Mono<DatabaseInfo> getDatabaseInfoById(@PathVariable("id") Long id) {
        return databaseService.getDatabaseInfoById(id);
    }

    /**
     * 插入数据库连接记录.
     * @param databaseInfo 数据库连接信息
     * @return 无
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "插入数据库连接记录")
    public Mono<Void> insertDatabaseInfo(@Validated({Insert.class}) @RequestBody DatabaseInfo databaseInfo) {
        return databaseService.insertDatabaseInfo(databaseInfo);
    }

    /**
     * 更新数据库连接记录信息.
     * @param databaseInfo 数据库连接信息
     * @return 无
     */
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "更新数据库连接信息")
    public Mono<Void> updateDatabaseInfo(@Validated({Update.class}) @RequestBody DatabaseInfo databaseInfo) {
        return databaseService.updateDatabaseInfo(databaseInfo);
    }

    /**
     * 根据主键 id 删除数据库连接信息.
     * @param id 记录主键 id
     * @return 无
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("根据主键id删除数据库连接信息")
    public Mono<Void> deleteDatabaseInfo(@PathVariable("id") Long id) {
        return databaseService.deleteDatabaseInfo(id);
    }

    /**
     * 获取当前用户的所有数据库连接信息.
     * @return 连接信息列表
     */
    @GetMapping("/all")
    @ApiOperation("查询当前登录用户保存的所有数据库连接信息")
    public Flux<DatabaseInfo> listAllDatabaseInfo() {
        return databaseService.listAllDatabaseInfo();
    }

}
