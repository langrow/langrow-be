/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.comment.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.school.langrowbe.domain.comment.dto.request.CommentCreateRequest;
import com.school.langrowbe.domain.comment.dto.request.CommentUpdateRequest;
import com.school.langrowbe.domain.comment.dto.response.CommentResponse;
import com.school.langrowbe.domain.comment.service.CommentService;
import com.school.langrowbe.global.response.BaseResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Comment", description = "댓글 관련 API(친구만 작성 가능)")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @Operation(summary = "댓글 작성(친구만)")
  @PostMapping("/diaries/{diaryId}/comments")
  public ResponseEntity<BaseResponse<CommentResponse>> create(
      @PathVariable Long diaryId, @RequestBody @Valid CommentCreateRequest request) {
    return ResponseEntity.ok(BaseResponse.success(commentService.create(diaryId, request)));
  }

  @Operation(summary = "댓글 목록 조회(전체 or 특정 문장)")
  @GetMapping("/diaries/{diaryId}/comments")
  public ResponseEntity<BaseResponse<List<CommentResponse>>> list(
      @PathVariable Long diaryId, @RequestParam(required = false) Integer sentenceIndex) {
    return ResponseEntity.ok(BaseResponse.success(commentService.list(diaryId, sentenceIndex)));
  }

  @Operation(summary = "댓글 수정(작성자)")
  @PutMapping("/comments/{commentId}")
  public ResponseEntity<BaseResponse<CommentResponse>> update(
      @PathVariable Long commentId, @RequestBody @Valid CommentUpdateRequest request) {
    return ResponseEntity.ok(BaseResponse.success(commentService.update(commentId, request)));
  }

  @Operation(summary = "댓글 삭제(작성자)")
  @DeleteMapping("/comments/{commentId}")
  public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long commentId) {
    commentService.delete(commentId);
    return ResponseEntity.ok(BaseResponse.success(null));
  }
}
