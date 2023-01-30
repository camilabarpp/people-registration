package camila.peopleregistration.model.person.response;

import camila.peopleregistration.model.address.entity.AddressEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponse {

    private Long id;

    private String name;
    private String birthdate;

    private List<AddressEntity> addresses;
}
