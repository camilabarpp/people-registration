package camila.peopleregistration.service;

import camila.peopleregistration.configuration.exception.NotFoundException;
import camila.peopleregistration.integration.IntegrationCep;
import camila.peopleregistration.model.address.entity.AddressEntity;
import camila.peopleregistration.model.person.entity.PersonEntity;
import camila.peopleregistration.model.person.request.PersonRequest;
import camila.peopleregistration.repository.AddressRepository;
import camila.peopleregistration.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        if (!personRequest.getAddresses().get(0).getCep().isEmpty()) {
            var cep = personRequest.getAddresses().get(0).getCep();
            var newAddress = this.integration.findCep(cep);

            newAddress.setNumber(personRequest.getAddresses().get(0).getNumber());
            newAddress.setMainAddress(personRequest.getAddresses().get(0).getMainAddress());
            personRequest.setAddresses(toList(newAddress));
            return newAddress;
        } else {
            throw new NotFoundException("CEP not found");
        }
    }

    public List<AddressEntity> getAddressesByPersonId(Long id) {
        PersonEntity person = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person with id " + id + " not found"));
        return person.getAddresses();
    }


    public AddressEntity createNewAddress(AddressEntity address, Long personId) {
        PersonEntity person = personRepository.findById(personId)
                .orElseThrow(() -> new NotFoundException("Person not found with id " + personId));

        AddressEntity newAddress = integration.findCep(address.getCep());
        newAddress.setNumber(address.getNumber());
        newAddress.setMainAddress(address.getMainAddress());
        address = repository.save(newAddress);

        person.getAddresses().add(0, address);
        personRepository.save(person);

        return address;
    }


    public AddressEntity updateAddressByPersonId(AddressEntity addressEntity, Long personId, Long addressId) {
        Optional<PersonEntity> person = personRepository.findById(personId);
        if (person.isEmpty()) {
            throw new NotFoundException("Person not found");
        }

        Optional<AddressEntity> address = repository.findById(addressId);
        if (address.isEmpty()) {
            throw new NotFoundException("Address not found");
        }

        AddressEntity newAddress = integration.findCep(addressEntity.getCep());
        newAddress.setNumber(addressEntity.getNumber());
        newAddress.setMainAddress(addressEntity.getMainAddress());
        newAddress.setId(addressId);

        return repository.save(newAddress);
    }

    public void deleteAddressByPersonId(Long personId, Long addressId) {
        Optional<AddressEntity> addressOptional = repository.findById(addressId);
        if (addressOptional.isPresent()) {
            repository.deleteById(addressId);
        } else {
            throw new NotFoundException("Address with ID " + addressId + " not found for person with ID " + personId);
        }
    }

}
