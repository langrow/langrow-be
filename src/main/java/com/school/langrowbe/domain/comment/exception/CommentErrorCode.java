/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.comment.exception;

import org.springframework.http.HttpStatus;

import com.school.langrowbe.global.exception.model.BaseErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentErrorCode implements BaseErrorCode {
  COMMENT_NOT_FOUND("COMMENT_4041", "존재하지 않는 댓글입니다.", HttpStatus.NOT_FOUND),
  NO_PERMISSION("COMMENT_4031", "해당 댓글에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN),
  NOT_FRIEND("COMMENT_4032", "친구 관계만 댓글을 작성할 수 있습니다.", HttpStatus.FORBIDDEN);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
