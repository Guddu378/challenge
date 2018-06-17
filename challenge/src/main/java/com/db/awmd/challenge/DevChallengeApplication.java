package com.db.awmd.challenge;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SpringBootApplication
public class DevChallengeApplication {
    public static void main(String[] args) {
        SpringApplication.run(DevChallengeApplication.class, args);
    }
}
