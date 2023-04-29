package com.aluminium.acoe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.aluminium.acoe.sys.config.properties"})
public class AcoeApplication {
    public static void main(String[] args) {
        SpringApplication.run(AcoeApplication.class, args);
    }
}
