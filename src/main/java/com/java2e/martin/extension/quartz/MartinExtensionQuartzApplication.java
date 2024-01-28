package com.java2e.martin.extension.quartz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 狮少
 * @version 1.0
 * @date 2021/3/12
 * @describtion MartinExtensionQuartzApplication
 * @since 1.0
 */
@SpringBootApplication
@MapperScan("com.java2e.martin.extension.quartz.mapper")
public class MartinExtensionQuartzApplication {
    public static void main(String[] args) {
        SpringApplication.run(MartinExtensionQuartzApplication.class, args);
    }
}
