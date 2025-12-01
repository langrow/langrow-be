/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.langrowbe.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByLoginId(String loginId);

  boolean existsByLoginId(String loginId);
}
