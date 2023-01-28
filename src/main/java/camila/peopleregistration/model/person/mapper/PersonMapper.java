package camila.peopleregistration.model.person.mapper;

import camila.peopleregistration.model.address.entity.AddressEntity;
import camila.peopleregistration.model.person.entity.PersonEntity;
import camila.peopleregistration.model.person.request.PersonRequest;
import camila.peopleregistration.model.person.response.PersonResponse;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class PersonMapper {

    public static PersonResponse fromEntity(PersonEntity personEntity) {
        return PersonResponse.builder()
                .id(personEntity.getId())
                .name(personEntity.getName())
                .birthdate(personEntity.getBirthdate())
                .addresses(personEntity.getAddresses())
                .build();
    }

    public static List<PersonResponse> fromEntityList(List<PersonEntity> personEntity) {
        return personEntity.stream()
                .map(PersonMapper::fromEntity)
                .toList();
    }

    public static PersonEntity toEntity(PersonRequest personRequest) {
        return PersonEntity.builder()
                .name(personRequest.getName())
                .birthdate(personRequest.getBirthdate())
                .addresses(personRequest.getAddresses())
                .build();
    }

    public static List<AddressEntity> toList(AddressEntity address) {
        List<AddressEntity> addressList = new ArrayList<>();
        addressList.add(address);
        return addressList;
    }
}
