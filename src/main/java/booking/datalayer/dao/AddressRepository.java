package booking.datalayer.dao;

import booking.datalayer.entity.AddressDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressDB, Long> {
    Optional<AddressDB> findByStreetAddressAndCityAndPostalCode(String streetAddress, String city, int postalCode);
}
