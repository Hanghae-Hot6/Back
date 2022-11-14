package com.project.odok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OdokApplication {

    public static void main(String[] args) {
        SpringApplication.run(OdokApplication.class, args);
    }

}
