/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.comment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.school.langrowbe.domain.diary.entity.Diary;
import com.school.langrowbe.domain.user.entity.User;
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
    name = "comment",
    indexes = {
      @Index(name = "idx_comment_diary", columnList = "diary_id"),
      @Index(name = "idx_comment_author", columnList = "author_id"),
      @Index(name = "idx_comment_sentence", columnList = "sentence_index")
    })
public class Comment extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "diary_id", nullable = false)
  private Diary diary;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "author_id", nullable = false)
  private User author;

  /** 문장 단위 인덱스(0-based) */
  @Column(name = "sentence_index", nullable = false)
  private int sentenceIndex;

  @Lob
  @Column(nullable = false)
  private String content;

  public void update(String content) {
    this.content = content;
  }
}
