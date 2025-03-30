package com.paulblog;

import com.paulblog.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class PaulblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaulblogApplication.class, args);
    }
}
