package camila.peopleregistration.service;

import camila.peopleregistration.integration.IntegrationCep;
import camila.peopleregistration.model.address.entity.AddressEntity;
import camila.peopleregistration.model.person.request.PersonRequest;
import camila.peopleregistration.repository.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final IntegrationCep integration;

    private final AddressRepository repository;

    public AddressEntity searchCep(PersonRequest personRequest) {
        String cep = personRequest.getAddresses().get(0).getCep();
        var newAddress = this.integration.findCep(cep);

        newAddress.setNumber(personRequest.getAddresses().get(0).getNumber());
        newAddress.setMainAddress(personRequest.getAddresses().get(0).getMainAddress());
        personRequest.setAddresses(PersonRequest.toList(newAddress));
        return newAddress;
    }

    public AddressEntity searchAnotherCep(PersonRequest personRequest) {
        String cep = personRequest.getAddresses().get(0).getCep();
        var newAddress = this.integration.findCep(cep);

        newAddress.setNumber(personRequest.getAddresses().get(0).getNumber());
        newAddress.setMainAddress(personRequest.getAddresses().get(0).getMainAddress());
        personRequest.setAddresses(PersonRequest.toList(newAddress));
        return repository.save(newAddress);
    }

    public AddressEntity findCep(String cep) {
        return integration.findCep(cep);
    }

    @Transactional
    public AddressEntity createAddress(AddressEntity addressEntity) {
        return repository.save(addressEntity);
    }

}
