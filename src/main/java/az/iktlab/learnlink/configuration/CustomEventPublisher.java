package az.iktlab.learnlink.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;


    public void  publishEvent(Object event) {
         applicationEventPublisher.publishEvent(event);
    }
}
