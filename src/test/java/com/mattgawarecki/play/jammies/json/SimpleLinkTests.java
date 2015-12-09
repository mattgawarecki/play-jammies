package com.mattgawarecki.play.jammies.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleLinkTests {
    private ObjectMapper objectMapper;

    @Before
    public void init() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testSerialization_ValueDefined_SerializesToStringValue() {
        final String expected = "my_simple_link";
        final SimpleLink link = new SimpleLink(expected);

        final JsonNode json = this.objectMapper.valueToTree(link);
        assertThat(json.textValue()).isEqualTo(expected);
    }

    @Test
    public void testSerialization_ValueNotDefined_SerializesToEmptyNode() {
        final String expected = "my_simple_link";
        final SimpleLink link = new SimpleLink(null);

        final JsonNode json = this.objectMapper.valueToTree(link);
        assertThat(json).isEmpty();
    }
}
