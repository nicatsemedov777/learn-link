package az.iktlab.learnlink.util;

import az.iktlab.learnlink.error.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseBuilder {
    public static <T> ResponseEntity<T> buildResponse(T body, HttpStatus status) {
        return new ResponseEntity<>(body, status);
    }

    public static <T> ResponseEntity<T> buildResponse(T body) {
        return buildResponse(body, HttpStatus.OK);
    }

    public static ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus status) {
        return new ResponseEntity<>(new ErrorResponse(status, message), status);
    }

    public static ResponseEntity<ErrorResponse> buildErrorResponse(List<String> messages, HttpStatus status) {
        return new ResponseEntity<>(new ErrorResponse(status, messages), status);
    }

    public static ResponseEntity<HttpStatus> buildResponse() {
        return ResponseEntity.ok().build();
    }

    public static <T> ResponseEntity<T> buildResponse(T body, HttpHeaders headers, HttpStatus status) {
        return new ResponseEntity<>(body, headers, status);
    }

    public static <T> ResponseEntity<T> buildResponse(T body, HttpHeaders headers) {
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    public static ResponseEntity<HttpStatus> buildResponse(HttpStatus status) {
        return new ResponseEntity<>(status);
    }
}