package org.minbros.chatbot.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RestaurantLocation {
    MAIN_BUILDING("010"),      // 본관
    CENTER("020"),              // 학생회관
    WESTERN("030"),             // 양식당
    NATURAL_SCIENCE("040");    // 자연과학관

    private final String stringValue;
}
