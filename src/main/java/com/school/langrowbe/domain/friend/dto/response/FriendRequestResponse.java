/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.friend.dto.response;

import java.time.LocalDateTime;

import com.school.langrowbe.domain.friend.entity.FriendRequestStatus;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FriendRequestResponse", description = "친구 요청 응답")
public record FriendRequestResponse(
    Long id,
    Long fromUserId,
    Long toUserId,
    FriendRequestStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
