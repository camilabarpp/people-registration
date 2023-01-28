package camila.peopleregistration.service;

import camila.peopleregistration.configuration.exception.NotFoundException;
import camila.peopleregistration.integration.IntegrationCep;
import camila.peopleregistration.model.address.entity.AddressEntity;
import camila.peopleregistration.model.person.entity.PersonEntity;
import camila.peopleregistration.model.person.mapper.PersonMapper;
import camila.peopleregistration.model.person.request.PersonRequest;
import camila.peopleregistration.repository.AddressRepository;
import camila.peopleregistration.repository.PersonRepository;
import camila.peopleregistration.service.stubs.AddressStubs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static camila.peopleregistration.model.person.mapper.PersonMapper.toEntity;
import static camila.peopleregistration.service.stubs.AddressStubs.*;
import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class AddressServiceTest {

    @Mock
    private IntegrationCep integration;

    @Mock
    private AddressRepository repository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private AddressService service;

    @Test
    @DisplayName("Pesquisar CEP com sucesso")
    void searchCep_ShouldReturnNewAddress() {
        String cep = "94020070";
        PersonRequest personRequest = personRequest();
        AddressEntity expected = AddressStubs.createAddress();

        when(integration.findCep(cep)).thenReturn(expected);

        var result = service.searchCep(personRequest);
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Deve lançar NotFoundException quando o CEP for vazio")
    void searchCep_ShouldThrowNotFoundException() {
        PersonRequest personRequest = personRequest();
        personRequest.getAddresses().get(0).setCep("");

        assertThrows(NotFoundException.class, () -> {
            service.searchCep(personRequest);
        });
    }

    @Test
    @DisplayName("Deve criar um novo endereço com sucesso")
    void createAddress_ShouldReturnNewAddress() {
        AddressEntity expected = AddressStubs.createAddress();

        AddressEntity addressEntity = AddressStubs.createAddress();

        PersonEntity personEntity = personEntity();

        when(integration.findCep("94020070")).thenReturn(expected);
        when(personRepository.findById(1L)).thenReturn(Optional.of(personEntity));
        when(repository.save(addressEntity)).thenReturn(expected);

        personEntity.setAddresses(new ArrayList<>(personEntity.getAddresses()));
        AddressEntity result = service.createAddress(addressEntity, 1L);
        assertEquals(expected, result);
    }

    @Test
    void getAddressesByPersonId() {
    }
}