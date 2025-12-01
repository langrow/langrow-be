/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.diary.dto.response;

import java.util.List;

import com.school.langrowbe.domain.comment.dto.response.CommentResponse;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DiaryDetailResponse", description = "일기 + 댓글 목록 응답")
public record DiaryDetailResponse(
    @Schema(description = "일기 기본 정보") DiaryResponse diary,
    @Schema(description = "댓글 목록(문장 인덱스/작성 시각 기준 정렬)") List<CommentResponse> comments) {}
