/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.school.langrowbe.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  @Query(
      """
      select c
      from Comment c
      join fetch c.author
      where c.diary.id = :diaryId
      order by c.sentenceIndex asc, c.createdAt asc
      """)
  List<Comment> findAllWithAuthorByDiaryIdOrderBySentenceAsc(Long diaryId);

  @Query(
      """
      select c
      from Comment c
      join fetch c.author
      where c.diary.id = :diaryId and c.sentenceIndex = :sentenceIndex
      order by c.createdAt asc
      """)
  List<Comment> findAllWithAuthorByDiaryIdAndSentenceIndex(Long diaryId, int sentenceIndex);
}
