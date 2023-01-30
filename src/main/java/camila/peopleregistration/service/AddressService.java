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
        Optional<PersonEntity> person = personRepository.findById(id);
        return person.map(PersonEntity::getAddresses)
                .orElseThrow(() -> new NotFoundException("Address not found"));
    }

    public AddressEntity createNewAddress(AddressEntity addressEntity, Long id) {
        if (personRepository.findById(id).isPresent()) {
            var newAddress = integration.findCep(addressEntity.getCep());
            newAddress.setNumber(addressEntity.getNumber());
            newAddress.setMainAddress(addressEntity.getMainAddress());
            addressEntity = newAddress;

            PersonEntity personEntity = personRepository.findById(id).get();
            personEntity.getAddresses().add(0, addressEntity);

            return repository.save(addressEntity);
        } else {
            throw new NotFoundException("Error to create a address, please check your data!");
        }
    }

    public AddressEntity updateAddressByPersonId(AddressEntity addressEntity, Long personId, Long addressId) {
        if (repository.findById(personId).isPresent()) {
            return repository.findById(addressId)
                    .map(addres -> {
                        var newAddress = integration.findCep(addressEntity.getCep());
                        newAddress.setNumber(addressEntity.getNumber());
                        newAddress.setMainAddress(addressEntity.getMainAddress());
                        newAddress.setId(addressEntity.getId());
                        addres = newAddress;
                        return repository.save(addres);
                    }).orElseThrow(() -> new NotFoundException("Error to update a address, please check your data!"));
        } else {
            throw new NotFoundException("Error to update a address, please check your data!");
        }
    }

    public void deleteAddressByPersonId(Long personId, Long addresId) {
        var address = repository.findById(addresId);
        if (address.isPresent()) {
            repository.deleteById(addresId);
        } else {
            throw new NotFoundException("Error to delete a address, please check your data!");
        }
    }
}
