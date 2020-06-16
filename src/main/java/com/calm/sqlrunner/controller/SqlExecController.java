package com.calm.sqlrunner.controller;

import com.calm.sqlrunner.bean.dto.SqlInfoDto;
import com.calm.sqlrunner.bean.vo.SqlExecResultVo;
import com.calm.sqlrunner.consts.Const;
import com.calm.sqlrunner.service.DatabaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * sql执行接口类.
 *
 * @author gaozhirong on 2020/02/04
 * @version 1.0.0
 */
@CrossOrigin
@RestController
@Api(tags = "sql 接口类")
@RequestMapping(Const.PREFIX_V1 + "/sql")
public class SqlExecController {

    @Resource
    private DatabaseService databaseService;

    /**
     * 根据传输对象信息执行 sql，并且返回执行结果
     * @param sqlInfoDto sql 信息传输对象
     * @return 执行结果值对象
     */
    @PostMapping("/actions/exec")
    @ApiOperation("执行提交的 sql 对象")
    public Mono<SqlExecResultVo> execSql(@Validated @RequestBody SqlInfoDto sqlInfoDto) {
        return databaseService.execSql(sqlInfoDto);
    }

}
