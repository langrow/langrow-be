/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.diary.service;

import java.util.List;

import com.school.langrowbe.domain.diary.dto.request.DiaryCreateRequest;
import com.school.langrowbe.domain.diary.dto.response.DiaryResponse;

public interface DiaryService {

  /**
   * 새 일기를 작성합니다.
   *
   * <p>- 본문은 필수이며, {@code entryDate}가 없으면 서버에서 오늘 날짜로 저장합니다.<br>
   * - 성공 시 사용자 {@code diaryCount}를 1 증가시킵니다.
   *
   * @param request 일기 작성 요청(본문, 선택: 일기 날짜)
   * @return 생성된 일기 정보 응답
   */
  DiaryResponse create(DiaryCreateRequest request);

  /**
   * 일기 단건을 조회합니다.
   *
   * @param diaryId 조회할 일기의 식별자
   * @return 일기 정보 응답
   */
  DiaryResponse get(Long diaryId);

  /**
   * 현재 로그인한 사용자가 작성한 모든 일기를 조회합니다(비페이징).
   *
   * <p>- 정렬: 생성 시각 기준 내림차순(createdAt DESC).
   *
   * @return 내 일기 목록
   */
  List<DiaryResponse> getMyDiaries();

  /**
   * 일기를 삭제합니다.
   *
   * <p>- 소유자 본인만 삭제할 수 있습니다.<br>
   * - 성공 시 사용자 {@code diaryCount}를 1 감소시킵니다(0 미만으로 내려가지 않도록 처리).
   *
   * @param diaryId 삭제할 일기의 식별자
   */
  void delete(Long diaryId);
}
