/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.friend.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.school.langrowbe.domain.friend.dto.request.FriendRequestCreateRequest;
import com.school.langrowbe.domain.friend.dto.response.FriendRequestResponse;
import com.school.langrowbe.domain.friend.entity.FriendRequest;
import com.school.langrowbe.domain.friend.entity.FriendRequestStatus;
import com.school.langrowbe.domain.friend.entity.Friendship;
import com.school.langrowbe.domain.friend.exception.FriendErrorCode;
import com.school.langrowbe.domain.friend.mapper.FriendRequestMapper;
import com.school.langrowbe.domain.friend.repository.FriendRequestRepository;
import com.school.langrowbe.domain.friend.repository.FriendshipRepository;
import com.school.langrowbe.domain.user.entity.User;
import com.school.langrowbe.domain.user.exception.UserErrorCode;
import com.school.langrowbe.domain.user.repository.UserRepository;
import com.school.langrowbe.global.exception.CustomException;
import com.school.langrowbe.global.security.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendRequestServiceImpl implements FriendRequestService {

  private final FriendRequestRepository friendRequestRepository;
  private final FriendshipRepository friendshipRepository;
  private final UserRepository userRepository;
  private final FriendRequestMapper mapper;

  @Override
  public FriendRequestResponse create(FriendRequestCreateRequest request) {
    Long me = SecurityUtil.getCurrentUserId();
    Long to = request.toUserId();

    if (me.equals(to)) {
      throw new CustomException(FriendErrorCode.SELF_REQUEST_NOT_ALLOWED);
    }

    // 사용자 존재 확인
    User meUser =
        userRepository
            .findById(me)
            .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    userRepository
        .findById(to)
        .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    // 이미 친구인지
    Long u1 = Math.min(me, to);
    Long u2 = Math.max(me, to);
    if (friendshipRepository.existsByUser1IdAndUser2Id(u1, u2)) {
      throw new CustomException(FriendErrorCode.FRIEND_ALREADY_EXISTS);
    }

    // 대기 중 요청 중복 방지(양방향)
    if (friendRequestRepository.existsPendingEitherDirection(me, to)) {
      throw new CustomException(FriendErrorCode.REQUEST_ALREADY_EXISTS);
    }

    FriendRequest saved = friendRequestRepository.save(mapper.toEntity(me, request));
    return mapper.toResponse(saved);
  }

  @Override
  public List<FriendRequestResponse> listInboundPending() {
    Long me = SecurityUtil.getCurrentUserId();
    return mapper.toResponseList(
        friendRequestRepository.findByToUserIdAndStatus(me, FriendRequestStatus.PENDING));
  }

  @Override
  public List<FriendRequestResponse> listOutboundPending() {
    Long me = SecurityUtil.getCurrentUserId();
    return mapper.toResponseList(
        friendRequestRepository.findByFromUserIdAndStatus(me, FriendRequestStatus.PENDING));
  }

  @Override
  public FriendRequestResponse accept(Long requestId) {
    Long me = SecurityUtil.getCurrentUserId();

    FriendRequest req =
        friendRequestRepository
            .findByIdAndToUserId(requestId, me)
            .orElseThrow(() -> new CustomException(FriendErrorCode.REQUEST_NOT_FOUND));

    if (req.getStatus() != FriendRequestStatus.PENDING) {
      throw new CustomException(FriendErrorCode.INVALID_STATUS);
    }

    Long other = req.getFromUserId();

    // 이미 친구인지 재확인
    Long u1 = Math.min(me, other);
    Long u2 = Math.max(me, other);
    if (!friendshipRepository.existsByUser1IdAndUser2Id(u1, u2)) {
      friendshipRepository.save(Friendship.of(me, other));
      // 양쪽 friendCount 증가
      User meUser =
          userRepository
              .findById(me)
              .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
      User otherUser =
          userRepository
              .findById(other)
              .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
      meUser.increaseFriendCount();
      otherUser.increaseFriendCount();
    }

    req.accept(); // 상태 전환
    return mapper.toResponse(req);
  }

  @Override
  public FriendRequestResponse reject(Long requestId) {
    Long me = SecurityUtil.getCurrentUserId();

    FriendRequest req =
        friendRequestRepository
            .findByIdAndToUserId(requestId, me)
            .orElseThrow(() -> new CustomException(FriendErrorCode.REQUEST_NOT_FOUND));

    if (req.getStatus() != FriendRequestStatus.PENDING) {
      throw new CustomException(FriendErrorCode.INVALID_STATUS);
    }

    req.reject();
    return mapper.toResponse(req);
  }

  @Override
  public void cancel(Long requestId) {
    Long me = SecurityUtil.getCurrentUserId();

    FriendRequest req =
        friendRequestRepository
            .findByIdAndFromUserId(requestId, me)
            .orElseThrow(() -> new CustomException(FriendErrorCode.REQUEST_NOT_FOUND));

    if (req.getStatus() != FriendRequestStatus.PENDING) {
      throw new CustomException(FriendErrorCode.INVALID_STATUS);
    }

    friendRequestRepository.delete(req);
  }
}
