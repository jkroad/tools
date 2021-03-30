package com.ismayfly.coins.tools;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.ismayfly.coins.tools.mapper")
@SpringBootApplication
public class ToolsApplication {
    public static void main(String[] args) {
        Thread thread = new Thread();
        SpringApplication.run(ToolsApplication.class, args);
    }

}
