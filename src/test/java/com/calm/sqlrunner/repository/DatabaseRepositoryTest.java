package com.calm.sqlrunner.repository;

import com.calm.sqlrunner.bean.dto.DatabaseInfoDto;
import com.calm.sqlrunner.bean.pojo.DatabaseInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * database dao 测试.
 *
 * @author gaozhirong on 2020-02-11
 **/
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class DatabaseRepositoryTest {

    @Resource
    private DatabaseInfoRepository databaseInfoRepository;

    @Test
    public void selectDatabaseInfo() {
        DatabaseInfo databaseInfo = databaseInfoRepository.findById(55L).get();
        DatabaseInfoDto databaseInfoDto = new DatabaseInfoDto();
        BeanUtils.copyProperties(databaseInfo, databaseInfoDto);

        log.info("hashCode: {}", databaseInfoDto.hashCode());
    }

    @Test
    public void selectDatabaseInfo2() {
        DatabaseInfo databaseInfo = databaseInfoRepository.findById(57L).get();
        DatabaseInfoDto databaseInfoDto = new DatabaseInfoDto();
        BeanUtils.copyProperties(databaseInfo, databaseInfoDto);

        log.info("hashCode: {}", databaseInfoDto.hashCode());
    }

}
