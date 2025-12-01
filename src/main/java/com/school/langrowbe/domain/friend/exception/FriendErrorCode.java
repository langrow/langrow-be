/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.friend.exception;

import org.springframework.http.HttpStatus;

import com.school.langrowbe.global.exception.model.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FriendErrorCode implements BaseErrorCode {
  SELF_REQUEST_NOT_ALLOWED("FRIEND_4001", "자기 자신에게는 친구 요청을 보낼 수 없습니다.", HttpStatus.BAD_REQUEST),
  FRIEND_ALREADY_EXISTS("FRIEND_4002", "이미 친구 상태입니다.", HttpStatus.BAD_REQUEST),
  REQUEST_ALREADY_EXISTS("FRIEND_4003", "대기 중인 친구 요청이 이미 존재합니다.", HttpStatus.BAD_REQUEST),
  REQUEST_NOT_FOUND("FRIEND_4041", "존재하지 않거나 접근 권한이 없는 친구 요청입니다.", HttpStatus.NOT_FOUND),
  INVALID_STATUS("FRIEND_4004", "처리할 수 없는 친구 요청 상태입니다.", HttpStatus.BAD_REQUEST);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
