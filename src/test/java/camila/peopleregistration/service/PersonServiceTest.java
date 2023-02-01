package camila.peopleregistration.service;

import camila.peopleregistration.configuration.exception.NotFoundException;
import camila.peopleregistration.model.address.entity.AddressEntity;
import camila.peopleregistration.model.person.entity.PersonEntity;
import camila.peopleregistration.model.person.request.PersonRequest;
import camila.peopleregistration.model.person.response.PersonResponse;
import camila.peopleregistration.repository.AddressRepository;
import camila.peopleregistration.repository.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static camila.peopleregistration.model.person.mapper.PersonMapper.*;
import static camila.peopleregistration.stubs.AddressStubs.*;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PersonServiceTest {

    @InjectMocks
    private PersonService service;

    @Mock
    private AddressService addressService;

    @Mock
    private PersonRepository repository;

    @Mock
    private AddressRepository addressRepository;

    @Test
    @DisplayName("Deve mostrar todas as pessoas")
    void findAll_WithSuccess() {
        List<PersonResponse> expectedPeople = List.of(personResponse(), personResponse());
        var request = List.of(personEntity(), personEntity());

        when(repository.findAll()).thenReturn(request);

        List<PersonResponse> actualPeople = service.findAll();

        assertEquals(2, actualPeople.size());
        assertEquals(expectedPeople, actualPeople);
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Deve mostrar uma pessoa")
    void findById_WithSuccess() {
        PersonResponse expectedPerson = personResponse();
        var request = personEntity();

        when(repository.findById(1L)).thenReturn(Optional.of(request));

        PersonResponse actualPerson = service.findById(1L);

        assertEquals(expectedPerson, actualPerson);
        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("Deve lancar NotFoundException quando nao encontrar uma pessoa")
    void findById_ShouldThrowNotFoundException() {
        var person = new PersonEntity();
        when(repository.findById(1L)).thenReturn(empty());

        assertThrows(NotFoundException.class, () -> service.findById(1L));

        verify(repository, never()).save(person);
    }

    @Test
    @DisplayName("Deve atualizar uma pessoa")
    void update_ShouldUpdatePersonSuccessfully() {
        Long id = 1L;
        PersonRequest personRequest = personRequest();
        AddressEntity addressRequest = createAddress();
        personRequest.setAddresses(Collections.singletonList(addressRequest));
        AddressEntity address = createAddress();
        PersonEntity personEntity = personEntity();
        personEntity.setAddresses(Collections.singletonList(address));
        PersonResponse expectedPersonResponse = personResponse();

        when(addressService.searchCep(personRequest)).thenReturn(address);
        when(repository.findById(id)).thenReturn(Optional.of(personEntity));
        when(repository.save(personEntity)).thenReturn(personEntity);
        when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));
        when(addressRepository.save(address)).thenReturn(address);

        PersonResponse actualPersonResponse = service.update(id, personRequest);

        assertEquals(expectedPersonResponse, actualPersonResponse);
        verify(addressService).searchCep(personRequest);
        verify(repository).findById(id);
        verify(repository).save(personEntity);
        verify(addressRepository).findById(address.getId());
    }

    @Test
    @DisplayName("Deve lançar NotFoundException quando não encontrar uma pessoa para atualizar")
    void update_personNotFound_shouldThrowNotFoundException() {
        long invalidId = -1;
        PersonRequest personRequest = new PersonRequest();

        when(repository.findById(invalidId))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            service.update(invalidId, personRequest);
        });
    }


    @Test
    @DisplayName("Deve salvar uma pessoa")
    void create_validRequest_shouldCreatePerson() {
        PersonRequest personRequest = new PersonRequest();
        AddressEntity addressEntity = new AddressEntity();

        when(addressService.searchCep(personRequest)).thenReturn(addressEntity);
        when(repository.save(requestToEntity(personRequest))).thenReturn(requestToEntity(personRequest));

        PersonResponse personResponse = service.create(personRequest);

        verify(repository).save(requestToEntity(personRequest));
        assertEquals(requestToEntity(personRequest), responseToEntity(personResponse));
    }


    @Test
    @DisplayName("Deve lançar NullPointerException quanto estiver faltando dados para salvar uma pessoa")
    void save_ShouldThrowNullPointerException() {
        var request = new PersonRequest();

        when(repository.findById(anyLong())).thenReturn(empty());

        assertThrows(NullPointerException.class, () -> service.create(request));
    }

    @Test
    @DisplayName("Deve lançar ConstraintViolationException quanto estiver faltando dados para salvar uma pessoa")
    void save_ShouldThrowConstraintViolationException() {
        var request = PersonRequest.builder()
                .addresses(List.of(createAddressWithoutNumber()))
                .build();

        when(repository.save(requestToEntity(request))).thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class, () -> service.create(request));
    }

    @Test
    @DisplayName("Deve deletar uma pessoa")
    void deleteById_validId_shouldDeletePerson() {
        Long id = 1L;
        when(repository.existsById(id)).thenReturn(true);
        doNothing().when(repository).deleteById(id);
        service.deleteById(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Deve lancar NotFoundException quando nao encontrar uma pessoa para deletar")
    void deleteById_ShouldThrowNotFoundException() {
        when(repository.findById(1L)).thenReturn(empty());

        assertThrows(NotFoundException.class, () -> service.deleteById(1L));
    }

    @Test
    @DisplayName("Deve deletar todas as pessoas")
    void deleteAll_WithSuccess() {
        service.deleteAll();

        verify(repository).deleteAll();
    }
}