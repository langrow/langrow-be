/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.user.mapper;

import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.stereotype.Component;

import com.school.langrowbe.domain.user.dto.request.InfoRequest;
import com.school.langrowbe.domain.user.dto.response.UserResponse;
import com.school.langrowbe.domain.user.entity.User;

@Component
public class UserMapper {

  /**
   * 회원가입 요청 DTO와 인코딩된 비밀번호를 기반으로 User 엔티티를 생성합니다. PUT/가입 모두 '전체 값 제공'을 전제로 하므로 null 검사를 하지 않습니다.
   */
  public User toUser(InfoRequest request, String encodedPassword) {
    return User.builder()
        .loginId(request.loginId())
        .password(encodedPassword)
        .name(request.name())
        .nativeLanguage(request.nativeLanguage())
        .learningLanguage(request.learningLanguage())
        .level(request.level())
        .interests(new HashSet<>(request.interests()))
        .bio(request.bio())
        .diaryCount(0)
        .streakCount(0)
        .friendCount(0)
        .build();
  }

  /** 마이페이지 수정(PUT) 요청을 기존 User 엔티티에 그대로 반영합니다. 전체 갱신 전제이므로 null 체크 없이 덮어씁니다. */
  public void applyProfileUpdateStrict(User user, InfoRequest request) {
    user.updateProfile(
        request.name(),
        request.nativeLanguage(),
        request.learningLanguage(),
        request.level(),
        request.interests(),
        request.bio());
  }

  /** User 엔티티를 사용자 응답 DTO로 변환합니다. */
  public UserResponse toResponse(User user) {
    return new UserResponse(
        user.getId(),
        user.getLoginId(),
        user.getName(),
        user.getNativeLanguage(),
        user.getLearningLanguage(),
        user.getLevel(),
        new ArrayList<>(user.getInterests()),
        user.getBio(),
        user.getDiaryCount(),
        user.getStreakCount(),
        user.getFriendCount());
  }
}
