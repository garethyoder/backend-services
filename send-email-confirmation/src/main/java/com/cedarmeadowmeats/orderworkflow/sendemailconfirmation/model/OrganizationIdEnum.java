package com.cedarmeadowmeats.orderworkflow.sendemailconfirmation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrganizationIdEnum {

    @JsonProperty("cedarmeadowmeats")
    CEDAR_MEADOW_MEATS("cedarmeadowmeats"),
    @JsonProperty("cedarmeadownaturals")
    CEDAR_MEADOW_NATURALS("cedarmeadownaturals"),
    @JsonProperty("gyoderaudioexpressions")
    G_YODER_AUDIO_EXPRESSIONS("gyoderaudioexpressions");

    private final String value;

    OrganizationIdEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String getCompanyName(OrganizationIdEnum organizationIdEnum) {
        if (organizationIdEnum.equals(CEDAR_MEADOW_MEATS)) {
            return "Cedar Meadow Meats";
        } else if (organizationIdEnum.equals(CEDAR_MEADOW_NATURALS)) {
            return "Cedar Meadow Naturals";
        } else if (organizationIdEnum.equals(G_YODER_AUDIO_EXPRESSIONS)) {
            return "G Yoder Audio Expressions";
        } else {
            return "Unknown";
        }
    }
}
