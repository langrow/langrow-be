package com.school.langrowbe.domain.diary.entity;

import com.school.langrowbe.domain.user.entity.User;
import com.school.langrowbe.global.common.BaseTimeEntity;
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
import java.time.LocalDate;
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
    name = "diary",
    indexes = {
        @Index(name = "idx_diary_user", columnList = "user_id"),
        @Index(name = "idx_diary_entry_date", columnList = "entry_date")
    }
)
public class Diary extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Lob
  @Column(nullable = false)
  private String content;

  @Column(name = "entry_date", nullable = false)
  private LocalDate entryDate;

  public void update(String content, LocalDate entryDate) {
    this.content = content;
    this.entryDate = entryDate;
  }
}