package camila.peopleregistration.service.stubs;

import camila.peopleregistration.model.address.entity.AddressEntity;
import camila.peopleregistration.model.person.entity.PersonEntity;
import camila.peopleregistration.model.person.request.PersonRequest;
import camila.peopleregistration.model.person.response.PersonResponse;

import java.util.List;

public class AddressStubs {

    public static PersonRequest personRequest() {
        return PersonRequest.builder()
                .name("Camila")
                .birthdate("02/07/1996")
                .addresses(List.of(createAddress()))
                .build();
    }

    public static PersonEntity personEntity() {
        return PersonEntity.builder()
                .id(1L)
                .name("Camila")
                .birthdate("02/07/1996")
                .addresses(List.of(createAddress()))
                .build();
    }

    public static PersonResponse personResponse() {
        return PersonResponse.builder()
                .id(1L)
                .name("Camila")
                .birthdate("02/07/1996")
                .addresses(List.of(createAddress()))
                .build();
    }

    public static AddressEntity createAddress() {
        return AddressEntity.builder()
                .id(1L)
                .cep("94020070")
                .street("Rua Joao Dutra")
                .neighborhood("Salgado Filho")
                .number("10")
                .city("Gravataí")
                .uf("RS")
                .mainAddress(true)
                .build();
    }

    public static AddressEntity createAddress2() {
        return AddressEntity.builder()
                .id(2L)
                .cep("94020050")
                .street("Rua Alfredo Soares Pitres")
                .neighborhood("Salgado Filho")
                .number("10")
                .city("Gravataí")
                .uf("RS")
                .mainAddress(true)
                .build();
    }
}
