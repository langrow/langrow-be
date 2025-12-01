/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.diary.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DiaryCreateRequest", description = "일기 작성 요청")
public record DiaryCreateRequest(
    @NotBlank
        @Size(max = 10000)
        @Schema(description = "일기 본문", example = "오늘은 문장별 댓글이 달리는 일기를 시작했다.")
        String content,
    @Schema(description = "일기 날짜(없으면 서버에서 오늘 날짜로 저장)", example = "2025-12-02")
        LocalDate entryDate) {}
