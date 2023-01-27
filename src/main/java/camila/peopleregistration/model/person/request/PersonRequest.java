package camila.peopleregistration.model.person.request;

import camila.peopleregistration.model.address.entity.AddressEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PersonRequest {
    @NotNull
    @NotBlank(message = "Name can not be null or empty")
    private String name;
    @NotBlank(message = "BirthDate can not be null or empty")
    @NotNull
    private String birthdate;


    private List<AddressEntity> addresses;

 }
