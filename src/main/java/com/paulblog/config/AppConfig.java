package com.paulblog.config;

import java.util.Base64;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author : paulkim
 * @description :
 * @packageName : com.paulblog.config
 * @fileName : AppConfig
 * @date : 2025-03-30
 */


@ConfigurationProperties(prefix = "paul")
@Getter
public class AppConfig {
    private byte[] jwtKey;

    public void setJwtKey(String jwtKey) {
        this.jwtKey = Base64.getDecoder().decode(jwtKey);
    }
}
