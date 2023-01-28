package camila.peopleregistration.model.person.request;

import camila.peopleregistration.model.address.entity.AddressEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonRequest {
    @NotNull
    @NotBlank(message = "Name can not be null or empty")
    @ApiModelProperty(notes = "Name of the person")
    private String name;
    @NotBlank(message = "BirthDate can not be null or empty")
    @NotNull
    @ApiModelProperty(notes = "Birthdate of the person")
    private String birthdate;

    @ApiModelProperty(notes = "Status of the person")
    private final String status = "Ativo";

    @ApiModelProperty(notes = "Address of the person")
    private List<AddressEntity> addresses;

    public void setAddresses(List<AddressEntity> addresses) {
        this.addresses = addresses;
    }
 }
