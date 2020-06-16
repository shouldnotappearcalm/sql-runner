package com.calm.sqlrunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

/**
 * sql执行器启动类.
 *
 * @author gaozhirong on 2020/2/3
 */
@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class SqlRunnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SqlRunnerApplication.class, args);
	}



}
