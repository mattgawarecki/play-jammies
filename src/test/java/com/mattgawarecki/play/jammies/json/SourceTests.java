package com.mattgawarecki.play.jammies.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SourceTests {
    private ObjectMapper objectMapper;

    @Before
    public void init() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testSerialization_PointerDefined_SerializesToPointerField() {
        final String expected = "my_pointer_value";
        final Source source = new Source(expected, null);

        final JsonNode json = this.objectMapper.valueToTree(source);
        assertThat(json.get("pointer").textValue()).isEqualTo(expected);
    }

    @Test
    public void testSerialization_PointerNotDefined_PointerFieldIsNotSerialized() {
        final Source source = new Source(null, null);

        final JsonNode json = this.objectMapper.valueToTree(source);
        assertThat(json.fieldNames()).doesNotContain("pointer");
    }

    @Test
    public void testSerialization_ParameterDefined_SerializesToParameterField() {
        final String expected = "my_parameter_value";
        final Source source = new Source(null, expected);

        final JsonNode json = this.objectMapper.valueToTree(source);
        assertThat(json.get("parameter").textValue()).isEqualTo(expected);
    }

    @Test
    public void testSerialization_ParameterNotDefined_ParameterFieldIsNotSerialized() {
        final Source source = new Source(null, null);

        final JsonNode json = this.objectMapper.valueToTree(source);
        assertThat(json.fieldNames()).doesNotContain("parameter");
    }
}
