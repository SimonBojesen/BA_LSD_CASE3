package backend.datalayer.dao;

import backend.datalayer.entity.AirportDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<AirportDB, String> {
}
