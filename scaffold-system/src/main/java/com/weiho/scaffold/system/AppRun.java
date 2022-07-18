package com.weiho.scaffold.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableCaching
@EnableAsync
@EnableSwagger2
@SpringBootApplication
@MapperScan(basePackages = {"com.weiho.scaffold.**.mapper"}) //Mybatis扫描Mapper
@ComponentScan(basePackages = {"com.weiho.scaffold"}) //扫描Spring Boot上下文的Bean
public class AppRun {

    public static void main(String[] args) {
        SpringApplication.run(AppRun.class, args);
    }

    static {
        //解决Druid连接池报警告“discard long time none received connection”
        System.setProperty("druid.mysql.usePingMethod", "false");
    }
}
