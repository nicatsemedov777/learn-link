package az.iktlab.learnlink.controller;

import az.iktlab.learnlink.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static az.iktlab.learnlink.util.ResponseBuilder.buildResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gpt")
public class ChatBotController {

    private final OpenAIService openAIService;

    @GetMapping(value = "/ask", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getResponse(@RequestParam String query) {
        return buildResponse(openAIService.getResponse(query));
    }
}
