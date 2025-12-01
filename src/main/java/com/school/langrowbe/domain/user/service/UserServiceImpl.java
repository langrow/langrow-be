/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.langrowbe.domain.auth.service.AuthService;
import com.school.langrowbe.domain.user.dto.request.InfoRequest;
import com.school.langrowbe.domain.user.dto.response.UserResponse;
import com.school.langrowbe.domain.user.entity.User;
import com.school.langrowbe.domain.user.exception.UserErrorCode;
import com.school.langrowbe.domain.user.mapper.UserMapper;
import com.school.langrowbe.domain.user.repository.UserRepository;
import com.school.langrowbe.global.exception.CustomException;
import com.school.langrowbe.global.security.SecurityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final AuthService authService;

  @Override
  public UserResponse register(InfoRequest request) {
    // 로그인 아이디 중복 체크
    if (userRepository.existsByLoginId(request.loginId())) {
      log.warn("회원가입 실패 - 중복 loginId: {}", request.loginId());
      throw new CustomException(UserErrorCode.DUPLICATE_LOGIN_ID);
    }

    // 비밀번호 인코딩 후 엔티티 생성
    String encodedPassword = passwordEncoder.encode(request.password());
    User user = userMapper.toUser(request, encodedPassword);

    // 저장
    User saved = userRepository.save(user);
    log.info("회원가입 성공 - userId: {}, loginId: {}", saved.getId(), saved.getLoginId());

    return userMapper.toResponse(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public UserResponse getMyInfo() {
    Long currentUserId = SecurityUtil.getCurrentUserId();

    User user =
        userRepository
            .findById(currentUserId)
            .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    log.info("내 정보 조회 - userId: {}, loginId: {}", user.getId(), user.getLoginId());
    return userMapper.toResponse(user);
  }

  @Override
  public UserResponse updateMyInfo(InfoRequest request) {
    Long currentUserId = SecurityUtil.getCurrentUserId();

    User user =
        userRepository
            .findById(currentUserId)
            .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    // 전체 갱신 (null 체크 없이 덮어쓰기 전제)
    userMapper.applyProfileUpdateStrict(user, request);

    log.info("내 정보 수정(put) - userId: {}, loginId: {}", user.getId(), user.getLoginId());
    return userMapper.toResponse(user);
  }

  @Override
  public void deleteMyAccount(HttpServletRequest request, HttpServletResponse response) {
    Long currentUserId = SecurityUtil.getCurrentUserId();

    User user =
        userRepository
            .findById(currentUserId)
            .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    authService.invalidateCurrentSessionQuietly(request, response);

    // 삭제
    userRepository.delete(user);
    log.info("회원 탈퇴 완료 - userId: {}, loginId: {}", user.getId(), user.getLoginId());
  }
}
