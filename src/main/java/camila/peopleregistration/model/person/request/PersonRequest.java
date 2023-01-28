package camila.peopleregistration.model.person.request;

import camila.peopleregistration.model.address.entity.AddressEntity;
import camila.peopleregistration.model.person.entity.PersonEntity;
import camila.peopleregistration.model.person.response.PersonResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class PersonRequest {
    @NotNull
    @NotBlank(message = "Name can not be null or empty")
    private String name;
    @NotBlank(message = "BirthDate can not be null or empty")
    @NotNull
    private String birthdate;

    private final String status = "Ativo";

    private List<AddressEntity> addresses;

    public void setAddresses(List<AddressEntity> addresses) {
        this.addresses = addresses;
    }

    public static List<AddressEntity> toList(AddressEntity address) {
        List<AddressEntity> addressList = new ArrayList<>();
        addressList.add(address);
        return addressList;
    }
 }
