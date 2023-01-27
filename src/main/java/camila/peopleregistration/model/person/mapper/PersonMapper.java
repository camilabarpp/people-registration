package camila.peopleregistration.model.person.mapper;

import camila.peopleregistration.model.person.entity.PersonEntity;
import camila.peopleregistration.model.person.request.PersonRequest;
import camila.peopleregistration.model.person.response.PersonResponse;
import lombok.experimental.UtilityClass;

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

    public static PersonEntity toEntity(PersonRequest personRequest) {
        return PersonEntity.builder()
                .name(personRequest.getName())
                .birthdate(personRequest.getBirthdate())
                .addresses(personRequest.getAddresses())
                .build();
    }
}
