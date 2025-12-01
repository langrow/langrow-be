/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.user.dto.response;

import java.util.List;

import com.school.langrowbe.domain.user.entity.InterestType;
import com.school.langrowbe.domain.user.entity.Language;
import com.school.langrowbe.domain.user.entity.Level;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UserResponse", description = "사용자 정보 응답 DTO")
public record UserResponse(
    @Schema(description = "사용자 식별자", example = "1") Long id,
    @Schema(description = "로그인 아이디", example = "wit") String loginId,
    @Schema(description = "사용자 이름", example = "나경") String name,
    @Schema(description = "모국어 (KO, EN, JA, ZH)", example = "KO") Language nativeLanguage,
    @Schema(description = "학습 언어 (KO, EN, JA, ZH)", example = "JA") Language learningLanguage,
    @Schema(description = "레벨 (BEGINNER, INTERMEDIATE, ADVANCED)", example = "BEGINNER")
        Level level,
    @Schema(
            description =
                "관심사 목록 (MUSIC, MOVIES, FOOD, TRAVEL, READING, SPORTS, GAMING, ART, HISTORY, SCIENCE)",
            example = "[\"MUSIC\", \"MOVIES\", \"TRAVEL\"]")
        List<InterestType> interests,
    @Schema(description = "소개글", example = "저는 일본어를 마스터해서 일본 여행을 갈 거예요!!") String bio,
    @Schema(description = "작성한 일기 수", example = "24") int diaryCount,
    @Schema(description = "연속 작성 일수", example = "5") int streakCount,
    @Schema(description = "친구 수", example = "3") int friendCount) {}
