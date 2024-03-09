package agh.projects.crowd_pressure.types.http;

import java.util.Optional;

public record HTTPResponse<T>(Optional<OkResponse<T>> response, Optional<ErrorResponse> errors) {

    public static <T> HTTPResponse<T> ok(T body) {
        return new HTTPResponse<>(Optional.of(new OkResponse<>(body)), Optional.empty());
    }

    public static <T> HTTPResponse<T> error(String message, ErrorResponse.ErrorCode code) {
        return new HTTPResponse<>(Optional.empty(), Optional.of(new ErrorResponse(message, code)));
    }

}
