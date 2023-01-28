package camila.peopleregistration.model.address.entity;

import camila.peopleregistration.model.person.entity.PersonEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLInsert;
import org.hibernate.annotations.Where;

import static jakarta.persistence.GenerationType.AUTO;

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
    @ApiModelProperty(notes = "ID of the user")
    private Long id;
    @NotNull
    @NotBlank(message = "CEP can not be null or empty")
    @ApiModelProperty(notes = "CEP of the user")
    @Column(length = 10, nullable = false)
    private String cep;
    @ApiModelProperty(notes = "Street of the user")
    @JsonProperty("logradouro")
    @Column(length = 100, nullable = false)
    private String street;

    @NotNull
    @NotBlank(message = "Number can not be null or empty")
    @ApiModelProperty(notes = "Number of the user")
    @Column(length = 10, nullable = false)
    private String number;

    @ApiModelProperty(notes = "Neighborhood of the user")
    @JsonProperty("bairro")
    @Column(length = 100, nullable = false)
    private String neighborhood;
    @ApiModelProperty(notes = "City of the user")
    @JsonProperty("localidade")
    @Column(length = 100, nullable = false)
    private String city;
    @ApiModelProperty(notes = "State of the user")
    @Column(length = 2, nullable = false)
    private String uf;

    @ApiModelProperty(notes = "Main address of the user")
    @Column(nullable = false)
    private Boolean mainAddress;

    private final String status = "Ativo";
}
