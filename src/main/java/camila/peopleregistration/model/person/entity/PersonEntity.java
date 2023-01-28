package camila.peopleregistration.model.person.entity;

import camila.peopleregistration.model.address.entity.AddressEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "PersonEntity", description = "PersonEntity")
@Entity
@SQLDelete(sql = "UPDATE PERSON_ENTITY SET status = 'Inativo' WHERE id = ?")
@Where(clause = "status = 'Ativo'")
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 10, nullable = false)
    private String birthdate;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "PERSON_ENTITY_ID")
    private List<AddressEntity> addresses;

    @Column(length = 10, nullable = false)
    private final String status = "Ativo";
}
