package booking.datalayer.dao;

import booking.datalayer.entity.AddressDB;
import booking.datalayer.entity.AirportDB;
import booking.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<AirportDB, String> {

    Optional<AirportDB> findAirportDBByAddressDB(AddressDB addressDB);
}
