package com.mattgawarecki.play.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Source {
    private final String pointer;
    private final String parameter;

    @JsonCreator
    public Source(@JsonProperty("pointer") final String pointer,
                  @JsonProperty("parameter") final String parameter) {
        this.pointer = pointer;
        this.parameter = parameter;
    }

    public String getPointer() { return pointer; }
    public String getParameter() { return parameter; }
}
