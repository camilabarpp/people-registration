package camila.peopleregistration.model.address.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static jakarta.persistence.GenerationType.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "AddressEntity", description = "AddressEntity")
@Entity
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @NotNull
    @NotBlank(message = "CEP can not be null or empty")
    @ApiModelProperty(notes = "CEP of the user")
    @Column(length = 8, nullable = false)
    private String cep;
    @ApiModelProperty(notes = "Street of the user")
    @Column(length = 100, nullable = false)
    private String street;

    @NotNull
    @NotBlank(message = "Number can not be null or empty")
    @ApiModelProperty(notes = "Number of the user")
    @Column(length = 10, nullable = false)
    private String number;

    @ApiModelProperty(notes = "Neighborhood of the user")
    @Column(length = 100, nullable = false)
    private String neighborhood;
    @ApiModelProperty(notes = "City of the user")
    @Column(length = 100, nullable = false)
    private String city;
    @ApiModelProperty(notes = "State of the user")
    @Column(length = 2, nullable = false)
    private String uf;

    @ApiModelProperty(notes = "Main address of the user")
    @Column(nullable = false)
    private Boolean mainAddress;
}
