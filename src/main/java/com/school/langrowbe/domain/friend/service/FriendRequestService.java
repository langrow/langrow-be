/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.friend.service;

import java.util.List;

import com.school.langrowbe.domain.friend.dto.request.FriendRequestCreateRequest;
import com.school.langrowbe.domain.friend.dto.response.FriendRequestResponse;

public interface FriendRequestService {

  /**
   * 친구 요청을 생성합니다.
   *
   * <p>검증: 자기 자신 요청 불가, 이미 친구 상태 불가, 대기 중 요청 중복 불가(양방향).
   *
   * @param request 수신자 ID를 포함한 생성 요청
   * @return 생성된 친구 요청 정보(PENDING)
   */
  FriendRequestResponse create(FriendRequestCreateRequest request);

  /**
   * 현재 로그인 사용자가 <b>받은</b> 친구 요청 중 대기(PENDING) 목록을 조회합니다.
   *
   * @return 받은 대기 요청 리스트
   */
  List<FriendRequestResponse> listInboundPending();

  /**
   * 현재 로그인 사용자가 <b>보낸</b> 친구 요청 중 대기(PENDING) 목록을 조회합니다.
   *
   * @return 보낸 대기 요청 리스트
   */
  List<FriendRequestResponse> listOutboundPending();

  /**
   * 친구 요청을 수락합니다(수신자 전용).
   *
   * <p>처리: 상태를 ACCEPTED로 변경, Friendship 생성(없을 경우), 양측 {@code friendCount++}.
   *
   * @param requestId 수락할 친구 요청 ID
   * @return 수락된 요청 정보(ACCEPTED)
   */
  FriendRequestResponse accept(Long requestId);

  /**
   * 친구 요청을 거절합니다(수신자 전용).
   *
   * <p>처리: 상태를 REJECTED로 변경.
   *
   * @param requestId 거절할 친구 요청 ID
   * @return 거절된 요청 정보(REJECTED)
   */
  FriendRequestResponse reject(Long requestId);

  /**
   * 보낸 친구 요청을 취소합니다(발신자 전용).
   *
   * <p>처리: 요청 엔티티 삭제(상태가 PENDING인 경우에만).
   *
   * @param requestId 취소할 친구 요청 ID
   */
  void cancel(Long requestId);
}
