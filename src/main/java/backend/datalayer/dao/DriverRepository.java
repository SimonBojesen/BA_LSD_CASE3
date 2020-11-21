package backend.datalayer.dao;

import backend.datalayer.entity.DriverDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<DriverDB, Long> {
}
