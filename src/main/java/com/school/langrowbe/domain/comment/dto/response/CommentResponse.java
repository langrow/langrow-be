/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.comment.dto.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CommentResponse", description = "댓글 응답")
public record CommentResponse(
    Long id,
    Long diaryId,
    Long authorId,
    String authorName,
    int sentenceIndex,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
