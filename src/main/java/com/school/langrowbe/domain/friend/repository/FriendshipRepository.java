/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.friend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.langrowbe.domain.friend.entity.Friendship;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

  boolean existsByUser1IdAndUser2Id(Long user1Id, Long user2Id);
}
