package com.mattgawarecki.play.jammies.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DetailedLinkTests {
    private ObjectMapper objectMapper;

    @Before
    public void init() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testSerialization_HrefDefined_SerializesToHrefField() {
        final String expected = "my_href_value";
        final DetailedLink link = new DetailedLink();
        link.setHref(expected);

        final JsonNode json = this.objectMapper.valueToTree(link);
        assertThat(json.get("href").textValue()).isEqualTo(expected);
    }

    @Test
    public void testSerialization_HrefNotDefined_DoesNotSerializeHrefField() {
        final DetailedLink link = new DetailedLink();
        final JsonNode json = this.objectMapper.valueToTree(link);
        assertThat(json.fieldNames()).doesNotContain("href");
    }

    @Test
    public void testSerialization_MetaFieldHasKeysAndValues_SerializesMetaFieldWithKeysAndValues() {
        final String expectedKey = "my_key";
        final Integer expectedValue = 42;

        final DetailedLink link = new DetailedLink();
        link.meta().put(expectedKey, expectedValue);

        final JsonNode metaJson = this.objectMapper.valueToTree(link).get("meta");
        assertThat(metaJson.isObject()).isTrue();
        assertThat(metaJson.size()).isEqualTo(1);
        assertThat(metaJson.get(expectedKey).intValue()).isEqualTo(expectedValue);
    }

    @Test
    public void testSerialization_MetaFieldIsEmpty_DoesNotSerializeMetaField() {
        final DetailedLink link = new DetailedLink();
        final JsonNode json = this.objectMapper.valueToTree(link);
        assertThat(json.fieldNames()).doesNotContain("meta");
    }
}
