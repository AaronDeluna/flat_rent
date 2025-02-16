package org.javaacademy.flat_rents.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoomsCount {
    STUDIO("Только комната"),
    ONE_BEDROOM("1-комнатная"),
    TWO_BEDROOM("2-комнатная"),
    THREE_BEDROOM("3-комнатная"),
    FOUR_OR_MORE_BEDROOMS("4 и более комнатная квартира");

    private final String description;
}
