package com.school.langrowbe.domain.diary.mapper;

import com.school.langrowbe.domain.diary.dto.request.DiaryCreateRequest;
import com.school.langrowbe.domain.diary.dto.response.DiaryResponse;
import com.school.langrowbe.domain.diary.entity.Diary;
import com.school.langrowbe.domain.user.entity.User;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class DiaryMapper {

  public Diary toEntity(User user, DiaryCreateRequest req) {
    return Diary.builder()
        .user(user)
        .content(req.content())
        .entryDate(req.entryDate() != null ? req.entryDate() : LocalDate.now())
        .build();
  }

  public DiaryResponse toResponse(Diary d) {
    return new DiaryResponse(
        d.getId(),
        d.getUser().getId(),
        d.getContent(),
        d.getEntryDate(),
        d.getCreatedAt(),
        d.getUpdatedAt()
    );
  }

  /**
   * 서비스에서 편하게 쓰도록 List 변환 지원
   */
  public List<DiaryResponse> toResponseList(List<Diary> diaries) {
    return diaries.stream().map(this::toResponse).collect(Collectors.toList());
  }

  /**
   * 필요 시 Page 매핑도 제공
   */
  public Page<DiaryResponse> toResponsePage(Page<Diary> page) {
    return page.map(this::toResponse);
  }
}