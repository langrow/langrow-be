/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.comment.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CommentCreateRequest", description = "댓글 작성 요청")
public record CommentCreateRequest(
    @Min(value = 0, message = "문장 인덱스는 0 이상이어야 합니다.")
        @Schema(description = "문장 인덱스(0-based)", example = "0")
        int sentenceIndex,
    @NotBlank @Size(max = 1000) @Schema(description = "댓글 내용", example = "이 문장이 정말 마음에 들어요.")
        String content) {}
