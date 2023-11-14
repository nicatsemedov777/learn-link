package az.iktlab.learnlink.model.request.chatbot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatBotRequest {
    String model;
    List<Message> messages = new ArrayList<>();

    public ChatBotRequest(String model, String query) {
        this.model = model;
        this.messages.add(new Message("user", query));
    }
}

