/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.school.langrowbe.domain.user.dto.request.InfoRequest;
import com.school.langrowbe.domain.user.dto.response.UserResponse;
import com.school.langrowbe.domain.user.service.UserService;
import com.school.langrowbe.global.response.BaseResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

  private final UserService userService;

  @Override
  public ResponseEntity<BaseResponse<UserResponse>> register(InfoRequest request) {
    UserResponse response = userService.register(request);
    return ResponseEntity.ok(BaseResponse.success("회원가입이 완료되었습니다.", response));
  }

  @Override
  public ResponseEntity<BaseResponse<UserResponse>> getMyInfo() {
    UserResponse response = userService.getMyInfo();
    return ResponseEntity.ok(BaseResponse.success("내 정보 조회에 성공했습니다.", response));
  }

  @Override
  public ResponseEntity<BaseResponse<UserResponse>> updateMyInfo(InfoRequest request) {
    UserResponse response = userService.updateMyInfo(request);
    return ResponseEntity.ok(BaseResponse.success(response));
  }

  @Override
  public ResponseEntity<BaseResponse<Void>> deleteMyAccount(
      HttpServletRequest request, HttpServletResponse response) {

    userService.deleteMyAccount(request, response);

    return ResponseEntity.ok(BaseResponse.success("회원 탈퇴가 완료되었습니다.", null));
  }
}
