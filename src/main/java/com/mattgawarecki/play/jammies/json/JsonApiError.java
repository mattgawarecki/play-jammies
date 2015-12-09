package com.mattgawarecki.play.jammies.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(value = JsonInclude.Include.NON_DEFAULT, content = JsonInclude.Include.NON_EMPTY)
public class JsonApiError {
    @JsonProperty
    private String id;

    @JsonProperty
    private final List<Link> links;

    @JsonProperty
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer status;

    @JsonProperty
    private String code;

    @JsonProperty
    private String title;

    @JsonProperty
    private String detail;

    @JsonProperty
    private Source source;

    @JsonProperty
    private final Map<String, Object> meta;

    public JsonApiError() {
        this.links = new ArrayList<>();
        this.meta = new HashMap<>();
    }

    public String getId() { return id; }
    public List<Link> links() { return links; }
    public Integer getStatus() { return status; }
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public String getDetail() { return detail; }
    public Source getSource() { return source; }
    public Map<String, Object> meta() { return meta; }

    public void setId(final String id) { this.id = id; }
    public void setStatus(final Integer status) { this.status = status; }
    public void setCode(final String code) { this.code = code; }
    public void setTitle(final String title) { this.title = title; }
    public void setDetail(final String detail) { this.detail = detail; }
    public void setSource(final Source source) { this.source = source; }
}
