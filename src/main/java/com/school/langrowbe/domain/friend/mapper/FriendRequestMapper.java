/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.friend.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.school.langrowbe.domain.friend.dto.request.FriendRequestCreateRequest;
import com.school.langrowbe.domain.friend.dto.response.FriendRequestResponse;
import com.school.langrowbe.domain.friend.entity.FriendRequest;
import com.school.langrowbe.domain.friend.entity.FriendRequestStatus;

@Component
public class FriendRequestMapper {

  public FriendRequest toEntity(Long fromUserId, FriendRequestCreateRequest req) {
    return FriendRequest.builder()
        .fromUserId(fromUserId)
        .toUserId(req.toUserId())
        .status(FriendRequestStatus.PENDING)
        .build();
  }

  public FriendRequestResponse toResponse(FriendRequest fr) {
    return new FriendRequestResponse(
        fr.getId(),
        fr.getFromUserId(),
        fr.getToUserId(),
        fr.getStatus(),
        fr.getCreatedAt(),
        fr.getUpdatedAt());
  }

  public List<FriendRequestResponse> toResponseList(List<FriendRequest> list) {
    return list.stream().map(this::toResponse).collect(Collectors.toList());
  }
}
