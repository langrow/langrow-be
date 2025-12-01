/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.friend.dto.request;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FriendRequestCreateRequest", description = "친구 요청 생성 요청")
public record FriendRequestCreateRequest(
    @NotNull @Schema(description = "요청을 받을 사용자 ID", example = "7") Long toUserId) {}
