package camila.peopleregistration.integration;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplateCep() {
        return new RestTemplateBuilder()
                .rootUri("https://viacep.com.br")
//                .errorHandler(new ErroHandler())
                .build();
    }
}
