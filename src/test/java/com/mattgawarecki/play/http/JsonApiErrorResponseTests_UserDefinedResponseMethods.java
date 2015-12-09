package com.mattgawarecki.play.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattgawarecki.play.json.JsonApiError;
import com.mattgawarecki.play.json.SimpleLink;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import play.mvc.Result;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonApiErrorResponseTests_UserDefinedResponseMethods {
    private JsonApiErrorResponseTestHelper helper;

    private static final int quickErrorResponseStatus = 622;
    private static final String quickErrorResponseTitle = "";
    private static final String quickErrorResponseDetail = "";
    private Result quickErrorResult;

    private int fullErrorHttpStatusCode = 622;
    private JsonApiError fullError;
    private Result fullErrorResult;

    private List<Result> testedResults;

    @Before
    public void init() {
        helper = new JsonApiErrorResponseTestHelper(new SoftAssertions());
        quickErrorResult =
            JsonApiErrorResponse.status(quickErrorResponseStatus, quickErrorResponseTitle, quickErrorResponseDetail)
                                .get(0);

        fullError = new JsonApiError();
        // Set this differently than {fullErrorHttpStatusCode} to show the body and the response status are not
        // absolutely required to match (although it's recommended)
        fullError.setStatus(404);
        fullError.meta().put("is_full_error", true);
        fullError.links().add(new SimpleLink("full_error_link"));
        fullErrorResult = JsonApiErrorResponse.status(fullErrorHttpStatusCode, fullError).get(0);

        testedResults = Arrays.asList(quickErrorResult, fullErrorResult);
    }

    @Test
    public void testQuickStatus_ReturnsResultWithSuppliedHttpStatusCode() {
        helper.testResultHttpStatusCode(this.quickErrorResult, quickErrorResponseStatus);
    }

    @Test
    public void testFullStatus_ReturnsResultWithSuppliedHttpStatusCode() {
        helper.testResultHttpStatusCode(this.fullErrorResult, this.fullErrorHttpStatusCode);
    }

    @Test
    public void testAllMethods_ReturnsResultWithCorrectContentType() {
        testedResults.stream().forEach(helper::testResultContentType);
    }

    @Test
    public void testAllMethods_ReturnsResultWithBodyWrappedInErrorField() {
        testedResults.stream().forEach(helper::testResultBodyIsWrapped);
    }

    @Test
    public void testQuickStatus_ReturnsResultBodyWithCorrectStatusValue() {
        helper.testResultBodyStatus(this.quickErrorResult, quickErrorResponseStatus);
    }

    @Test
    public void testQuickStatus_ReturnsResultBodyWithCorrectTitleValue() {
        helper.testResultBodyTitle(this.quickErrorResult, quickErrorResponseTitle);
    }

    @Test
    public void testQuickStatus_ReturnsResultBodyWithCorrectDetailValue() {
        helper.testResultBodyDetail(this.quickErrorResult, quickErrorResponseDetail);
    }

    @Test
    public void testFullStatus_ReturnsResultBodyWithFieldsFromErrorPOJO() {
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode pojoJson = objectMapper.valueToTree(this.fullError);

        final JsonNode resultJsonData = helper.getResultJson(this.fullErrorResult).get("error");
        assertThat(resultJsonData).isEqualTo(pojoJson);
    }
}
