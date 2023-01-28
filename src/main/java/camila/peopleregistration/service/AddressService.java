package camila.peopleregistration.service;

import camila.peopleregistration.integration.IntegrationCep;
import camila.peopleregistration.model.address.entity.AddressEntity;
import camila.peopleregistration.model.person.entity.PersonEntity;
import camila.peopleregistration.model.person.request.PersonRequest;
import camila.peopleregistration.repository.AddressRepository;
import camila.peopleregistration.repository.PersonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static camila.peopleregistration.model.person.mapper.PersonMapper.toList;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final IntegrationCep integration;

    private final AddressRepository repository;

    private final PersonRepository personRepository;

    public AddressEntity searchCep(PersonRequest personRequest) {
        String cep = personRequest.getAddresses().get(0).getCep();
        var newAddress = this.integration.findCep(cep);

        newAddress.setNumber(personRequest.getAddresses().get(0).getNumber());
        newAddress.setMainAddress(personRequest.getAddresses().get(0).getMainAddress());
        personRequest.setAddresses(toList(newAddress));
        return newAddress;
    }

    @Transactional
    public AddressEntity createAddress(AddressEntity addressEntity, Long id) {
        if (personRepository.findById(id).isPresent()) {
            var newAddress = integration.findCep(addressEntity.getCep());
            newAddress.setNumber(addressEntity.getNumber());
            newAddress.setMainAddress(addressEntity.getMainAddress());
            addressEntity = newAddress;

            PersonEntity personEntity = personRepository.findById(id).get();
            personEntity.getAddresses().add(0, addressEntity);

            return repository.save(addressEntity);
        } else {
            return null;
        }
    }

    public List<AddressEntity> getAddressesByPersonId(Long id) {
        Optional<PersonEntity> person = personRepository.findById(id);
        return person.map(PersonEntity::getAddresses).orElse(new ArrayList<>());
    }
}
