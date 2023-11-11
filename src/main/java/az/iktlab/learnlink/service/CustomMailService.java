package az.iktlab.learnlink.service;

public interface CustomMailService {
    void sendOTPMail(String to, String otp);
}
