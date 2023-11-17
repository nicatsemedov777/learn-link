package az.iktlab.learnlink.event.listener;

import az.iktlab.learnlink.event.NewCourseNotificationEvent;
import az.iktlab.learnlink.service.CustomMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewCourseNotificationEventListener {
    private final CustomMailService mailService;

    @Async
    @EventListener
    public void onEvent(NewCourseNotificationEvent event) {
        mailService.sendNewCourseNotification(event.getEmails(),
                String.format("Author %s published new course : %s.",event.getAuthorName(), event.getCourseName()));
    }
}
