/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.global.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerRedirectController {

  @GetMapping("/")
  public String redirectToSwaggerUi() {
    return "redirect:/swagger-ui/index.html";
  }
}
