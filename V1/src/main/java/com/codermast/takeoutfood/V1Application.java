package com.codermast.takeoutfood;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@Slf4j
@SpringBootApplication
@ServletComponentScan("com.codermast.takeoutfood.filter")
public class V1Application {
    public static void main(String[] args) {
        SpringApplication.run(V1Application.class, args);
        log.info("项目启动成功：V1版本");
    }

}
