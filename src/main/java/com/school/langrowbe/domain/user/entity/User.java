/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.user.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 20)
  private String loginId;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, length = 20)
  private String name;

  // ------------ 언어 필드 (고정 Enum) ------------

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 5)
  private Language nativeLanguage; // 모국어

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 5)
  private Language learningLanguage; // 학습 중인 언어

  // ------------ 레벨 ------------

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private Level level;

  // ------------ 관심사 ------------

  @Builder.Default
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "user_interests", joinColumns = @JoinColumn(name = "user_id"))
  @Enumerated(EnumType.STRING)
  @Column(name = "interest", length = 30)
  private Set<InterestType> interests = new HashSet<>();

  // ------------ 소개 ------------

  @Column(length = 255)
  private String bio;

  // ------------ 통계 ------------

  @Builder.Default
  @Column(nullable = false)
  private int diaryCount = 0;

  @Builder.Default
  @Column(nullable = false)
  private int streakCount = 0;

  @Builder.Default
  @Column(nullable = false)
  private int friendCount = 0;

  public void updateProfile(
      String name,
      Language nativeLanguage,
      Language learningLanguage,
      Level level,
      List<InterestType> interests,
      String bio) {
    if (name != null) {
      this.name = name;
    }
    if (nativeLanguage != null) {
      this.nativeLanguage = nativeLanguage;
    }
    if (learningLanguage != null) {
      this.learningLanguage = learningLanguage;
    }
    if (level != null) {
      this.level = level;
    }

    if (interests != null) {
      if (this.interests == null) {
        this.interests = new HashSet<>();
      }
      this.interests.clear();
      this.interests.addAll(interests);
    }

    if (bio != null) {
      this.bio = bio;
    }
  }

  public void increaseDiaryCount() {
    this.diaryCount++;
  }

  public void decreaseDiaryCountSafely() {
    if (this.diaryCount > 0) {
      this.diaryCount--;
    }
  }
}
