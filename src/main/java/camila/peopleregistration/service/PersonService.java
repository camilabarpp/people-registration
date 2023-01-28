package camila.peopleregistration.service;

import camila.peopleregistration.model.address.entity.AddressEntity;
import camila.peopleregistration.model.person.entity.PersonEntity;
import camila.peopleregistration.model.person.request.PersonRequest;
import camila.peopleregistration.model.person.response.PersonResponse;
import camila.peopleregistration.repository.AddressRepository;
import camila.peopleregistration.repository.PersonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static camila.peopleregistration.model.person.mapper.PersonMapper.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    private final AddressRepository addressRepository;

    private final AddressService addressService;

    public List<PersonResponse> findAll() {
        return fromEntityList(personRepository.findAll());
    }

    public PersonResponse findById(Long id) {
        return fromEntity(personRepository.findById(id).orElseThrow());
    }

    public PersonResponse create(PersonRequest personRequest) {
        //Método que pesquisa o cep na api e salva no banco de AddressEntity
        addressService.searchCep(personRequest);
        return fromEntity(personRepository.save(toEntity(personRequest)));
    }

    @Transactional
    public Optional<PersonEntity> createAnotherCep(Long id, PersonRequest personRequest) {
//        AddressEntity address1 =  addressService.searchAnotherCep(personRequest);
//        return personRepository.findById(id).map(personEntity -> {
//            personEntity.setAddresses(PersonRequest.toList(address1));
//            return personEntity;
//        });
        AddressEntity address1 =  addressService.searchAnotherCep(personRequest);
        return personRepository.findById(id).map(personEntity -> {
            personEntity.setAddresses(PersonRequest.toList(address1));
            return personEntity;
        });
    }

    @Transactional
    public AddressEntity criarEndereco(AddressEntity endereco, Long id) {
        personRepository.findById(id).map(teste -> {
            teste.getAddresses().add(0, endereco);
            return teste;
        });

        return addressRepository.save(endereco);
    }

    public PersonResponse update(Long id, PersonRequest personRequest) {
        AddressEntity address = addressService.searchCep(personRequest);
        return fromEntity(personRepository.findById(id)
                .map(personEntity -> {
                    personEntity.setId(id);
                    personEntity.setName(personRequest.getName());
                    personEntity.setBirthdate(personRequest.getBirthdate());
                    //Pesquisar no banco se já existe um endereço com este mesmo ID e não gerar um novo
                    addressRepository.findById(personEntity.getAddresses().get(0).getId()).map(addressEntity -> {
                        addressEntity.setId(personEntity.getAddresses().get(0).getId());
                        addressEntity.setNumber(personRequest.getAddresses().get(0).getNumber());
                        addressEntity.setMainAddress(personRequest.getAddresses().get(0).getMainAddress());
                        addressEntity.setCep(address.getCep());
                        addressEntity.setStreet(address.getStreet());
                        addressEntity.setNeighborhood(address.getNeighborhood());
                        addressEntity.setCity(address.getCity());
                        addressEntity.setUf(address.getUf());
                      return addressRepository.save(addressEntity);
                    });
                    return personRepository.save(personEntity);
                })
                .orElseThrow());
    }

    //Fiz um soft delete, pois não acho interessante deletar completemento do banco
    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

    public void deleteAll() {
        personRepository.deleteAll();
    }
}
