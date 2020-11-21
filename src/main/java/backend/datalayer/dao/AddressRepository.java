package backend.datalayer.dao;

import backend.datalayer.entity.AddressDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressDB, Long> {
}
