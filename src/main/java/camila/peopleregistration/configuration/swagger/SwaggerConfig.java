package camila.peopleregistration.configuration.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    //http://localhost:8080/swagger-ui/index.html#/
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(metaData())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
    @Bean
    ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Cep API")
                .description("\"Api de cadastro de pessoas com busca de CEP e temperatura na api externa\"")
                .version("1.0.0")
                .contact(new Contact("Camila Ramão Barpp",
                        "https://www.github.com/camilabarpp",
                        "milabarpp5@gmail.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .build();
    }
}

