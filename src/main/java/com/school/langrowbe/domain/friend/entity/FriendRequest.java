/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.friend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import com.school.langrowbe.global.common.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    name = "friend_request",
    indexes = {
      @Index(name = "idx_fr_from", columnList = "from_user_id"),
      @Index(name = "idx_fr_to", columnList = "to_user_id"),
      @Index(name = "idx_fr_status", columnList = "status")
    })
public class FriendRequest extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "from_user_id", nullable = false)
  private Long fromUserId;

  @Column(name = "to_user_id", nullable = false)
  private Long toUserId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private FriendRequestStatus status;

  public void accept() {
    this.status = FriendRequestStatus.ACCEPTED;
  }

  public void reject() {
    this.status = FriendRequestStatus.REJECTED;
  }
}
