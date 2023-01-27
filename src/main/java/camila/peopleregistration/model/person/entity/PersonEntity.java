package camila.peopleregistration.model.person.entity;

import camila.peopleregistration.model.address.entity.AddressEntity;
import io.swagger.annotations.ApiModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

import static jakarta.persistence.GenerationType.AUTO;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "PersonEntity", description = "PersonEntity")
@Entity
@SQLDelete(sql = "UPDATE Course SET status = 'Inativo' WHERE id = ?")
@Where(clause = "status = 'Ativo'")
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 10, nullable = false)
    private String birthdate;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(length = 100, nullable = false)
    private List<AddressEntity> addresses;

}
