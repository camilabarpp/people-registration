package camila.peopleregistration.controller;

import camila.peopleregistration.model.person.response.PersonResponse;
import camila.peopleregistration.repository.PersonRepository;
import camila.peopleregistration.service.AddressService;
import camila.peopleregistration.service.PersonService;
import camila.peopleregistration.stubs.AddressStubs;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static camila.peopleregistration.stubs.AddressStubs.personResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    PersonControllerTest(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Test
    @DisplayName("Deve procurar todas as pessoas")
    void shouldFindAllPeople() throws Exception {
        // Arrange
        List<PersonResponse> expectedPeople = Arrays.asList(
                personResponse(),
                personResponse()
        );
        when(personService.findAll()).thenReturn(expectedPeople);

        ObjectMapper mapper = new ObjectMapper();

        var expect = mapper.writeValueAsString(expectedPeople);

        mvc.perform(MockMvcRequestBuilders.get("/v1/person/"))
                .andExpect(status().isOk())
                .andExpect(content().json(expect))
                .andReturn();
    }

    @Test
    @DisplayName("Deve procurar uma pessoa pelo ID")
    void shouldbyPersonByIdWithSuccess() {

    }


}