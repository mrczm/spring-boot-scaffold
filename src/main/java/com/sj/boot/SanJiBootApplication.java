package com.sj.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangrd
 * @date 2019/1/8
 **/
@RestController
@SpringBootApplication
@EnableJpaAuditing
public class SanJiBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SanJiBootApplication.class, args);
    }
}
