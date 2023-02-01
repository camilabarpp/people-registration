package camila.peopleregistration.exceptions;

import camila.peopleregistration.configuration.exception.ApiExceptionHandler;
import camila.peopleregistration.configuration.exception.NotFoundException;
import camila.peopleregistration.configuration.exception.errorresponse.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(value = ApiExceptionHandlerTest.class)
@AutoConfigureMockMvc
class ApiExceptionHandlerTest {
    @InjectMocks
    private ApiExceptionHandler exceptionHandler;
    @Mock
    private ErrorResponse errorObject;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    @DisplayName("Deve lançar NotFoundException")
    void notFoundException() {
        ErrorResponse response = exceptionHandler
                .notFoundException(new NotFoundException(
                        errorObject.getMessage()));

        assertNotNull(response);
        assertEquals("NotFoundException", response.getParameter());
        assertEquals("NOT_FOUND", response.getField());
        assertNotNull(response.getTimestamp());
    }

    @Test
    @DisplayName("Deve lançar NullPointerException")
    void nullPointerException() {
        ErrorResponse response = exceptionHandler
                .nullPointerException(new NullPointerException(
                        errorObject.getParameter()));

        assertNotNull(response);
        assertEquals("NullPointerException", response.getParameter());
        assertEquals("INTERNAL_SERVER_ERROR", response.getField());
    }

    @Test
    @DisplayName("Deve lançar ResponseStatusException")
    void shouldThowsResponseStatusException() {
        ErrorResponse response = exceptionHandler
                .handleException(new ResponseStatusException(
                        HttpStatus.BAD_REQUEST));

        assertNotNull(response);
        assertEquals("ResponseStatusException", response.getParameter());
        assertEquals("BAD_REQUEST", response.getField());
    }

    @Test
    @DisplayName("Deve lançar HttpClientErrorException")
    void shouldThowsHttpClientErrorException() {
        ErrorResponse response = exceptionHandler
                .httpClientErrorException(new HttpClientErrorException(
                        HttpStatus.BAD_REQUEST
                ));

        assertNotNull(response);
        assertEquals("HttpClientErrorException", response.getParameter());
        assertEquals("BAD_REQUEST", response.getField());
    }

    @Test
    @DisplayName("Deve lançar Exception")
    void shouldThowsException() {
        ErrorResponse response = exceptionHandler
                .handleException(new Exception(
                        errorObject.getParameter()));
        assertNotNull(response);
        assertEquals("Exception", response.getParameter());
        assertEquals("INTERNAL_SERVER_ERROR", response.getField());
    }
}