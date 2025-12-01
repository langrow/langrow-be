package com.school.langrowbe.domain.diary.controller;

import com.school.langrowbe.domain.diary.dto.request.DiaryCreateRequest;
import com.school.langrowbe.domain.diary.dto.response.DiaryResponse;
import com.school.langrowbe.domain.diary.service.DiaryService;
import com.school.langrowbe.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Diary", description = "일기 CRUD API(수정 제외)")
@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {

  private final DiaryService diaryService;

  @Operation(summary = "일기 작성")
  @PostMapping
  public ResponseEntity<BaseResponse<DiaryResponse>> create(
      @RequestBody @Valid DiaryCreateRequest request) {
    return ResponseEntity.ok(BaseResponse.success(diaryService.create(request)));
  }

  @Operation(summary = "일기 단건 조회(본인)")
  @GetMapping("/{id}")
  public ResponseEntity<BaseResponse<DiaryResponse>> get(@PathVariable Long id) {
    return ResponseEntity.ok(BaseResponse.success(diaryService.get(id)));
  }

  @Operation(summary = "내 일기 전체 조회(페이지)")
  @GetMapping("/me")
  public ResponseEntity<BaseResponse<Page<DiaryResponse>>> getMyDiaries(Pageable pageable) {
    return ResponseEntity.ok(BaseResponse.success(diaryService.getMyDiaries(pageable)));
  }

  @Operation(summary = "일기 삭제(본인)")
  @DeleteMapping("/{id}")
  public ResponseEntity<BaseResponse<Void>> delete(@PathVariable Long id) {
    diaryService.delete(id);
    return ResponseEntity.ok(BaseResponse.success(null));
  }
}