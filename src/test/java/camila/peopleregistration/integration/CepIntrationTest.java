package camila.peopleregistration.integration;

import camila.peopleregistration.configuration.exception.NotFoundException;
import camila.peopleregistration.model.address.entity.AddressEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static camila.peopleregistration.stubs.AddressStubs.createAddress;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = IntegrationCep.class)
@EnableWebMvc
@AutoConfigureMockMvc
class CepIntegrationTest {

    @InjectMocks
    private IntegrationCep integrationCep;

    private static ClientAndServer server;

    @BeforeAll
    static void startServer(){
        server = startClientAndServer();
    }

    @BeforeEach
    void setupClass() {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(String.format("http://localhost:%d", server.getPort()))
                .build();

        integrationCep = new IntegrationCep(restTemplate);
    }

    @AfterAll
    static void stopServer(){
        server.close();
    }

    @Test
    @DisplayName("Deve pesquisar CEP na api externa")
    void whenFindCepReturnCepIntegration() throws JsonProcessingException {
        AddressEntity expect = createAddress();

        HttpRequest request =  HttpRequest.request()
                .withPath("/ws/94020070/json")
                .withMethod("GET");

        ObjectMapper mapper = new ObjectMapper();

        var body = mapper.writeValueAsString(createAddress());

        HttpResponse response = HttpResponse.response(body)
                .withStatusCode(HttpStatus.OK.value())
                .withHeader(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE);

        server.when(request).respond(response);

        var actual = integrationCep.findCep("94020070");
        assertEquals(expect, actual);
    }

    @Test
    @DisplayName("Deve lançar ApiNotFoundException quando o cep estiver em branco")
    void shouldThrowsApiNotFoundException() {
        IntegrationCep integration = mock(IntegrationCep.class);

        doThrow(NotFoundException.class)
                .when(integration).findCep("94020071");

        assertThrows(NotFoundException.class, () -> integration.findCep("94020071"));
    }
}