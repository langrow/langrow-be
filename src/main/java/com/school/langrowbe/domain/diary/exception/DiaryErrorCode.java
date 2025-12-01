package com.school.langrowbe.domain.diary.exception;

import com.school.langrowbe.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DiaryErrorCode implements BaseErrorCode {
  DIARY_NOT_FOUND("DIARY_4041", "존재하지 않는 일기입니다.", HttpStatus.NOT_FOUND),
  NO_PERMISSION("DIARY_4031", "해당 일기에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN);

  private final String code;
  private final String message;
  private final HttpStatus status;
}