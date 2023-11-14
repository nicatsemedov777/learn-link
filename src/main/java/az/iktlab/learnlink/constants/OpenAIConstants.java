package az.iktlab.learnlink.constants;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class OpenAIConstants {

    @Value("${model}")
    private String model;

    @Value("${apiUrl}")
    private String apiUrl;

    @Value("${apiKey}")
    private String apiKey;
}
