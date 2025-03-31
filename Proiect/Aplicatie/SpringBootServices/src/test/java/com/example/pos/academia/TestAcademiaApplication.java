package com.example.pos.academia;

import org.springframework.boot.SpringApplication;
import org.testcontainers.utility.TestcontainersConfiguration;

public class TestAcademiaApplication {

    public static void main(String[] args) {
        SpringApplication.from(AcademiaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
