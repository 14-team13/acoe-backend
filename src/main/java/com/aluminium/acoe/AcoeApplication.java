package com.aluminium.acoe;

import com.aluminium.acoe.sys.config.properties.AppProperties;
import com.aluminium.acoe.sys.config.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AppProperties.class, CorsProperties.class})
public class AcoeApplication {
    public static void main(String[] args) {
        SpringApplication.run(AcoeApplication.class, args);
    }
}
