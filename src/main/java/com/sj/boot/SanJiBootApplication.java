package com.sj.boot;

import com.sj.boot.modules.sys.model.Menu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.*;

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
