/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.user.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.school.langrowbe.domain.user.entity.InterestType;
import com.school.langrowbe.domain.user.entity.Language;
import com.school.langrowbe.domain.user.entity.Level;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "InfoRequest", description = "회원 정보(회원가입/마이페이지 수정) 요청 DTO")
public record InfoRequest(

    // ====== 계정 기본 정보 ======

    @NotBlank(message = "로그인 아이디는 필수입니다.")
        @Size(min = 4, max = 20, message = "로그인 아이디는 4~20자여야 합니다.")
        @Pattern(regexp = "^[A-Za-z0-9]+$", message = "아이디는 영문과 숫자만 사용할 수 있습니다.")
        @Schema(description = "아이디", example = "langrow", minLength = 4, maxLength = 20)
        String loginId,
    @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 8~20자여야 합니다.")
        @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}$",
            message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다.")
        @Schema(description = "비밀번호", example = "qwer1234!", minLength = 8, maxLength = 20)
        String password,
    @NotBlank(message = "이름은 필수입니다.")
        @Size(min = 2, max = 20, message = "이름은 2~20자여야 합니다.")
        @Pattern(regexp = "^[가-힣A-Za-z]+$", message = "이름은 한글 또는 영문만 가능합니다.")
        @Schema(description = "사용자 이름", example = "나경", minLength = 2, maxLength = 20)
        String name,

    // ====== 프로필 정보 ======

    @Schema(description = "모국어 (KO, EN, JA, ZH)", example = "KO") Language nativeLanguage,
    @Schema(description = "학습 언어 (KO, EN, JA, ZH)", example = "JA") Language learningLanguage,
    @Schema(description = "레벨 (BEGINNER, INTERMEDIATE, ADVANCED)", example = "BEGINNER")
        Level level,
    @Schema(
            description =
                "관심사 목록 (MUSIC, MOVIES, FOOD, TRAVEL, READING, SPORTS, GAMING, ART, HISTORY, SCIENCE)",
            example = "[\"MUSIC\", \"MOVIES\", \"TRAVEL\"]")
        List<InterestType> interests,
    @Size(max = 255, message = "소개는 최대 255자까지 가능합니다.")
        @Schema(description = "소개", example = "저는 일본어를 마스터해서 일본 여행을 갈 거예요!!")
        String bio) {}
