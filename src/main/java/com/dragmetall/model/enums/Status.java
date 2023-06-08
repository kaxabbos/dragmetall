package com.dragmetall.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    ACTIVE("Активно"),
    DISPOSED_OF("Утилизирован");

    private final String name;
}

