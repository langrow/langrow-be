/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.friend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

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
    name = "friendship",
    uniqueConstraints =
        @UniqueConstraint(
            name = "uk_friend_pair",
            columnNames = {"user1_id", "user2_id"}),
    indexes = {
      @Index(name = "idx_friend_user1", columnList = "user1_id"),
      @Index(name = "idx_friend_user2", columnList = "user2_id")
    })
public class Friendship extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user1_id", nullable = false)
  private Long user1Id;

  @Column(name = "user2_id", nullable = false)
  private Long user2Id;

  public static Friendship of(Long a, Long b) {
    Long min = Math.min(a, b);
    Long max = Math.max(a, b);
    return Friendship.builder().user1Id(min).user2Id(max).build();
  }
}
