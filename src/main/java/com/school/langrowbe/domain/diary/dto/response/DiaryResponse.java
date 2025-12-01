package com.school.langrowbe.domain.diary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(name = "DiaryResponse", description = "일기 응답")
public record DiaryResponse(
    Long id,
    Long userId,
    String content,
    LocalDate entryDate,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

}