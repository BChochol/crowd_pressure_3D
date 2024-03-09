package agh.projects.crowd_pressure.types.http;

public record ErrorResponse(String message, ErrorCode code) {

    public enum ErrorCode {
        CRITICAL_ERROR,
        UNKNOWN_ERROR,
    }

}
