package com.cedarmeadowmeats.orderworkflow.createcustomerinsquare.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FormEnum {

    @JsonProperty("contact-form")
    CONTACT_FORM("contact-form"),
    @JsonProperty("order-form")
    ORDER_FORM("order-form"),
    @JsonProperty("fundraiser-form")
    FUNDRAISER_FORM("fundraiser-form"),
    @JsonProperty("vendor-form")
    VENDOR_FORM("vendor-form");

    private final String value;

    FormEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static String getFormName(FormEnum formEnum) {
        if (formEnum.equals(CONTACT_FORM)) {
            return "Contact Form";
        } else if (formEnum.equals(ORDER_FORM)) {
            return "Order Form";
        } else if (formEnum.equals(FUNDRAISER_FORM)) {
            return "Fundraiser Form";
        } else if (formEnum.equals(VENDOR_FORM)) {
            return "Vendor Form";
        } else {
            return "Unknown";
        }
    }

}
