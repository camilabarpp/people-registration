package camila.peopleregistration.repository;

import camila.peopleregistration.model.address.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

//    List<AddressEntity> findAddressEntitiesBy(Long personId);
}
