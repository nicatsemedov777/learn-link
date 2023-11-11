package az.iktlab.learnlink.error;


import az.iktlab.learnlink.error.exception.AuthenticationException;
import az.iktlab.learnlink.error.exception.OTPSessionExpiredException;
import az.iktlab.learnlink.error.exception.ResourceAlreadyExistsException;
import az.iktlab.learnlink.error.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;
import java.util.stream.Collectors;
import static az.iktlab.learnlink.util.ResponseBuilder.buildErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgNotValidException(MethodArgumentNotValidException exception) {
        List<String> messages = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());

        return buildErrorResponse(messages, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException authenticationException) {
    return buildErrorResponse(authenticationException.getMessage(),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(OTPSessionExpiredException.class)
    public ResponseEntity<ErrorResponse> handleOTPSessionExpiredException(OTPSessionExpiredException exception) {
        return buildErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(ResourceAlreadyExistsException exception) {
        return buildErrorResponse(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return buildErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
