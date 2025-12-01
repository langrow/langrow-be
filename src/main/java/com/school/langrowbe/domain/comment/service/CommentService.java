/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.comment.service;

import java.util.List;

import com.school.langrowbe.domain.comment.dto.request.CommentCreateRequest;
import com.school.langrowbe.domain.comment.dto.request.CommentUpdateRequest;
import com.school.langrowbe.domain.comment.dto.response.CommentResponse;
import com.school.langrowbe.domain.diary.dto.response.DiaryDetailResponse;

public interface CommentService {

  /**
   * 댓글을 작성합니다.
   *
   * <p>검증: 일기 존재/접근 권한, 친구 여부, sentenceIndex(0 이상), 내용 필수.
   *
   * @param diaryId 댓글을 달 일기 ID
   * @param request 문장 인덱스 및 내용
   * @return 생성된 댓글 정보
   */
  CommentResponse create(Long diaryId, CommentCreateRequest request);

  DiaryDetailResponse getWithComments(Long diaryId, Integer sentenceIndex); // ← 追加

  /**
   * 댓글 목록을 조회합니다.
   *
   * <p>- {@code sentenceIndex}가 {@code null}이면 해당 일기의 <b>전체 댓글</b>을 반환합니다.<br>
   * - 값이 있으면 해당 문장 인덱스의 댓글만 반환합니다.<br>
   * - 기본 정렬: 문장 인덱스 오름차순, 생성 시각 오름차순.
   *
   * @param diaryId 일기 ID
   * @param sentenceIndex 특정 문장 인덱스(선택)
   * @return 댓글 리스트
   */
  List<CommentResponse> list(Long diaryId, Integer sentenceIndex);

  /**
   * 댓글을 수정합니다(작성자 본인).
   *
   * @param commentId 수정할 댓글 ID
   * @param request 수정 내용
   * @return 수정된 댓글 정보
   */
  CommentResponse update(Long commentId, CommentUpdateRequest request);

  /**
   * 댓글을 삭제합니다(작성자 본인).
   *
   * @param commentId 삭제할 댓글 ID
   */
  void delete(Long commentId);
}
