/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.diary.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DiaryResponse", description = "일기 응답")
public record DiaryResponse(
    Long id,
    Long userId,
    String content,
    LocalDate entryDate,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
