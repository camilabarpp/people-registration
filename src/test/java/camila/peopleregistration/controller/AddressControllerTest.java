package camila.peopleregistration.controller;

import camila.peopleregistration.repository.AddressRepository;
import camila.peopleregistration.repository.PersonRepository;
import camila.peopleregistration.service.AddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static camila.peopleregistration.stubs.AddressStubs.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AddressService service;

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
    @DisplayName("Deve retornar erro 404 quando buscar por id de pessoa inexistente")
    void shouldReturnNotFound() throws Exception {
        MvcResult result = mvc.perform(get("/v1/person/2/address/")
                .param("addressId", "1")
                .contentType(APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andReturn();

        String json = result.getResponse().getContentAsString();

        assertThat(json).contains("Person with id 2 not found");
    }

    @Test
    @DisplayName("Deve criar um novo endereço quando passar um id de pessoa existente")
    void createNewAddress_shouldReturnCreated() throws Exception {
        var person = personRepository.findAll();
        var personId = person.get(0).getId();

        MvcResult result = mvc.perform(post("/v1/person/{personId}/address/", personId)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createAddress())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        assertThat(json).contains("94020-070");
        assertThat(json).contains("Salgado Filho");
        assertThat(json).contains("RS");
    }

    @Test
    @DisplayName("Deve retornar uma lista de endereços quando buscar por id de pessoa existente")
    void shouldGetAddressesByPersonId() throws Exception {
        var person = personRepository.findAll();
        var id = person.get(0).getId();

        //When
        MvcResult result = mvc.perform(get("/v1/person/{id}/address/", id)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        assertThat(json).contains("94020070");
        assertThat(json).contains("Salgado Filho");
        assertThat(json).contains("RS");
    }

    @Test
    @DisplayName("Deve retornar erro ConstraintViolationException quando passar um cep inválido no cadastro")
    void createNewAddressWithoutNumber_shouldReturnBadRequest() throws Exception {
        var person = personRepository.findAll();
        var personId = person.get(0).getId();
        var addressId = person.get(0).getAddresses().get(0).getId();

        MvcResult result = mvc.perform(post("/v1/person/{personId}/address/", personId)
                        .param("addressId", addressId.toString())
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createAddressWithoutNumber())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        assertThat(json).contains("Missing required fields or invalid data");
    }


    @Test
    @DisplayName("Deve atualizar um endereço quando passar um id de pessoa existente")
    void updateAddressByPersonId_shouldReturnCreated() throws Exception {
        var person = personRepository.findAll();
        var personId = person.get(0).getId();
        var addressId = person.get(0).getAddresses().get(0).getId();
        //When
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/v1/person/{personId}/address/{addressId}", personId, addressId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(createAddress())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        //When
        assertThat(json).contains("94020-070");
        assertThat(json).contains("Salgado Filho");
        assertThat(json).contains("RS");
    }

    @Test
    @DisplayName("Deve retornar erro 404 quando tentar atualizar com um id de pessoa inexistente")
    void updateAddressByPersonId_shouldReturnNotFound() throws Exception {
        var id = personRepository.findAll();


        String request = mapper.writeValueAsString(createAddress());

        //When
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/v1/person/2/address/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        assertThat(json).contains("Person not found");
    }

    @Test
    @DisplayName("Deve deletar um endereço quando passar um id de pessoa existente")
    void deleteAddressByPersonId_shouldReturnCreated() throws Exception {
        var person = personRepository.findAll();
        var personId = person.get(0).getId();
        var addressId = person.get(0).getAddresses().get(0).getId();

        //When
        MvcResult result = mvc.perform(delete("/v1/person/{personId}/address/{addressId}", personId, addressId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        assertThat(json).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar erro 404 quando tentar deletar com um id de pessoa inexistente")
    void deleteAddressByPersonId_shouldReturnNotFound() throws Exception {
        //When
        MvcResult result = mvc.perform(delete("/v1/person/7/address/12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        String json = result.getResponse().getContentAsString();

        assertThat(json).contains("Address with ID 12 not found for person with ID 7");
    }
}