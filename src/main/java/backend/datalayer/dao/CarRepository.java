package backend.datalayer.dao;

import backend.datalayer.entity.Car;
import booking.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    void findAvailableCars(LocalDateTime pickUpDate, Place pickUpPlace);
}
