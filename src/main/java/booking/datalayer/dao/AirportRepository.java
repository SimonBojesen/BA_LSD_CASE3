package booking.datalayer.dao;

import booking.datalayer.entity.AirportDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<AirportDB, String> {
}
