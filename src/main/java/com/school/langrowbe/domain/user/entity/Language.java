/* 
 * Copyright (c) 나경 
 */
package com.school.langrowbe.domain.user.entity;

public enum Language {
  KO("Korean", "한국어"),
  EN("English", "English"),
  JA("Japanese", "日本語"),
  ZH("Chinese", "中文");

  private final String label;
  private final String nativeName;

  Language(String label, String nativeName) {
    this.label = label;
    this.nativeName = nativeName;
  }

  public String getLabel() {
    return label;
  }

  public String getNativeName() {
    return nativeName;
  }
}
