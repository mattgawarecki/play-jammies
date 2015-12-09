package com.mattgawarecki.play.jammies.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class DetailedLink implements Link {
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    @JsonProperty("href")
    private String href;

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    @JsonProperty("meta")
    private final Map<String, Object> meta;

    public DetailedLink() {
        this.meta = new HashMap<>();
    }

    public String getHref() { return href; }
    public void setHref(final String href) { this.href = href; }

    public Map<String, Object> meta() { return meta; }
}
