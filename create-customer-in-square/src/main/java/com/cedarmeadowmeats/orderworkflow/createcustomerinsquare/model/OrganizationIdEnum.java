package com.cedarmeadowmeats.orderworkflow.createcustomerinsquare.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrganizationIdEnum {

    @JsonProperty("cedarmeadowmeats")
    CEDAR_MEADOW_MEATS("cedarmeadowmeats"),
    @JsonProperty("cedarmeadownaturals")
    CEDAR_MEADOW_NATURALS("cedarmeadownaturals");

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
        } else {
            return "Unknown";
        }
    }
}
