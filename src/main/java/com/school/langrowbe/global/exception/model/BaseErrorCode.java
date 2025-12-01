/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.global.exception.model;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

  String getCode();

  String getMessage();

  HttpStatus getStatus();
}
