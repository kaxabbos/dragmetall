package com.dragmetall.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {
    GOLD("Золото"),
    SILVER("Серебро"),
    IRIDIUM("Иридий"),
    OSMIUM("Осмий"),
    RUTHENIUM("Рутений"),
    COPPER("Медь");

    private final String name;
}

