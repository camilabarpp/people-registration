package camila.peopleregistration.controller;

import camila.peopleregistration.configuration.exception.NotFoundException;
import camila.peopleregistration.model.address.entity.AddressEntity;
import camila.peopleregistration.service.AddressService;
import camila.peopleregistration.service.stubs.AddressStubs;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AddressController.class)
class AddressControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AddressService service;

    private final String url = "http://localhost:8080/v1/person/1/address";

    @Test
    @DisplayName("Deve retornar uma lista de endereços quando buscar por id de pessoa válido")
    void shouldGetAddressesByPersonId() throws Exception {
        Long personId = 1L;
        List<AddressEntity> addresses = Arrays.asList(
                new AddressEntity(),
                new AddressEntity()
        );
        when(service.getAddressesByPersonId(personId)).thenReturn(addresses);

        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

        verify(service, times(1)).getAddressesByPersonId(personId);
    }

    @Test
    @DisplayName("Deve retornar erro 404 quando buscar por id de pessoa inválido")
    void shouldReturnNotFound() throws Exception {
        Long personId = 1L;

        when(service.getAddressesByPersonId(personId)).thenThrow(new NotFoundException("Address not found"));

        mvc.perform(get(url))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.message").value("Address not found"))
                .andExpect(jsonPath("$.field").value("NOT_FOUND"))
                .andExpect(jsonPath("$.parameter").value("NotFoundException"));

        verify(service, times(1)).getAddressesByPersonId(personId);
    }

    @Test
    @DisplayName("Deve criar um novo endereço quando passar um id de pessoa válido")
    void createNewAddress_shouldReturnCreated() throws Exception {
        Long personId = 1L;
        AddressEntity address = new AddressEntity();
        address.setId(1L);
        when(service.createNewAddress(address, personId)).thenReturn(address);

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(address);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .param("personId", personId.toString()))
                .andExpect(status().isCreated());

        verify(service, times(1)).createNewAddress(address, personId);
    }

    @Test
    @DisplayName("Deve retornar erro 404 quando passar um id de pessoa inválido")
    void createNewAddress_shouldReturnNotFound() throws Exception {
        Long personId = 1L;
        AddressEntity address = new AddressEntity();
        address.setId(1L);
        when(service.createNewAddress(address, personId)).thenThrow(new NotFoundException("Error to create a address, please check your data!"));

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(address);
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .param("personId", personId.toString()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.message").value("Error to create a address, please check your data!"))
                .andExpect(jsonPath("$.field").value("NOT_FOUND"))
                .andExpect(jsonPath("$.parameter").value("NotFoundException"));

        verify(service, times(1)).createNewAddress(address, personId);
    }

    @Test
    @DisplayName("Deve atualizar um endereço quando passar um id de pessoa válido")
    void updateAddressByPersonId_shouldReturnCreated() throws Exception {
        var addressExpect = AddressStubs.createAddress();
        var addressResponse = AddressStubs.createAddress();
        Long personId = 1L;
        Long addressId = 1L;

        when(service.updateAddressByPersonId(addressExpect, personId, addressId)).thenReturn(addressResponse);

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(addressResponse);
        mvc.perform(put("http://localhost:8080/v1/person/{personId}/address/{addressId}", personId, addressId)
                .contentType(APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isCreated());

        verify(service, times(1)).updateAddressByPersonId(addressExpect, personId, addressId);
    }

    @Test
    @DisplayName("Deve retornar erro 404 quando tentar atualizar com um id de pessoa inválido")
    void updateAddressByPersonId_shouldReturnNotFound() throws Exception {
        var addressExpect = AddressStubs.createAddress();
        var addressResponse = AddressStubs.createAddress();
        Long personId = 1L;
        Long addressId = 1L;

        when(service.updateAddressByPersonId(addressExpect, personId, addressId))
                .thenThrow(new NotFoundException("Error to update a address, please check your data!"));

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(addressExpect);
        mvc.perform(put("http://localhost:8080/v1/person/{personId}/address/{addressId}", personId, addressId)
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.message").value("Error to update a address, please check your data!"))
                .andExpect(jsonPath("$.field").value("NOT_FOUND"))
                .andExpect(jsonPath("$.parameter").value("NotFoundException"));;
    }

    @Test
    @DisplayName("Deve deletar um endereço quando passar um id de pessoa válido")
    void deleteAddressByPersonId_shouldReturnCreated() throws Exception {
//        var addressExpect = AddressStubs.createAddress();
//        var addressResponse = AddressStubs.createAddress();
//        Long personId = 1L;
//        Long addressId = 1L;
//
//        when(service.deleteAddressByPersonId(personId, addressId));
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        String json = mapper.writeValueAsString(addressResponse);
//        mvc.perform(put("http://localhost:8080/v1/person/{personId}/address/{addressId}", personId, addressId)
//                .contentType(APPLICATION_JSON)
//                .content(json)
//        ).andExpect(status().isCreated());
//
//        verify(service, times(1)).updateAddressByPersonId(addressExpect, personId, addressId);
    }

}