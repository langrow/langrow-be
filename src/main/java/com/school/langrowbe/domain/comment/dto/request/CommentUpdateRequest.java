/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CommentUpdateRequest", description = "댓글 수정 요청")
public record CommentUpdateRequest(
    @NotBlank @Size(max = 1000) @Schema(description = "수정할 댓글 내용", example = "표현을 조금 바꿔봤어요.")
        String content) {}
