package backend.dao;

import backend.datalayer.constants.Place;
import backend.datalayer.dao.BookingRepository;
import backend.datalayer.entity.*;
import booking.dto.BookingDetails;
import booking.entity.Address;
import booking.entity.Car;
import booking.entity.Type;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingRepositoryTest
{
    // SUT
    @Autowired // injects BookingRepository
    private BookingRepository bookingRepository;

    // DOC
    Address airportAddress = new Address("Ellehammervej 123", 2300, "Kbh. S");
    AddressDB airportAddressDB = new AddressDB(airportAddress);
    AirportDB airportDB = new AirportDB("EKCPH", "CPH airport", airportAddressDB, true);

    @Autowired
    private TestEntityManager testEntityManager;

    private BookingDB persistedBookingDB;


    @BeforeEach
    public void setup()
    {
        Car car1 = new Car("stelnr 2", "AB 546783", Type.A, 50.50, 4, true);
        Address address = new Address("hovedvejen", 6565, "JyskBy");
        AddressDB addressDB = new AddressDB(address);
        CarDB carDB = new CarDB(car1, Place.AIRPORT, airportAddressDB);
        DriverDB driverDB = new DriverDB("Hans", addressDB, "hans@høns.dk", new Date(), 123, true, 123L);
        EmployeeDB employeeDB = new EmployeeDB("poul", addressDB, "poul@asfd.dk", new Date(), 4545, true, "demo", "demon");
        persistedBookingDB = new BookingDB(carDB, driverDB, employeeDB, addressDB, new Date(), new Date(), 200.0, 0.0);
        testEntityManager.persist(persistedBookingDB);
        testEntityManager.persist(airportDB);

    }

    @Test
    public void mustFindPersistedBooking() {
        // Arrange
        // Act
        var result = bookingRepository.findBooking(persistedBookingDB.getId());

        // Assert
        assertNotNull(result);
    }
}
