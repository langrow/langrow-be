/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.friend.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.langrowbe.domain.friend.dto.request.FriendRequestCreateRequest;
import com.school.langrowbe.domain.friend.dto.response.FriendRequestResponse;
import com.school.langrowbe.domain.friend.service.FriendRequestService;
import com.school.langrowbe.global.response.BaseResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Friend", description = "친구 관련 API")
@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendRequestController {

  private final FriendRequestService friendRequestService;

  @Operation(summary = "친구 요청 보내기")
  @PostMapping
  public ResponseEntity<BaseResponse<FriendRequestResponse>> create(
      @RequestBody @Valid FriendRequestCreateRequest request) {
    return ResponseEntity.ok(BaseResponse.success(friendRequestService.create(request)));
  }

  @Operation(summary = "받은 친구 요청 목록")
  @GetMapping("/inbound")
  public ResponseEntity<BaseResponse<List<FriendRequestResponse>>> inbound() {
    return ResponseEntity.ok(BaseResponse.success(friendRequestService.listInboundPending()));
  }

  @Operation(summary = "보낸 친구 요청 목록")
  @GetMapping("/outbound")
  public ResponseEntity<BaseResponse<List<FriendRequestResponse>>> outbound() {
    return ResponseEntity.ok(BaseResponse.success(friendRequestService.listOutboundPending()));
  }

  @Operation(summary = "친구 요청 수락")
  @PostMapping("/{requestId}/accept")
  public ResponseEntity<BaseResponse<FriendRequestResponse>> accept(@PathVariable Long requestId) {
    return ResponseEntity.ok(BaseResponse.success(friendRequestService.accept(requestId)));
  }

  @Operation(summary = "친구 요청 거절")
  @PostMapping("/{requestId}/reject")
  public ResponseEntity<BaseResponse<FriendRequestResponse>> reject(@PathVariable Long requestId) {
    return ResponseEntity.ok(BaseResponse.success(friendRequestService.reject(requestId)));
  }

  @Operation(summary = "친구 요청 보낸사람으로써 취소")
  @DeleteMapping("/{requestId}")
  public ResponseEntity<BaseResponse<Void>> cancel(@PathVariable Long requestId) {
    friendRequestService.cancel(requestId);
    return ResponseEntity.ok(BaseResponse.success(null));
  }
}
