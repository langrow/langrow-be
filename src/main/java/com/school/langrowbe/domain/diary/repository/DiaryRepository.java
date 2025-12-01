/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.diary.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.school.langrowbe.domain.diary.entity.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

  Page<Diary> findByUser_IdOrderByCreatedAtDesc(Long userId, Pageable pageable);

  Optional<Diary> findByIdAndUser_Id(Long id, Long userId);
}
