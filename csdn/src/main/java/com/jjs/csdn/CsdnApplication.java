package com.jjs.csdn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jjs.csdn.mapper")
public class CsdnApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsdnApplication.class, args);
    }
}
