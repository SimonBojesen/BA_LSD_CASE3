package booking.servicelayer;

import booking.datalayer.constants.Place;
import booking.datalayer.entity.*;
import booking.dto.*;
import booking.entity.*;
import booking.eto.InvalidInputException;
import booking.eto.NotFoundException;
import booking.eto.PersistanceFailedException;
import booking.eto.UnavailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan("booking.servicelayer")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ContractImplDBTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    @Qualifier("ContractImpl")
    private ContractImpl contractImpl;

    //Test data for entities from our contract
    Address address = new Address("testvej", 1111, "testby");
    Address address2 = new Address("testvej2", 11112, "testby2");
    Car car1 = new Car("", "testPlate", Type.B, 200.0, 2, false);
    Car car2 = new Car("", "testPlate2", Type.B, 200.0, 2, false);
    Car car3 = new Car("", "testPlate3", Type.A, 400.0, 4, true);
    Driver driver = new Driver("simon", address, "simon@simonsen.dk", new Date(), 1234, true, 1232344L);
    Hotel hotel = new Hotel("testHotel", address, true, 1, Rating.FIVE);
    Airport airport = new Airport("Københavns Lufthavn", address2, true,  "123nrk");
    Employee employee = new Employee("martin", address2, "martin@martinson.dk", new Date(), 1234, true, "demo", "demo");

    //Persisted test entities used in test
    AddressDB persistedAddressDBHotel;
    AddressDB persistedAddressDBAirport;
    HotelDB persistedHotelDB;
    AirportDB persistedAirportDB;
    EmployeeDB persistedEmployeeDB;
    DriverDB persistedDriverDB;
    CarDB persistedCarDBAirport;
    CarDB persistedCarHotel;
    CarDB persistedCarNone;


    @BeforeEach
    public void setup(){
        persistedAddressDBHotel = new AddressDB(address);
        persistedAddressDBAirport = new AddressDB(address2);
        persistedAddressDBHotel = em.persist(persistedAddressDBHotel);
        persistedAddressDBAirport = em.persist(persistedAddressDBAirport);
        persistedHotelDB = new HotelDB(hotel.getName(), persistedAddressDBHotel, hotel.isActive(), hotel.getRating());
        em.persist(persistedHotelDB);
        persistedAirportDB = new AirportDB(airport.getIATA(), airport.getName(), persistedAddressDBAirport, airport.isActive());
        em.persist(persistedAirportDB);
        persistedDriverDB = new DriverDB(driver.getName(), persistedAddressDBHotel, driver.getEmail(), driver.getDateOfBirth(), driver.getSocialSecurityNumber(), driver.isActive(), driver.getLicenseNo());
        persistedEmployeeDB = new EmployeeDB(employee.getName(), persistedAddressDBAirport, employee.getEmail(), employee.getDateOfBirth(), employee.getSocialSecurityNumber(), employee.isActive(), employee.getUsername(), employee.getPassword());
        em.persist(persistedDriverDB);
        em.persist(persistedEmployeeDB);
        persistedCarDBAirport = new CarDB(car1, booking.datalayer.constants.Place.AIRPORT, persistedAddressDBAirport);
        persistedCarHotel = new CarDB(car3, Place.HOTEL, persistedAddressDBHotel);
        persistedCarNone = new CarDB(car2, booking.datalayer.constants.Place.NONE, null);
        em.persist(persistedCarDBAirport);
        em.persist(persistedCarHotel);
        em.persist(persistedCarNone);
        em.flush();
    }

    @Test
    void saveBooking_WhereDriverExistTest() throws InvalidInputException, PersistanceFailedException, UnavailableException {
        booking.entity.Place pickUpPlace = new booking.entity.Place("airport", address, true);
        booking.entity.Place deliveryPlace = new booking.entity.Place("hotel", address2, true);
        BookingCriteria bookingCriteria = new BookingCriteria(pickUpPlace, deliveryPlace, LocalDateTime.now(), LocalDateTime.now());
        DriverDetails driverDetails = new DriverDetails(driver, driver.getLicenseNo());
        EmployeeDetails employeeDetails = new EmployeeDetails(employee);
        CarSummary carSummary = new CarSummary(car1, pickUpPlace);
        BookingDetails booking = contractImpl.createBooking(bookingCriteria, car1.getPrice(), driverDetails, employeeDetails, carSummary);

        booking = contractImpl.saveBooking(booking);
        assertNotNull(booking.getId());
    }

    @Test
    void saveBooking_WhereDriverNotExistTest() throws InvalidInputException, PersistanceFailedException, UnavailableException {
        booking.entity.Place pickUpPlace = new booking.entity.Place("airport", address, true);
        booking.entity.Place deliveryPlace = new booking.entity.Place("hotel", address2, true);
        BookingCriteria bookingCriteria = new BookingCriteria(pickUpPlace, deliveryPlace, LocalDateTime.now(), LocalDateTime.now());

        Driver driver = new Driver("test", new Address("halløj", 9876, "goddav"), "test@test.dk", new Date(), 98756372, true, 987654321L);
        DriverDetails driverDetails = new DriverDetails(driver, driver.getLicenseNo());

        EmployeeDetails employeeDetails = new EmployeeDetails(employee);
        CarSummary carSummary = new CarSummary(car1, pickUpPlace);
        BookingDetails booking = contractImpl.createBooking(bookingCriteria, car1.getPrice(), driverDetails, employeeDetails, carSummary);

        booking = contractImpl.saveBooking(booking);
        assertNotNull(booking.getId());
    }

    @Test
    void findBooking_UsingHotelAddress() throws NotFoundException,InvalidInputException {
        BookingDB persistedBooking = new BookingDB(persistedCarHotel, persistedDriverDB, persistedEmployeeDB, persistedAddressDBHotel,LocalDateTime.now(), LocalDateTime.now().minusDays(2), 20d, 30d);
        em.persist(persistedBooking);
        em.flush();

        BookingIdentifier bookingIdentifier = new BookingIdentifier(persistedBooking.getId());
        BookingDetails booking = contractImpl.findBooking(bookingIdentifier);
        assertNotNull(booking.getId());
    }

    @Test
    void findBooking_UsingAirportAddress() throws NotFoundException,InvalidInputException {
        BookingDB persistedBooking = new BookingDB(persistedCarDBAirport, persistedDriverDB, persistedEmployeeDB, persistedAddressDBAirport,LocalDateTime.now(), LocalDateTime.now().minusDays(2), 20d, 30d);
        em.persist(persistedBooking);
        em.flush();
        BookingIdentifier bookingIdentifier = new BookingIdentifier(persistedBooking.getId());
        BookingDetails booking = contractImpl.findBooking(bookingIdentifier);
        assertNotNull(booking.getId());
    }


}
