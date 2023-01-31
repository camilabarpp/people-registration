package camila.peopleregistration.controller;

import camila.peopleregistration.configuration.exception.NotFoundException;
import camila.peopleregistration.model.person.request.PersonRequest;
import camila.peopleregistration.model.person.response.PersonResponse;
import camila.peopleregistration.repository.AddressRepository;
import camila.peopleregistration.repository.PersonRepository;
import camila.peopleregistration.service.AddressService;
import camila.peopleregistration.service.PersonService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static camila.peopleregistration.stubs.AddressStubs.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PersonService personService;

    @Autowired
    private AddressService addressService;

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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(List.of(personRequest()))))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        //When
        assertThat(json).contains("Camila");
    }

    @Test
    @DisplayName("Deve criar uma pessoa com sucesso")
    void shouldCreateAPErsonWithSuccess() throws Exception {
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
    }

    @Test
    @DisplayName("Deve lançar MethodArgumentNotValidException quando tentar cadastrar uma pessoa sem nome")
    void shouldThrowsMethodArgumentNotValidException() throws Exception {
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





































//    @Test
//    @DisplayName("Deve procurar todas as pessoas")
//    void shouldFindAllPeople() throws Exception {
//        List<PersonResponse> expectedPeople = Arrays.asList(
//                personResponse(),
//                personResponse()
//        );
//        when(personService.findAll()).thenReturn(expectedPeople);
//
//        var expect = mapper.writeValueAsString(expectedPeople);
//
//        var result = mvc.perform(MockMvcRequestBuilders.get("/v1/person/"))
//                .andExpect(status().isOk())
//                .andExpect(content().json(expect))
//                .andReturn();
//
//        List<PersonResponse> people = mapper.readValue(
//                result.getResponse().getContentAsString(),
//                new TypeReference<List<PersonResponse>>(){}
//        );
//
//        assertEquals(people, expectedPeople);
//
//    }
//
//    @Test
//    @DisplayName("Deve procurar uma pessoa pelo ID")
//    void shouldbyPersonByIdWithSuccess() throws Exception {
//        Long id = 1L;
//        PersonResponse personResponse = personResponse();
//
//        when(personService.findById(id)).thenReturn(personResponse);
//
//        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/v1/person/{id}", id))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        PersonResponse person = mapper.readValue(
//                result.getResponse().getContentAsString(),
//                PersonResponse.class
//        );
//
//        assertEquals(personResponse, person);
//    }
//
//    @Test
//    @DisplayName("Deve atualizar uma pessoa com sucesso")
//    void shouldUpdateAPErsonWithSuccess() throws Exception {
//        Long id = 1L;
//        PersonRequest personRequest = personRequest();
//        PersonResponse personResponse = personResponse();
//
//        when(personService.update(id, personRequest)).thenReturn(personResponse);
//
//        MvcResult result = mvc.perform(put("/v1/person/{id}", id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content( mapper.writeValueAsString(personRequest)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String response = result.getResponse().getContentAsString();
//
//        assertEquals(response,  mapper.writeValueAsString(personResponse));
//    }
//
//
//    @Test
//    @DisplayName("Deve deletar uma pessoa com sucesso")
//    void shouldDeleteAPersonByIDWithSuccess() throws Exception {
//        Long id = 1L;
//
//        MvcResult result = mvc.perform(delete("/v1/person/{id}", id))
//                .andExpect(status().isNoContent())
//                .andReturn();
//
//        String response = result.getResponse().getContentAsString();
//        assertThat(response).isEmpty();
//    }
//
//    @Test
//    @DisplayName("Deve deletar todas as pessoas com suceso")
//    void shouldDeleteAllPeople() throws Exception {
//        MvcResult result = mvc.perform(delete("/v1/person/"))
//                .andExpect(status().isNoContent())
//                .andReturn();
//
//        String response = result.getResponse().getContentAsString();
//        assertThat(response).isEmpty();
//    }
//
//    @Test
//    @DisplayName("Deve lançar NotFoundException quando procurar uma pessoa pelo ID e não encontrar")
//    void shouldThrowNotFoundExceptionWhenFindPersonById() throws Exception {
//        Long id = 1L;
//        when(personService.findById(id)).thenThrow(new NotFoundException("Person not found"));
//
//       mvc.perform(MockMvcRequestBuilders.get("/v1/person/{id}", id))
//                .andExpect(status().isNotFound())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.message").value("Person not found"))
//                .andExpect(jsonPath("$.timestamp").exists())
//                .andReturn();
//    }
//
//    @Test
//    @DisplayName("Deve lançar NotFoundException quanto tentar deletar uma pessoa que não existe")
//    void shouldThrowNotFoundExceptionWhenDeletePerson() throws Exception {
//        Long id = 1L;
//
//        doThrow(new NotFoundException("Person not found")).when(personService).deleteById(id);
//
//        MvcResult result = mvc.perform(delete("/v1/person/{id}", id))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.timestamp").exists())
//                .andExpect(jsonPath("$.message").value("Person not found"))
//                .andExpect(jsonPath("$.field").value("NOT_FOUND"))
//                .andExpect(jsonPath("$.parameter").value("NotFoundException"))
//                .andReturn();
//
//        String response = result.getResponse().getContentAsString();
//        assertThat(response).contains("Person not found");
//    }

}
