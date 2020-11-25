package booking.datalayer.dao;

import booking.datalayer.entity.CarDB;
import booking.dto.BookingCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<CarDB, Long>, CustomCarRepository {
    Optional<CarDB> findByLicensePlate(String licensePlate);
}

