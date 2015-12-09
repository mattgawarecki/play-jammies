package com.mattgawarecki.play.http;

import com.fasterxml.jackson.databind.JsonNode;
import org.assertj.core.api.SoftAssertions;
import play.libs.F;
import play.libs.Json;
import play.mvc.Result;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static play.test.Helpers.contentAsString;

public class JsonApiErrorResponseTestHelper {
    private final SoftAssertions softly;

    public JsonApiErrorResponseTestHelper(final SoftAssertions softly) {
        this.softly = softly;
    }

    public void testResultHttpStatusCode(final Result result, final int expected) {
        softly.assertThat(result.status()).isEqualTo(expected);
    }

    public void testResultContentType(final Result result) {
        softly.assertThat(result.contentType()).isEqualTo("application/vnd.api+json");
    }

    public void testResultBodyIsWrapped(final Result result) {
        final JsonNode json = getResultJson(result);
        softly.assertThat(json.isObject());
        softly.assertThat(json.fieldNames()).containsOnly("error");
    }

    public void testResultBodyStatus(final Result result, final int expected) {
        final JsonNode json = getResultJson(result);
        softly.assertThat(json.get("error").get("status").textValue()).isEqualTo(Integer.toString(expected));
    }

    public void testResultBodyTitle(final Result result, final String expected) {
        final JsonNode json = getResultJson(result);
        softly.assertThat(json.get("error").get("title").textValue()).isEqualTo(expected);
    }

    public void testResultBodyDetail(final Result result, final String expected) {
        final JsonNode json = getResultJson(result);
        softly.assertThat(json.get("error").get("detail").textValue()).isEqualTo(expected);
    }

    public void assertAll() {
        softly.assertAll();
    }

    public List<Result> redeemPromises(final F.Promise<Result>... promises) {
        return Arrays.stream(promises).map(p -> p.get(0)).collect(Collectors.toList());
    }

    public JsonNode getResultJson(final Result result) {
        return Json.parse(contentAsString(result));
    }
}
