package camila.peopleregistration.controller;

import camila.peopleregistration.configuration.exception.NotFoundException;
import camila.peopleregistration.repository.AddressRepository;
import camila.peopleregistration.repository.PersonRepository;
import camila.peopleregistration.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.HttpClientErrorException;

import static camila.peopleregistration.stubs.AddressStubs.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        personRepository.save(personEntity());
        addressRepository.save(createAddress());
    }

    @AfterEach
    void tearDown() {
        personRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve procurar todas as pessoas")
    void shouldFindAllPeople() throws Exception {
        //When
        MvcResult result = mvc.perform(get("/v1/person/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        //When
        assertThat(json).contains("Camila");
    }

    @Test
    @DisplayName("Deve procurar uma pessoa pelo id")
    void shouldFindAPersonByIdWithSuccess() throws Exception {
        //When
        MvcResult result = mvc.perform(get("/v1/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        //When
        assertThat(json).contains("Camila");
        assertThat(json).contains("02/07/1996");
    }

    @Test
    @DisplayName("Deve lançar NotFoundException quando procurar uma pessoa pelo id inexistente")
    void shouldThrowsNotFoundExceptionWhenFindByInvalidId() throws Exception {
        //When
        MvcResult result = mvc.perform(get("/v1/person/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(personRequest())))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        //When
        assertThat(json).contains("Person not found");
        assertThrows(NotFoundException.class, () -> personService.findById(2L));
    }

    @Test
    @DisplayName("Deve criar uma pessoa com sucesso")
    void shouldCreateAPersonWithSuccess() throws Exception {
        //When
        MvcResult result = mvc.perform(post("/v1/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(personRequest())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        //When
        assertThat(json).contains("Camila");
        assertThat(json).contains("02/07/1996");
    }

    @Test
    @DisplayName("Deve lançar MethodArgumentNotValidException quando tentar cadastrar uma pessoa sem nome")
    void shouldThrowsMethodArgumentNotValidExceptionWhenTrySaveAInvalidPerson() throws Exception {
        //When
        MvcResult result = mvc.perform(post("/v1/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(personRequestWithoutName())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        //When
        assertThat(json).contains("Name can not be null or empty");
    }

    @Test
    @DisplayName("Deve lancar DataIntegrityViolationException quando tentar cadastrar uma pessoa com nome maior que 100 caracteres")
    void shouldThrowsContraintViolationExceptionWhenTrySaveAInvalidPerson() throws Exception {
        //When
        MvcResult result = mvc.perform(post("/v1/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(personRequestWithInvalidName())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        //When
        assertThat(json).contains("Impossible insert do database, object with invalid size");
        assertThrows(DataIntegrityViolationException.class, () -> personService.create(personRequestWithInvalidName()));
    }

    @Test
    @DisplayName("Deve lançar HttpClientErrorException quando tentar cadastrar uma pessoa com cep invalido")
    void shouldThrowsHttpClientErrorExceptionWhenTrySaveAPersonWithInvalidCep() throws Exception {
        MvcResult result = mvc.perform(post("/v1/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(personRequestWithInvalidCep())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        assertThat(json).contains("Insert a valid CEP");
        assertThrows(HttpClientErrorException.class, () -> personService.create(personRequestWithInvalidCep()));
    }

    @Test
    @DisplayName("Deve atualizar uma pessoa com sucesso")
    void shouldUpdateAPersonWithSuccess() throws Exception {
        String request = mapper.writeValueAsString(personRequest());
        //When
        MvcResult result = mvc.perform(get("/v1/person/")
                        .param("id", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        //When
        assertThat(json).contains("Camila");
        assertThat(json).contains("02/07/1996");

    }

    @Test
    @DisplayName("Deve lançar NotFoundException quando tentar atualizar uma pessoa inexistente")
    void shouldThrowsNotFoundExceptionWhenTryUpdateAPerson() throws Exception {
        //When
        MvcResult result = mvc.perform(put("/v1/person/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(personRequest())))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        //When
        assertThat(json).contains("Person not found");
        assertThrows(NotFoundException.class, () -> personService.update(2L, personRequest()));
    }

    @Test
    @DisplayName("Deve deletar uma pessoa com sucesso")
    void shouldDeleteAPersonWithSuccess() throws Exception {
        MvcResult result = mvc.perform(delete("/v1/person/")
                        .param("id", "10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(personRequest())))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        assertThat(json).isEmpty();
    }

    @Test
    @DisplayName("Deve lançar NotFoundException quando tentar deletar uma pessoa inexistente")
    void shouldThrowsNotFoundExceptionWhenTryDeleteAPerson() throws Exception {
        //When
        MvcResult result = mvc.perform(delete("/v1/person/2"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        //When
        assertThat(json).contains("Person not found");
        assertThrows(NotFoundException.class, () -> personService.deleteById(2L));
    }

    @Test
    @DisplayName("Deve deletar todas as pessoas com sucesso")
    void shouldDeleteAllPeopleWithSuccess() throws Exception {
        //When
        MvcResult result = mvc.perform(delete("/v1/person/"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        //When
        assertThat(json).isEmpty();
    }
}