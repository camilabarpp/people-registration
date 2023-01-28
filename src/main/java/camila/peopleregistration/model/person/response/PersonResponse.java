package camila.peopleregistration.model.person.response;

import camila.peopleregistration.model.address.entity.AddressEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PersonResponse {

    private Long id;

    private String name;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String birthdate;

    private List<AddressEntity> addresses;
}
