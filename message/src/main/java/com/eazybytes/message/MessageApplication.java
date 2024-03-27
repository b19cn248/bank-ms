package com.eazybytes.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class MessageApplication {

  public static void main(String[] args) {
    SpringApplication.run(MessageApplication.class, args);
  }

}
