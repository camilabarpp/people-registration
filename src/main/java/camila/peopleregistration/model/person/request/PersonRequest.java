package camila.peopleregistration.model.person.request;

import camila.peopleregistration.model.address.entity.AddressEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonRequest {
    @NotNull
    @NotBlank(message = "Name can not be null or empty")
    private String name;
    @NotBlank(message = "BirthDate can not be null or empty")
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String birthdate;

    private final String status = "Ativo";

    private List<AddressEntity> addresses;

    public void setAddresses(List<AddressEntity> addresses) {
        this.addresses = addresses;
    }
 }
