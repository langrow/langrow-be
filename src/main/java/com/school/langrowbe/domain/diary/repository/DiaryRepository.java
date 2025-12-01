/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.diary.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.langrowbe.domain.diary.entity.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

  List<Diary> findByUser_IdOrderByCreatedAtDesc(Long userId);

  Optional<Diary> findByIdAndUser_Id(Long id, Long userId);
}
