package az.iktlab.learnlink.controller;

import az.iktlab.learnlink.model.request.chatbot.ChatBotRequest;
import az.iktlab.learnlink.model.response.course.chatbot.ChatBotResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gpt")
public class ChatGptController {
    private String model="gpt-3.5-turbo";
    private String apiUrl="https://api.openai.com/v1/chat/completions";
    private String apiKey ="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";

    private static RestTemplate restTemplate = new RestTemplate();

    @GetMapping(value = "/ask",produces = MediaType.APPLICATION_JSON_VALUE)
    public String getResponse(@RequestParam String query) {
        ChatBotRequest request = new  ChatBotRequest(model,query);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization","Bearer "+apiKey);
        ChatBotResponse response = restTemplate.postForObject(apiUrl,new HttpEntity<>(request,httpHeaders),ChatBotResponse.class);
        return response.getChoices().get(0).getMessage().getContent();
    }
}
