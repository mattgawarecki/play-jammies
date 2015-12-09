package com.mattgawarecki.play.jammies.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class SimpleLink implements Link {
    private final String value;

    @JsonCreator
    public SimpleLink(final String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() { return value; }
}
