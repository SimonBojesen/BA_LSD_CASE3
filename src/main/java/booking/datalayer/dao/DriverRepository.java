package booking.datalayer.dao;

import booking.datalayer.entity.DriverDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<DriverDB, Long> {
    Optional<DriverDB> findByLicenseNo(long licenseNo);
}
