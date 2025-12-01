/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.friend.service;

import org.springframework.stereotype.Service;

import com.school.langrowbe.domain.friend.repository.FriendshipRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendService {

  private final FriendshipRepository friendshipRepository;

  public boolean areFriends(Long a, Long b) {
    Long u1 = Math.min(a, b);
    Long u2 = Math.max(a, b);
    return friendshipRepository.existsByUser1IdAndUser2Id(u1, u2);
  }
}
