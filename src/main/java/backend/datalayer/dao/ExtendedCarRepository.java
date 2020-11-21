package backend.datalayer.dao;

import backend.datalayer.entity.CarDB;
import booking.dto.CarSummary;
import booking.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.LocalDateTime;
import java.util.Collection;

@NoRepositoryBean
public interface ExtendedCarRepository extends JpaRepository<CarDB, Long> {
    Collection<CarSummary> findAvailableCars(LocalDateTime pickUpDate, Place pickUpPlace);
}
