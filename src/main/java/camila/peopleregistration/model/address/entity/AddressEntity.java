package camila.peopleregistration.model.address.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.AUTO;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "AddressEntity", description = "AddressEntity")
@Entity
@SQLDelete(sql = "UPDATE ADDRESS_ENTITY  SET status = 'Inativo' WHERE id = ?")
@Where(clause = "status = 'Ativo'")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @ApiModelProperty(notes = "ID of the address")
    private Long id;
    @NotNull
    @NotBlank(message = "CEP can not be null or empty")
    @ApiModelProperty(notes = "CEP of the user")
    @Column(length = 10, nullable = false)
    @Length(min = 8, message = "teste")
    private String cep;
    @ApiModelProperty(notes = "Street of the person")
    @JsonProperty("logradouro")
    @Column(length = 100, nullable = false)
    private String street;

    @NotNull
    @NotBlank(message = "Number can not be null or empty")
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

    private final String status = "Ativo";
}
