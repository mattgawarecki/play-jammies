package com.mattgawarecki.play.http.handlers;

import com.mattgawarecki.play.http.JsonApiErrorResponse;
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
        final F.Promise<Result> resultPromise;

        switch (statusCode) {
            case Http.Status.BAD_REQUEST:
                resultPromise = onBadRequest(request, message);
                break;
            case Http.Status.FORBIDDEN:
                resultPromise = onForbidden(request, message);
                break;
            case Http.Status.NOT_FOUND:
                resultPromise = onNotFound(request, message);
                break;
            default:
                resultPromise = onOtherClientError(request, statusCode, message);
                break;
        }

        return resultPromise;
    }

    protected F.Promise<Result> onBadRequest(final Http.RequestHeader request, final String message) {
        return showDetailedErrors()
            ? JsonApiErrorResponse.badRequest(message)
            : JsonApiErrorResponse.badRequest();
    }

    protected F.Promise<Result> onForbidden(final Http.RequestHeader request, final String message) {
        return showDetailedErrors()
            ? JsonApiErrorResponse.forbidden(message)
            : JsonApiErrorResponse.forbidden();
    }

    protected F.Promise<Result> onNotFound(final Http.RequestHeader request, final String message) {
        return showDetailedErrors()
            ? JsonApiErrorResponse.notFound(message)
            : JsonApiErrorResponse.notFound();
    }

    protected F.Promise<Result> onOtherClientError(final Http.RequestHeader request, final int statusCode, final String message) {
        final String detail = showDetailedErrors() ? message : "There was a problem with the request.";
        return JsonApiErrorResponse.status(statusCode, "Unknown Client Error", detail);
    }

    @Override
    public F.Promise<Result> onServerError(final Http.RequestHeader request, final Throwable exception) {
        final F.Promise<Result> resultPromise;

        if (showDetailedErrors()) {
            final String detail = getExceptionMessage(exception);
            resultPromise = JsonApiErrorResponse.internalServerError(detail);
        } else {
            resultPromise = JsonApiErrorResponse.internalServerError();
        }

        return resultPromise;
    }

    private String getExceptionMessage(final Throwable throwable) {
        final UsefulException usefulException = HttpErrorHandlerExceptions.throwableToUsefulException(
            this.optionalSourceMapper.sourceMapper(), false, throwable);

        return usefulException.toString();
    }

    private Boolean showDetailedErrors() {
        return this.configuration.getBoolean("errors.showDetail", !this.environment.isProd());
    }
}
