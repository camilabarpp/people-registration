package camila.peopleregistration.model.person.response;

import camila.peopleregistration.model.address.entity.AddressEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PersonResponse {

    private Long id;

    private String name;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;

    private List<AddressEntity> addresses;
}
