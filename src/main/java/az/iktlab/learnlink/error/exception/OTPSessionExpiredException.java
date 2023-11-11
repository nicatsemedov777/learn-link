package az.iktlab.learnlink.error.exception;

public class OTPSessionExpiredException extends RuntimeException {
    public OTPSessionExpiredException() {
    }

    public OTPSessionExpiredException(String message) {
        super(message);
    }

    public OTPSessionExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public OTPSessionExpiredException(Throwable cause) {
        super(cause);
    }
}
