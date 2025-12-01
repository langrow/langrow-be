/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.friend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.school.langrowbe.domain.friend.entity.FriendRequest;
import com.school.langrowbe.domain.friend.entity.FriendRequestStatus;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

  List<FriendRequest> findByToUserIdAndStatus(Long toUserId, FriendRequestStatus status);

  List<FriendRequest> findByFromUserIdAndStatus(Long fromUserId, FriendRequestStatus status);

  Optional<FriendRequest> findByIdAndToUserId(Long id, Long toUserId);

  Optional<FriendRequest> findByIdAndFromUserId(Long id, Long fromUserId);

  boolean existsByFromUserIdAndToUserIdAndStatus(
      Long fromUserId, Long toUserId, FriendRequestStatus status);

  @Query(
      """
      select case when count(fr)>0 then true else false end
      from FriendRequest fr
      where fr.status = 'PENDING'
        and (
          (fr.fromUserId = :a and fr.toUserId = :b)
          or
          (fr.fromUserId = :b and fr.toUserId = :a)
        )
      """)
  boolean existsPendingEitherDirection(Long a, Long b);
}
