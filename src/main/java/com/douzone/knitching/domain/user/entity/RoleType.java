package com.douzone.knitching.domain.user.entity;

/**
 * 사용자 역할 유형을 정의하는 열거형
 */
public enum RoleType {
    USER("일반 수강생"),
    INSTRUCTOR("강사");

    private final String description;

    RoleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
