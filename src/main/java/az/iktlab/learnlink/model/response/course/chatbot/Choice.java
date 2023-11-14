package az.iktlab.learnlink.model.response.course.chatbot;

import az.iktlab.learnlink.model.request.chatbot.Message;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@AllArgsConstructor
public class Choice {
    Integer index;
    Message message;
}
