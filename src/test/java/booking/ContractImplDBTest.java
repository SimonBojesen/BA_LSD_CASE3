package booking;

import booking.ContractImpl;
import booking.datalayer.constants.Place;
import booking.datalayer.entity.*;
import booking.dto.*;
import booking.entity.*;
import booking.eto.InvalidInputException;
import booking.eto.PersistanceFailedException;
import booking.eto.UnavailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ContractImplDBTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    @Qualifier("ContractImpl")
    private Contract contractImpl;

    //TESTDATA
    Address address = new Address("testvej", 1111, "testby");;
    Address address2 = new Address("testvej2", 11112, "testby2");;
    Car car1 = new Car("", "", Type.B, 200.0, 2, false);
    Car car2 = new Car("", "", Type.B, 200.0, 2, false);
    Car car3 = new Car("", "", Type.A, 400.0, 4, true);
    Driver driver = new Driver("simon", address, "simon@simonsen.dk", new Date(), 1234, true, 1232344L);
    Hotel hotel = new Hotel("testHotel", address, true, 1, Rating.FIVE);
    Airport airport = new Airport("Københavns Lufthavn", address2, true,  "123nrk");
    Employee employee = new Employee("martin", address2, "martin@martinson.dk", new Date(), 1234, true, "demo", "demo");

    @BeforeEach
    public void setup(){
        AddressDB sampleAddress = new AddressDB(address);
        AddressDB sampleAddress2 = new AddressDB(address2);
        sampleAddress = em.persist(sampleAddress);
        sampleAddress2 = em.persist(sampleAddress2);
        HotelDB sampleHotelDB = new HotelDB(hotel.getName(), sampleAddress, hotel.isActive(), hotel.getRating());
        em.persist(sampleHotelDB);
        AirportDB sampleAirportDB = new AirportDB(airport.getIATA(), airport.getName(), sampleAddress2, airport.isActive());
        em.persist(sampleAirportDB);
        DriverDB sampleDriver = new DriverDB(driver.getName(), sampleAddress, driver.getEmail(), driver.getDateOfBirth(), driver.getSocialSecurityNumber(), driver.isActive(), driver.getLicenseNo());
        EmployeeDB sampleEmployee = new EmployeeDB(employee.getName(), sampleAddress2, employee.getEmail(), employee.getDateOfBirth(), employee.getSocialSecurityNumber(), employee.isActive(), employee.getUsername(), employee.getPassword());
        em.persist(sampleDriver);
        em.persist(sampleEmployee);
        CarDB sampleCar = new CarDB(car1, booking.datalayer.constants.Place.AIRPORT, sampleAddress2);
        CarDB sampleCar3 = new CarDB(car2, booking.datalayer.constants.Place.NONE, null);
        CarDB sampleCar2 = new CarDB(car3, Place.HOTEL, sampleAddress);
        em.persist(sampleCar);
        em.persist(sampleCar3);
        em.persist(sampleCar2);
        em.flush();
    }

    @Test
    void saveBookingTest() throws InvalidInputException, PersistanceFailedException, UnavailableException {
        booking.entity.Place pickupplace = new booking.entity.Place("airport", address, true);
        booking.entity.Place deliveryplace = new booking.entity.Place("hotel", address2, true);
        BookingCriteria bookingCriteria = new BookingCriteria(pickupplace, deliveryplace, LocalDateTime.now(), LocalDateTime.now());
        DriverDetails driverDetails = new DriverDetails(driver, driver.getLicenseNo());
        EmployeeDetails employeeDetails = new EmployeeDetails(employee);
        CarSummary carSummary = new CarSummary(car1, pickupplace);
        BookingDetails booking = contractImpl.createBooking(bookingCriteria, car1.getPrice(), driverDetails, employeeDetails, carSummary);

        booking = contractImpl.saveBooking(booking);
        assertNotNull(booking.getId());
    }


}
