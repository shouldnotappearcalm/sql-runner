package com.calm.sqlrunner.repository;

import com.calm.sqlrunner.bean.pojo.DatabaseInfo;
import com.calm.sqlrunner.bean.pojo.User;
import com.calm.sqlrunner.enums.DatabaseTypeEnum;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserRepositoryTest {

    @Resource
    private UserRepository userRepository;

    @Resource
    private DatabaseInfoRepository databaseInfoRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertUser() {
        User user = new User();
        user.setUserName("gzr").setPassword(passwordEncoder.encode("6789@jkl"));
        userRepository.save(user);
    }

    @Test
    public void updateDatabaseInfo() {
        DatabaseInfo databaseInfo = new DatabaseInfo()
                .setName("测试2")
                .setDatabaseHost("172.25.17.72").setDatabasePort("6543").setDatabasePassword("6789@jkl")
                .setDatabaseName("db_lottery").setDatabaseType(DatabaseTypeEnum.POSTGRESQL.getCode())
                .setDatabaseUser("sa").setUserId(1L);
        databaseInfo.setId(5L);

        databaseInfoRepository.save(databaseInfo);
    }

    @Test
    public void batchInsertDatabaseInfo() {
        List<DatabaseInfo> databaseInfoList = new ArrayList<>();
        for (int i = 26; i <= 50; i++) {
            DatabaseInfo databaseInfo = new DatabaseInfo()
                    .setName("测试" + i)
                    .setDatabaseHost("172.25.17.72").setDatabasePort("6543").setDatabasePassword("6789@jkl")
                    .setDatabaseName("db_lottery").setDatabaseType(DatabaseTypeEnum.POSTGRESQL.getCode())
                    .setDatabaseUser("sa").setUserId(2L);

            databaseInfoList.add(databaseInfo);
        }



        databaseInfoRepository.saveAll(databaseInfoList);
    }

    @Test
    public void insertDatabaseInfo() {
        DatabaseInfo databaseInfo = new DatabaseInfo()
                .setDatabaseHost("172.25.17.73").setDatabasePort("6543").setDatabasePassword("6789@jkl")
                .setDatabaseName("db_lottery").setDatabaseType(DatabaseTypeEnum.POSTGRESQL.getCode())
                .setDatabaseUser("sa").setUserId(4L).setName("测试2");

        databaseInfoRepository.save(databaseInfo);
    }

}