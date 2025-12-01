/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.comment.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.school.langrowbe.domain.comment.dto.request.CommentCreateRequest;
import com.school.langrowbe.domain.comment.dto.response.CommentResponse;
import com.school.langrowbe.domain.comment.entity.Comment;
import com.school.langrowbe.domain.diary.entity.Diary;
import com.school.langrowbe.domain.user.entity.User;

@Component
public class CommentMapper {

  public Comment toEntity(Diary diary, User author, CommentCreateRequest req) {
    return Comment.builder()
        .diary(diary)
        .author(author)
        .sentenceIndex(req.sentenceIndex())
        .content(req.content())
        .build();
  }

  public CommentResponse toResponse(Comment c) {
    return new CommentResponse(
        c.getId(),
        c.getDiary().getId(),
        c.getAuthor().getId(),
        c.getAuthor().getName(),
        c.getSentenceIndex(),
        c.getContent(),
        c.getCreatedAt(),
        c.getUpdatedAt());
  }

  public List<CommentResponse> toResponseList(List<Comment> list) {
    return list.stream().map(this::toResponse).collect(Collectors.toList());
  }
}
