package com.mattgawarecki.play.jammies.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonApiErrorTests {
    private ObjectMapper objectMapper;

    @Before
    public void init() {
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testSerialization_IdIsDefined_SerializesToIdField() {
        final String expected = "my_id_value";
        final JsonApiError error = new JsonApiError();
        error.setId(expected);

        final JsonNode json = this.objectMapper.valueToTree(error);
        assertThat(json.get("id").textValue()).isEqualTo(expected);
    }

    @Test
    public void testSerialization_IdNotDefined_DoesNotSerializeIdField() {
        final JsonApiError error = new JsonApiError();
        final JsonNode json = this.objectMapper.valueToTree(error);
        assertThat(json.fieldNames()).doesNotContain("id");
    }

    @Test
    public void testSerialization_LinksFieldHasItems_SerializesToLinksField() {
        final SimpleLink link = new SimpleLink(null);
        final JsonApiError error = new JsonApiError();
        error.links().add(link);

        final JsonNode linksJson = this.objectMapper.valueToTree(error).get("links");
        assertThat(linksJson.isArray()).isTrue();
        assertThat(linksJson.size()).isEqualTo(1);
    }

    @Test
    public void testSerialization_LinksFieldIsEmpty_DoesNotSerializeLinksField() {
        final JsonApiError error = new JsonApiError();
        final JsonNode json = this.objectMapper.valueToTree(error);
        assertThat(json.fieldNames()).doesNotContain("links");
    }

    @Test
    public void testSerialization_StatusIsDefined_SerializesToStatusFieldAsString() {
        final Integer expected = 200;
        final JsonApiError error = new JsonApiError();
        error.setStatus(expected);

        final JsonNode statusJson = this.objectMapper.valueToTree(error).get("status");
        assertThat(statusJson.isTextual()).isTrue();
        assertThat(statusJson.textValue()).isEqualTo(expected.toString());
    }

    @Test
    public void testSerialization_StatusNotDefined_DoesNotSerializeStatusField() {
        final JsonApiError error = new JsonApiError();
        final JsonNode json = this.objectMapper.valueToTree(error);
        assertThat(json.fieldNames()).doesNotContain("status");
    }

    @Test
    public void testSerialization_CodeIsDefined_SerializesToCodeField() {
        final String expected = "my_code_value";
        final JsonApiError error = new JsonApiError();
        error.setCode(expected);

        final JsonNode json = this.objectMapper.valueToTree(error);
        assertThat(json.get("code").textValue()).isEqualTo(expected);
    }

    @Test
    public void testSerialization_CodeNotDefined_DoesNotSerializeCodeField() {
        final JsonApiError error = new JsonApiError();
        final JsonNode json = this.objectMapper.valueToTree(error);
        assertThat(json.fieldNames()).doesNotContain("code");
    }

    @Test
    public void testSerialization_TitleIsDefined_SerializesToTitleField() {
        final String expected = "my_title_value";
        final JsonApiError error = new JsonApiError();
        error.setTitle(expected);

        final JsonNode json = this.objectMapper.valueToTree(error);
        assertThat(json.get("title").textValue()).isEqualTo(expected);
    }

    @Test
    public void testSerialization_TitleNotDefined_DoesNotSerializeTitleField() {
        final JsonApiError error = new JsonApiError();
        final JsonNode json = this.objectMapper.valueToTree(error);
        assertThat(json.fieldNames()).doesNotContain("title");
    }

    @Test
    public void testSerialization_DetailIsDefined_SerializesToDetailField() {
        final String expected = "my_detail_value";
        final JsonApiError error = new JsonApiError();
        error.setDetail(expected);

        final JsonNode json = this.objectMapper.valueToTree(error);
        assertThat(json.get("detail").textValue()).isEqualTo(expected);
    }

    @Test
    public void testSerialization_DetailNotDefined_DoesNotSerializeDetailField() {
        final JsonApiError error = new JsonApiError();
        final JsonNode json = this.objectMapper.valueToTree(error);
        assertThat(json.fieldNames()).doesNotContain("detail");
    }

    @Test
    public void testSerialization_SourceIsDefined_SerializesToSourceField() {
        final Source source = new Source(null, null);
        final JsonApiError error = new JsonApiError();
        error.setSource(source);

        final JsonNode sourceJson = this.objectMapper.valueToTree(error).get("source");
        assertThat(sourceJson.isObject()).isTrue();
    }

    @Test
    public void testSerialization_SourceNotDefined_DoesNotSerializeSourceField() {
        final JsonApiError error = new JsonApiError();
        final JsonNode json = this.objectMapper.valueToTree(error);
        assertThat(json.fieldNames()).doesNotContain("source");
    }

    @Test
    public void testSerialization_MetaFieldHasKeysAndValues_SerializesMetaFieldWithKeysAndValues() {
        final String expectedKey = "my_key";
        final Integer expectedValue = 42;
        final JsonApiError error = new JsonApiError();
        error.meta().put(expectedKey, expectedValue);

        final JsonNode metaJson = this.objectMapper.valueToTree(error).get("meta");
        assertThat(metaJson.isObject()).isTrue();
        assertThat(metaJson.size()).isEqualTo(1);
        assertThat(metaJson.get(expectedKey).intValue()).isEqualTo(expectedValue);
    }

    @Test
    public void testSerialization_MetaFieldIsEmpty_DoesNotSerializeMetaField() {
        final JsonApiError error = new JsonApiError();
        final JsonNode json = this.objectMapper.valueToTree(error);
        assertThat(json.fieldNames()).doesNotContain("meta");
    }
}
