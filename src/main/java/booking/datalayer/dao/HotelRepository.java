package booking.datalayer.dao;

import  booking.datalayer.entity.AddressDB;
import booking.datalayer.entity.HotelDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<HotelDB, Long> {

    Optional<HotelDB> findHotelDBByAddressDB(AddressDB addressDB);

}
