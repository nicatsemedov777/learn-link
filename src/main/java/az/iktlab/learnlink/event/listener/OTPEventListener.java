package az.iktlab.learnlink.event.listener;

import az.iktlab.learnlink.event.OTPEvent;
import az.iktlab.learnlink.service.CustomMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OTPEventListener {
    private final CustomMailService mailService;

    @Async
    @EventListener
    public void onEvent(OTPEvent event) {
        mailService.sendOTPMail(event.getEmail(), event.getOtpCode());
    }
}
