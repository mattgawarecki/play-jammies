package com.mattgawarecki.play.http;

import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class JsonApiErrorResponseTests_CommonResponseMethods {
    @Parameterized.Parameters(name = "{0} ({1})")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            {
                Http.Status.BAD_REQUEST, "Bad Request",
                "The request could not be processed by the server.",
                JsonApiErrorResponse.badRequest(), JsonApiErrorResponse.badRequest(expectedCustomDetail)
            },
            {
                Http.Status.FORBIDDEN, "Forbidden",
                "The requested operation was not permitted.",
                JsonApiErrorResponse.forbidden(), JsonApiErrorResponse.forbidden(expectedCustomDetail)
            },
            {
                Http.Status.NOT_FOUND, "Not Found",
                "The requested resource could not be found.",
                JsonApiErrorResponse.notFound(), JsonApiErrorResponse.notFound(expectedCustomDetail)
            },
            {
                Http.Status.INTERNAL_SERVER_ERROR, "Internal Server Error",
                "The server encountered an error while processing the request.",
                JsonApiErrorResponse.internalServerError(), JsonApiErrorResponse.internalServerError(expectedCustomDetail)
            }
        });
    }

    private static String expectedCustomDetail = "my_detail_value";
    private JsonApiErrorResponseTestHelper helper;

    private final Result resultWithDefaultDetail;
    private final Result resultWithCustomDetail;
    private final List<Result> testedResults;

    private final int expectedStatusCode;
    private final String expectedTitle;
    private final String expectedDefaultDetail;

    public JsonApiErrorResponseTests_CommonResponseMethods(final int expectedStatusCode,
                                                           final String expectedTitle,
                                                           final String expectedDefaultDetail,
                                                           final F.Promise<Result> defaultResultPromiseGetter,
                                                           final F.Promise<Result> customDetailResultPromiseGetter) {
        this.resultWithDefaultDetail = defaultResultPromiseGetter.get(0);
        this.resultWithCustomDetail = customDetailResultPromiseGetter.get(0);
        this.testedResults = Arrays.asList(this.resultWithDefaultDetail, this.resultWithCustomDetail);

        this.expectedStatusCode = expectedStatusCode;
        this.expectedTitle = expectedTitle;
        this.expectedDefaultDetail = expectedDefaultDetail;
    }

    @Before
    public void init() {
        helper = new JsonApiErrorResponseTestHelper(new SoftAssertions());
    }

    @After
    public void finish() {
        helper.assertAll();
    }

    @Test
    public void testAllMethods_ReturnsResultWithCorrectHttpStatusCode() {
        testedResults.stream().forEach(r -> helper.testResultHttpStatusCode(r, this.expectedStatusCode));
    }

    @Test
    public void testAllMethods_ReturnsResultWithCorrectContentType() {
        testedResults.stream().forEach(helper::testResultContentType);
    }

    @Test
    public void testAllMethods_ResultBodyWrappedInErrorField() {
        testedResults.stream().forEach(helper::testResultBodyIsWrapped);
    }

    @Test
    public void testAllMethods_ReturnsResultBodyWithCorrectStatusValue() {
        testedResults.stream().forEach(r -> helper.testResultBodyStatus(r, this.expectedStatusCode));
    }

    @Test
    public void testAllMethods_ReturnsResultBodyWithCorrectTitleValue() {
        testedResults.stream().forEach(r -> helper.testResultBodyTitle(r, this.expectedTitle));
    }

    @Test
    public void testAllMethods_DetailNotDefined_ReturnsResultBodyWithCorrectPredefinedDetailValue() {
        helper.testResultBodyDetail(this.resultWithDefaultDetail, this.expectedDefaultDetail);
    }

    @Test
    public void testAllMethods_DetailDefined_ReturnsResultBodyWithSuppliedDetailValue() {
        helper.testResultBodyDetail(this.resultWithCustomDetail, expectedCustomDetail);
    }
}
