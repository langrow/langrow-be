/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.diary.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.langrowbe.domain.diary.dto.request.DiaryCreateRequest;
import com.school.langrowbe.domain.diary.dto.response.DiaryResponse;
import com.school.langrowbe.domain.diary.entity.Diary;
import com.school.langrowbe.domain.diary.exception.DiaryErrorCode;
import com.school.langrowbe.domain.diary.mapper.DiaryMapper;
import com.school.langrowbe.domain.diary.repository.DiaryRepository;
import com.school.langrowbe.domain.friend.service.FriendService;
import com.school.langrowbe.domain.user.entity.User;
import com.school.langrowbe.domain.user.exception.UserErrorCode;
import com.school.langrowbe.domain.user.repository.UserRepository;
import com.school.langrowbe.global.exception.CustomException;
import com.school.langrowbe.global.security.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DiaryServiceImpl implements DiaryService {

  private final DiaryRepository diaryRepository;
  private final UserRepository userRepository;
  private final DiaryMapper diaryMapper;
  private final FriendService friendService;

  @Override
  public DiaryResponse create(DiaryCreateRequest request) {
    Long userId = SecurityUtil.getCurrentUserId();
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    Diary diary = diaryMapper.toEntity(user, request);
    Diary saved = diaryRepository.save(diary);

    user.increaseDiaryCount();

    return diaryMapper.toResponse(saved);
  }

  @Override
  public DiaryResponse get(Long diaryId) {
    Long me = SecurityUtil.getCurrentUserId();

    Diary diary =
        diaryRepository
            .findById(diaryId)
            .orElseThrow(() -> new CustomException(DiaryErrorCode.DIARY_NOT_FOUND));

    Long ownerId = diary.getUser().getId();
    if (!ownerId.equals(me)) {
      boolean isFriend = friendService.areFriends(me, ownerId);
      boolean isToday = LocalDate.now().equals(diary.getEntryDate());

      if (!(isFriend && isToday)) {
        throw new CustomException(DiaryErrorCode.NO_PERMISSION);
      }
    }

    return diaryMapper.toResponse(diary);
  }

  @Override
  @Transactional(readOnly = true)
  public List<DiaryResponse> getMyDiaries() {
    Long userId = SecurityUtil.getCurrentUserId();
    return diaryMapper.toResponseList(diaryRepository.findByUser_IdOrderByCreatedAtDesc(userId));
  }

  @Override
  public void delete(Long diaryId) {
    Long userId = SecurityUtil.getCurrentUserId();
    Diary diary =
        diaryRepository
            .findByIdAndUser_Id(diaryId, userId)
            .orElseThrow(() -> new CustomException(DiaryErrorCode.DIARY_NOT_FOUND));

    diaryRepository.delete(diary);

    diary.getUser().decreaseDiaryCountSafely();
  }
}
