package camila.peopleregistration.service;

import camila.peopleregistration.configuration.exception.NotFoundException;
import camila.peopleregistration.integration.IntegrationCep;
import camila.peopleregistration.model.address.entity.AddressEntity;
import camila.peopleregistration.model.person.entity.PersonEntity;
import camila.peopleregistration.model.person.request.PersonRequest;
import camila.peopleregistration.repository.AddressRepository;
import camila.peopleregistration.repository.PersonRepository;
import camila.peopleregistration.stubs.AddressStubs;
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

import static camila.peopleregistration.stubs.AddressStubs.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
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

        when(integration.findCep(cep))
                .thenReturn(expected);

        var result = service.searchCep(personRequest);
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Deve lançar NotFoundException quando o CEP for vazio")
    void searchCep_ShouldThrowNotFoundException() {
        PersonRequest personRequest = personRequest();
        personRequest.getAddresses().get(0).setCep("");

        assertThrows(NotFoundException.class, () ->
                service.searchCep(personRequest));
    }


    @Test
    @DisplayName("Deve retornar endereços pelo id da pessoa")
    void getAddressesByPersonId_ShouldReturnAddressList() {
        Long personId = 1L;
        PersonEntity personEntity = personEntity();
        List<AddressEntity> expected = personEntity.getAddresses();

        when(personRepository.findById(personId))
                .thenReturn(Optional.of(personEntity));

        var result = service.getAddressesByPersonId(personId);
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Deve lançar NotFoundException quando não achar o ID")
    void shouldThrowNotFoundExceptionWhenIdNotFound() {
        Long id = 1L;

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getAddressesByPersonId(id));
    }

    @Test
    @DisplayName("Deve criar um novo endereço com sucesso")
    void createAddress_ShouldReturnNewAddress() {
        AddressEntity expected = AddressStubs.createAddress();
        AddressEntity addressEntity = AddressStubs.createAddress();
        PersonEntity personEntity = personEntity();

        when(integration.findCep("94020070"))
                .thenReturn(expected);
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(personEntity));
        when(repository.save(addressEntity))
                .thenReturn(expected);

        personEntity.setAddresses(new ArrayList<>(personEntity.getAddresses()));
        AddressEntity result = service.createNewAddress(addressEntity, 1L);

        assertNotNull(expected);
        assertEquals(expected, result);
        assertEquals(2, personEntity.getAddresses().size());
        verify(repository, times(1)).save(expected);
    }

    @Test
    void createNewAddress_PersonNotFound() {
        Long id = 1L;
        AddressEntity address = new AddressEntity();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.createNewAddress(address, id));
        verify(integration, never()).findCep(anyString());
        verify(repository, never()).save(any(AddressEntity.class));
        verify(repository, never()).save(address);
    }

    @Test
    @DisplayName("Deve atualizar um endereço apartir de um ID válido")
    void shouldUpdateAddressByPersonId() {
        var personEntity = personEntity();
        var olderAddress = createAddress();
        var addressUpdated = createAddress2();
        var cep = personEntity.getAddresses().get(0).getCep();
        var addressId = personEntity.getAddresses().get(0).getId();
        var personId = personEntity.getId();

        when(repository.findById(addressId))
                .thenReturn(Optional.of(olderAddress));
        when(integration.findCep(cep))
                .thenReturn(addressUpdated);
        when(repository.save(addressUpdated))
                .thenReturn(addressUpdated);

        var result = service.updateAddressByPersonId(olderAddress, personId, addressId);

        assertEquals(addressUpdated, result);
        verify(integration, times(1)).findCep(cep);
        verify(repository, times(1)).save(addressUpdated);
    }

    @Test
    @DisplayName("Deve lançar NotFoundException quando não achar pelo ID")
    void shouldThrowNotFoundExceptionWhenFindInvalidId() {
        var personEntity = personEntity();
        var olderAddress = createAddress();
        var addressId = personEntity.getAddresses().get(0).getId();
        var personId = personEntity.getId();

        when(repository.findById(addressId))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.updateAddressByPersonId(olderAddress, personId, addressId));
        verify(integration, never()).findCep(anyString());
        verify(repository, never()).save(any(AddressEntity.class));
    }

    @Test
    @DisplayName("Deve deletar um endereço com sucesso")
    void shouldDeleteAddressWithSuccess() {
        Long personId = 1L;
        Long addressId = 2L;
        AddressEntity addressEntity = createAddress();

        when(repository.findById(addressId))
                .thenReturn(Optional.of(addressEntity));

        service.deleteAddressByPersonId(personId, addressId);

        verify(repository, times(1)).findById(addressId);
        verify(repository, times(1)).deleteById(addressId);
    }

    @Test
    @DisplayName("Deve lançar NotFoundException quando tentar deletar pelo id inválido")
    void shouldDeleteAddressByPersonId_AddressNotFound() {
        Long personId = 1L;
        Long addressId = 2L;
        when(repository.findById(addressId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.deleteAddressByPersonId(personId, addressId));
        verify(repository, times(1)).findById(addressId);
        verify(repository, never()).deleteById(addressId);
    }

}