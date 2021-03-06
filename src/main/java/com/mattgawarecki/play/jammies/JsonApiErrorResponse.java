package com.mattgawarecki.play.jammies;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mattgawarecki.play.jammies.json.JsonApiError;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

public class JsonApiErrorResponse {
    public static Result status(final int httpStatusCode, final JsonApiError error) {
        final JsonNode errorJson = Json.toJson(error);

        final ObjectNode wrapped = Json.newObject();
        wrapped.set("error", errorJson);

        return Results.status(httpStatusCode, wrapped).as("application/vnd.api+json");
    }

    public static Result status(final int httpStatusCode, final String title, final String detail) {
        final JsonApiError error = new JsonApiError();
        error.setStatus(httpStatusCode);
        error.setTitle(title);
        error.setDetail(detail);

        return status(httpStatusCode, error);
    }

    public static Result badRequest() {
        return badRequest("The request could not be processed by the server.");
    }

    public static Result badRequest(final String detail) {
        final int statusCode = Http.Status.BAD_REQUEST;
        final JsonApiError error = buildError(statusCode, "Bad Request", detail);
        return status(statusCode, error);
    }

    public static Result forbidden() {
        return forbidden("The requested operation was not permitted.");
    }

    public static Result forbidden(final String detail) {
        final int statusCode = Http.Status.FORBIDDEN;
        final JsonApiError error = buildError(statusCode, "Forbidden", detail);
        return status(statusCode, error);
    }

    public static Result notFound() {
        return notFound("The requested resource could not be found.");
    }

    public static Result notFound(final String detail) {
        final int statusCode = Http.Status.NOT_FOUND;
        final JsonApiError error = buildError(statusCode, "Not Found", detail);
        return status(statusCode, error);
    }

    public static Result internalServerError() {
        return internalServerError("The server encountered an error while processing the request.");
    }

    public static Result internalServerError(final String detail) {
        final int statusCode = Http.Status.INTERNAL_SERVER_ERROR;
        final JsonApiError error = buildError(statusCode, "Internal Server Error", detail);
        return status(statusCode, error);
    }

    private static JsonApiError buildError(final int statusCode, final String title, final String detail) {
        final JsonApiError error = new JsonApiError();
        error.setStatus(statusCode);
        error.setTitle(title);
        error.setDetail(detail);

        return error;
    }
}
