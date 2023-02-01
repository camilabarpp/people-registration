package camila.peopleregistration.model.person.response;

import camila.peopleregistration.model.address.entity.AddressEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PersonResponse {

    private Long id;

    private String name;
    private String birthdate;

    private List<AddressEntity> addresses;
}
