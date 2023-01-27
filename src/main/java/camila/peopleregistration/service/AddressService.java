package camila.peopleregistration.service;

import camila.peopleregistration.integration.IntegrationCep;
import camila.peopleregistration.model.address.entity.AddressEntity;
import camila.peopleregistration.model.person.mapper.PersonMapper;
import camila.peopleregistration.model.person.request.PersonRequest;
import camila.peopleregistration.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository repository;

    private final IntegrationCep integration;

//   public AddressEntity consultarCep(String cep) {
//       return integration.consultarCep(cep);
//   }

//    public void searchCepAndSaveInDatabase(PersonRequest personRequest) {
//        var address = this.integration.consultarCep(personRequest.getAddresses().get(0).getCep());
//        personRequest.getAddresses().set(0, address);
//    }

    public void searchCepAndSaveInDatabase(PersonRequest personRequest) {
//        Long id = personRequest.getAddresses().get(0).getId();
//        String cep = personRequest.getAddresses().get(0).getCep();
//        var address = repository.findById((id)).orElseGet(() -> {
//            var newAddress = this.integration.consultarCep(cep);
//            repository.save(newAddress);
//            return newAddress;
//        });
//        personRequest.setAddresses((List<AddressEntity>) address);

        String cep = personRequest.getAddresses().get(0).getCep();

        var newAddress = this.integration.consultarCep(cep);

        newAddress.setNumber(personRequest.getAddresses().get(0).getNumber());
        newAddress.setMainAddress(personRequest.getAddresses().get(0).getMainAddress());

        repository.save(newAddress);

        personRequest.setAddresses(PersonRequest.toList(newAddress));
    }

}
