package com.cedarmeadowmeats.orderworkflow.sendemailconfirmation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderFormSelectionEnum {
    @JsonProperty("eighth-steer")
    EIGHTH_STEER("eighth-steer"),
    @JsonProperty("quarter-steer")
    QUARTER_STEER("quarter-steer"),
    @JsonProperty("half-steer")
    HALF_STEER("half-steer"),
    @JsonProperty("whole-steer")
    WHOLE_STEER("whole-steer"),
    @JsonProperty("half-hog")
    HALF_HOG("half-hog"),
    @JsonProperty("whole-hog")
    WHOLE_HOG("whole-hog");

    private final String value;


    OrderFormSelectionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
