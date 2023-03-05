package camila.peopleregistration.service;

import camila.peopleregistration.configuration.exception.NotFoundException;
import camila.peopleregistration.model.address.entity.AddressEntity;
import camila.peopleregistration.model.person.request.PersonRequest;
import camila.peopleregistration.model.person.response.PersonResponse;
import camila.peopleregistration.repository.AddressRepository;
import camila.peopleregistration.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static camila.peopleregistration.model.person.mapper.PersonMapper.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    private final AddressRepository addressRepository;

    private final AddressService addressService;

    public List<PersonResponse> findAll() {
        return responseFromEntityList(personRepository.findAll());
    }

    public PersonResponse findById(Long id) {
        return entityToRespopnse(personRepository.findById(id).orElseThrow(() -> new NotFoundException("Person not found")));
    }

    public PersonResponse create(PersonRequest personRequest) {
        //Método que pesquisa o cep na api e salva no cadastro da pessoa
        addressService.searchCep(personRequest);
        return entityToRespopnse(personRepository.save(requestToEntity(personRequest)));
    }

    public PersonResponse update(Long id, PersonRequest personRequest) {
        return entityToRespopnse(personRepository.findById(id)
                .map(personEntity -> {
                    personEntity.setName(personRequest.getName());
                    personEntity.setBirthdate(personRequest.getBirthDate());
                    addressRepository.findById(personEntity.getAddresses().get(0).getId())
                            .map(addressEntity -> updateAddressEntity(addressEntity, personRequest))
                            .orElseThrow(() -> new NotFoundException("Address not found"));
                    return personRepository.save(personEntity);
                })
                .orElseThrow( () -> new NotFoundException("Person not found")));
    }

    private AddressEntity updateAddressEntity(AddressEntity address, PersonRequest personRequest) {
        AddressEntity addressInfo = addressService.searchCep(personRequest);
        address.setCep(addressInfo.getCep());
        address.setStreet(addressInfo.getStreet());
        address.setNumber(addressInfo.getNumber());
        address.setNeighborhood(addressInfo.getNeighborhood());
        address.setCity(addressInfo.getCity());
        address.setUf(addressInfo.getUf());
        address.setMainAddress(addressInfo.getMainAddress());
        return address;
    }

    //Fiz um soft delete, pois não acho interessante deletar completamente do banco
    public void deleteById(Long id) {
        if (!personRepository.existsById(id)) {
            throw new NotFoundException("Person not found");
        }
        personRepository.deleteById(id);
    }

    public void deleteAll() {
        personRepository.deleteAll();
    }
}
