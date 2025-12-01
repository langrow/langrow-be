/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.comment.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.school.langrowbe.domain.comment.dto.request.CommentCreateRequest;
import com.school.langrowbe.domain.comment.dto.request.CommentUpdateRequest;
import com.school.langrowbe.domain.comment.dto.response.CommentResponse;
import com.school.langrowbe.domain.comment.entity.Comment;
import com.school.langrowbe.domain.comment.exception.CommentErrorCode;
import com.school.langrowbe.domain.comment.mapper.CommentMapper;
import com.school.langrowbe.domain.comment.repository.CommentRepository;
import com.school.langrowbe.domain.diary.dto.response.DiaryDetailResponse;
import com.school.langrowbe.domain.diary.entity.Diary;
import com.school.langrowbe.domain.diary.exception.DiaryErrorCode;
import com.school.langrowbe.domain.diary.mapper.DiaryMapper;
import com.school.langrowbe.domain.diary.repository.DiaryRepository;
import com.school.langrowbe.domain.friend.service.FriendService;
import com.school.langrowbe.domain.user.entity.User;
import com.school.langrowbe.domain.user.exception.UserErrorCode;
import com.school.langrowbe.domain.user.repository.UserRepository;
import com.school.langrowbe.global.exception.CustomException;
import com.school.langrowbe.global.security.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final DiaryRepository diaryRepository;
  private final UserRepository userRepository;
  private final FriendService friendService;
  private final CommentMapper commentMapper;
  private final DiaryMapper diaryMapper;

  @Override
  public CommentResponse create(Long diaryId, CommentCreateRequest request) {
    Long me = SecurityUtil.getCurrentUserId();
    User author =
        userRepository
            .findById(me)
            .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

    Diary diary =
        diaryRepository
            .findById(diaryId)
            .orElseThrow(() -> new CustomException(DiaryErrorCode.DIARY_NOT_FOUND));

    Long ownerId = diary.getUser().getId();

    // 소유자는 자기 일기에 댓글 가능, 그 외에는 친구만 가능
    if (!friendService.areFriends(me, ownerId)) {
      throw new CustomException(CommentErrorCode.NOT_FRIEND);
    }

    Comment saved = commentRepository.save(commentMapper.toEntity(diary, author, request));
    return commentMapper.toResponse(saved);
  }

  @Override
  public DiaryDetailResponse getWithComments(Long diaryId, Integer sentenceIndex) {
    Long userId = SecurityUtil.getCurrentUserId();

    var diary =
        diaryRepository
            .findById(diaryId)
            .orElseThrow(() -> new CustomException(DiaryErrorCode.DIARY_NOT_FOUND));

    // 정책: 본인만 상세 조회 (필요 시 완화 가능)
    if (!diary.getUser().getId().equals(userId)) {
      throw new CustomException(DiaryErrorCode.NO_PERMISSION);
    }

    var diaryResp = diaryMapper.toResponse(diary);

    var comments =
        (sentenceIndex == null)
            ? commentRepository.findAllWithAuthorByDiaryIdOrderBySentenceAsc(diaryId)
            : commentRepository.findAllWithAuthorByDiaryIdAndSentenceIndex(diaryId, sentenceIndex);

    return new DiaryDetailResponse(diaryResp, commentMapper.toResponseList(comments));
  }

  @Override
  public List<CommentResponse> list(Long diaryId, Integer sentenceIndex) {
    // 일기 존재 검증(권한은 정책에 따라: 본인/친구만/공개… 지금은 본인만으로 가려면 SecurityUtil로 체크)
    diaryRepository
        .findById(diaryId)
        .orElseThrow(() -> new CustomException(DiaryErrorCode.DIARY_NOT_FOUND));

    if (sentenceIndex == null) {
      return commentMapper.toResponseList(
          commentRepository.findAllWithAuthorByDiaryIdOrderBySentenceAsc(diaryId));
    } else {
      return commentMapper.toResponseList(
          commentRepository.findAllWithAuthorByDiaryIdAndSentenceIndex(diaryId, sentenceIndex));
    }
  }

  @Override
  public CommentResponse update(Long commentId, CommentUpdateRequest request) {
    Long me = SecurityUtil.getCurrentUserId();

    Comment comment =
        commentRepository
            .findById(commentId)
            .orElseThrow(() -> new CustomException(CommentErrorCode.COMMENT_NOT_FOUND));

    if (!comment.getAuthor().getId().equals(me)) {
      throw new CustomException(CommentErrorCode.NO_PERMISSION);
    }

    comment.update(request.content());
    return commentMapper.toResponse(comment);
  }

  @Override
  public void delete(Long commentId) {
    Long me = SecurityUtil.getCurrentUserId();

    Comment comment =
        commentRepository
            .findById(commentId)
            .orElseThrow(() -> new CustomException(CommentErrorCode.COMMENT_NOT_FOUND));

    if (!comment.getAuthor().getId().equals(me)) {
      throw new CustomException(CommentErrorCode.NO_PERMISSION);
    }

    commentRepository.delete(comment);
  }
}
