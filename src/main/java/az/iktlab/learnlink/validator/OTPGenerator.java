package az.iktlab.learnlink.validator;

import java.security.SecureRandom;

public class OTPGenerator {
    private static final int MIN_OTP_VALUE = 100000;
    private static final int MAX_OTP_VALUE = 999999;
    private static final SecureRandom random = new SecureRandom();

    public static String generate() {
        int otp = random.nextInt(MAX_OTP_VALUE - MIN_OTP_VALUE + 1) + MIN_OTP_VALUE;
        return Integer.toString(otp);
    }
}
