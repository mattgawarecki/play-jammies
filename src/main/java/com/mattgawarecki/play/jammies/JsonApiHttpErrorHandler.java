package com.mattgawarecki.play.jammies;

import play.Configuration;
import play.Environment;
import play.api.OptionalSourceMapper;
import play.api.UsefulException;
import play.api.http.HttpErrorHandlerExceptions;
import play.http.HttpErrorHandler;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;


public class JsonApiHttpErrorHandler implements HttpErrorHandler {
    private final Configuration configuration;
    private final Environment environment;
    private final OptionalSourceMapper optionalSourceMapper;

    @Inject
    public JsonApiHttpErrorHandler(final Configuration configuration,
                                   final Environment environment,
                                   final OptionalSourceMapper optionalSourceMapper) {
        this.configuration = configuration;
        this.environment = environment;
        this.optionalSourceMapper = optionalSourceMapper;
    }

    @Override
    public F.Promise<Result> onClientError(final Http.RequestHeader request, final int statusCode, final String message) {
        final Result result;

        switch (statusCode) {
            case Http.Status.BAD_REQUEST:
                result = onBadRequest(request, message);
                break;
            case Http.Status.FORBIDDEN:
                result = onForbidden(request, message);
                break;
            case Http.Status.NOT_FOUND:
                result = onNotFound(request, message);
                break;
            default:
                result = onOtherClientError(request, statusCode, message);
                break;
        }

        return F.Promise.pure(result);
    }

    protected Result onBadRequest(final Http.RequestHeader request, final String message) {
        return showDetailedErrors()
            ? JsonApiErrorResponse.badRequest(message)
            : JsonApiErrorResponse.badRequest();
    }

    protected Result onForbidden(final Http.RequestHeader request, final String message) {
        return showDetailedErrors()
            ? JsonApiErrorResponse.forbidden(message)
            : JsonApiErrorResponse.forbidden();
    }

    protected Result onNotFound(final Http.RequestHeader request, final String message) {
        return showDetailedErrors()
            ? JsonApiErrorResponse.notFound(message)
            : JsonApiErrorResponse.notFound();
    }

    protected Result onOtherClientError(final Http.RequestHeader request, final int statusCode, final String message) {
        final String detail = showDetailedErrors() ? message : "There was a problem with the request.";
        return JsonApiErrorResponse.status(statusCode, "Unknown Client Error", detail);
    }

    @Override
    public F.Promise<Result> onServerError(final Http.RequestHeader request, final Throwable exception) {
        final Result result;
        if (showDetailedErrors()) {
            final String detail =
                String.format("%s: %s", exception.getClass().getCanonicalName(), exception.getMessage());
            result = JsonApiErrorResponse.internalServerError(detail);
        } else {
            result = JsonApiErrorResponse.internalServerError();
        }

        return F.Promise.pure(result);
    }

    private Boolean showDetailedErrors() {
        return this.configuration.getBoolean("jammies.showDetailedErrors", !this.environment.isProd());
    }
}
