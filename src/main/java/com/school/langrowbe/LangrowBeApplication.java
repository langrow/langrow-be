/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LangrowBeApplication {

  public static void main(String[] args) {
    SpringApplication.run(LangrowBeApplication.class, args);
  }
}
