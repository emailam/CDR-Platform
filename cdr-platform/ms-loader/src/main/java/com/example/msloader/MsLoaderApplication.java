package com.example.msloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MsLoaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsLoaderApplication.class, args);
    }

}
