package az.iktlab.learnlink.model.response.course.chatbot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatBotResponse {
    private List<Choice> choices;

}