package az.iktlab.learnlink.service.impl;


import az.iktlab.learnlink.constants.SecurityConstants;
import az.iktlab.learnlink.service.CustomMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomMailServiceImpl implements CustomMailService {
    private final JavaMailSender mailSender;
    private final SecurityConstants securityConstants;

    @Override
    public void sendOTPMail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(securityConstants.getEmail());
        message.setTo(to);
        message.setText("OTP code: " + otp);
        message.setSubject("Account Recovery");

        mailSender.send(message);
    }
}
