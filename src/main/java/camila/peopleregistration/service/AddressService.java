package camila.peopleregistration.service;

import camila.peopleregistration.integration.IntegrationCep;
import camila.peopleregistration.model.address.entity.AddressEntity;
import camila.peopleregistration.model.person.request.PersonRequest;
import camila.peopleregistration.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final IntegrationCep integration;

    public AddressEntity searchCep(PersonRequest personRequest) {
        String cep = personRequest.getAddresses().get(0).getCep();
        var newAddress = this.integration.consultarCep(cep);

        newAddress.setNumber(personRequest.getAddresses().get(0).getNumber());
        newAddress.setMainAddress(personRequest.getAddresses().get(0).getMainAddress());
        personRequest.setAddresses(PersonRequest.toList(newAddress));
        return newAddress;
    }

}
