package az.iktlab.learnlink.service.impl;

import az.iktlab.learnlink.constants.OpenAIConstants;
import az.iktlab.learnlink.model.request.chatbot.ChatBotRequest;
import az.iktlab.learnlink.model.response.course.chatbot.ChatBotResponse;
import az.iktlab.learnlink.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OpenAIServiceImpl implements OpenAIService {
    
    private final OpenAIConstants openAIConstants;
    private static final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getResponse(@RequestParam String query) {
        ChatBotRequest request = new  ChatBotRequest(openAIConstants.getModel(), query);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization","Bearer "+openAIConstants.getApiKey());
        ChatBotResponse response = restTemplate.
                postForObject(openAIConstants.getApiUrl(),new HttpEntity<>(request,httpHeaders),ChatBotResponse.class);
        assert response != null;
        return response.getChoices().get(0).getMessage().getContent();
    }
}
