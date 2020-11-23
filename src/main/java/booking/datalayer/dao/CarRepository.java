package booking.datalayer.dao;

import booking.datalayer.entity.CarDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<CarDB, Long> {
    Optional<CarDB> findByLicensePlate(String licensePlate);
}

