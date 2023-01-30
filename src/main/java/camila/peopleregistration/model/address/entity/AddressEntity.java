package camila.peopleregistration.model.address.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "AddressEntity", description = "AddressEntity")
@Entity
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @ApiModelProperty(notes = "ID of the address")
    private Long id;

    @NotBlank
    @ApiModelProperty(notes = "CEP of the user")
    @Column(length = 10, nullable = false)
    @Length(min = 8, message = "teste")
    private String cep;
    @ApiModelProperty(notes = "Street of the person")
    @JsonProperty("logradouro")
    @Column(length = 100, nullable = false)
    private String street;

    @NotBlank
    @ApiModelProperty(notes = "Number of the person")
    @Column(length = 10, nullable = false)
    private String number;

    @ApiModelProperty(notes = "Neighborhood of the person")
    @JsonProperty("bairro")
    @Column(length = 100, nullable = false)
    private String neighborhood;
    @ApiModelProperty(notes = "City of the person")
    @JsonProperty("localidade")
    @Column(length = 100, nullable = false)
    private String city;
    @ApiModelProperty(notes = "State of the person")
    @Column(length = 2, nullable = false)
    private String uf;

    @ApiModelProperty(notes = "Main address of the person")
    @Column(nullable = false)
    private Boolean mainAddress;
}
