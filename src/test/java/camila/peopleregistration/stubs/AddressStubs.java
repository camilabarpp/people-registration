package camila.peopleregistration.stubs;

import camila.peopleregistration.model.address.entity.AddressEntity;
import camila.peopleregistration.model.person.entity.PersonEntity;
import camila.peopleregistration.model.person.request.PersonRequest;
import camila.peopleregistration.model.person.response.PersonResponse;

import java.time.LocalDate;
import java.util.List;

public class AddressStubs {

    static String invalidName = "Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
            " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an " +
            "unknown printer took a galley of type and scrambled" +
            " it to make a type specimen book. It has survived not only five centuries, but also the " +
            "leap into electronic typesetting, remaining essentially unchanged. It was popularised in " +
            "the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more " +
            "recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

    public static PersonRequest personRequest() {
        return PersonRequest.builder()
                .name("Camila")
                .birthdate(LocalDate.now())
                .addresses(List.of(createAddress()))
                .build();
    }

    public static PersonRequest personRequestWithoutName() {
        return PersonRequest.builder()
                .birthdate(LocalDate.now())
                .addresses(List.of(createAddressWithoutNumber()))
                .build();
    }

    public static PersonRequest personRequestWithInvalidName() {
        return PersonRequest.builder()
                .name(invalidName)
                .birthdate(LocalDate.now())
                .addresses(List.of(createAddressWithoutNumber()))
                .build();
    }

    public static PersonRequest personRequestWithInvalidCep() {
        return PersonRequest.builder()
                .name(invalidName)
                .birthdate(LocalDate.now())
                .addresses(List.of(AddressEntity
                        .builder()
                        .cep("9402007")
                        .build()))
                .build();
    }

    public static PersonEntity personEntity() {
        return PersonEntity.builder()
                .name("Camila")
                .birthdate(LocalDate.now())
                .addresses(List.of(createAddress()))
                .build();
    }

    public static PersonResponse personResponse() {
        return PersonResponse.builder()
                .name("Camila")
                .birthdate(LocalDate.now())
                .addresses(List.of(createAddress()))
                .build();
    }

    public static AddressEntity createAddress() {
        return AddressEntity.builder()
                .cep("94020070")
                .street("Rua João Dutra")
                .neighborhood("Salgado Filho")
                .number("10")
                .city("Gravatai")
                .uf("RS")
                .mainAddress(true)
                .build();
    }

    public static AddressEntity createAddressWithoutNumber() {
        return AddressEntity.builder()
                .id(2L)
                .cep("94020050")
                .build();
    }
}
