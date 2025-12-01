/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.global.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.school.langrowbe.global.jwt.JwtProvider;
import com.school.langrowbe.global.redis.RedisUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;
  private final RedisUtil redisUtil;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String token = SecurityUtil.resolveAccessToken(request);

    if (token != null && jwtProvider.validateToken(token)) {

      // 블랙리스트 체크
      if (redisUtil.existData("blacklist:" + token)) {
        log.warn("블랙리스트 토큰 접근 차단 - token: {}", token);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access token is blacklisted.");
        return;
      }

      Long userId = jwtProvider.extractUserId(token);
      String loginId = jwtProvider.extractLoginId(token);

      CustomUserDetails userDetails = new CustomUserDetails(userId, loginId);

      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }
}
