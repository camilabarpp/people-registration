package camila.peopleregistration.service;

import camila.peopleregistration.model.person.mapper.PersonMapper;
import camila.peopleregistration.model.person.request.PersonRequest;
import camila.peopleregistration.model.person.response.PersonResponse;
import camila.peopleregistration.repository.AddressRepository;
import camila.peopleregistration.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static camila.peopleregistration.model.person.mapper.PersonMapper.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    private final AddressRepository addressRepository;

    public List<PersonResponse> findAll() {
        return fromEntityList(personRepository.findAll());
    }

    public PersonResponse findById(Long id) {
        return fromEntity(personRepository.findById(id).orElseThrow());
    }

    public PersonResponse create(PersonRequest personRequest) {
        return fromEntity(personRepository.save(toEntity(personRequest)));
    }

    public PersonResponse update(Long id, PersonRequest personRequest) {
        return fromEntity(personRepository.findById(id)
                .map(personEntity -> {
                    personEntity.setId(id);
                    personEntity.setName(personRequest.getName());
                    personEntity.setBirthdate(personRequest.getBirthdate());
                    //Pesquisar no banco se já existe um endereço com este mesmo ID e não gerar um novo
                    addressRepository.findById(personEntity.getAddresses().get(0).getId()).map(addressEntity -> {
                        addressEntity.setId(personEntity.getAddresses().get(0).getId());
                        addressEntity.setCep(personRequest.getAddresses().get(0).getCep());
                        addressEntity.setStreet(personRequest.getAddresses().get(0).getStreet());
                        addressEntity.setNumber(personRequest.getAddresses().get(0).getNumber());
                        addressEntity.setNeighborhood(personRequest.getAddresses().get(0).getNeighborhood());
                        addressEntity.setCity(personRequest.getAddresses().get(0).getCity());
                        addressEntity.setUf(personRequest.getAddresses().get(0).getUf());
                        addressEntity.setMainAddress(personRequest.getAddresses().get(0).getMainAddress());
                      return addressRepository.save(addressEntity);
                    });
                    return personRepository.save(personEntity);
                })
                .orElseThrow());
    }
    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

    public void deleteAll() {
        personRepository.deleteAll();
    }
}
