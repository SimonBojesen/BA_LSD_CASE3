package unit;

import booking.Contract;
import booking.datalayer.dao.*;
import booking.dto.*;
import booking.entity.*;
import booking.eto.InvalidInputException;
import booking.eto.NotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

import booking.servicelayer.ContractImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ContractImplTest
{
    // DOC
    private CarRepository carRepository = mock(CarRepository.class);
    private AddressRepository addressRepository = mock(AddressRepository.class);
    private AirportRepository airportRepository = mock(AirportRepository.class);
    private BookingRepository bookingRepository = mock(BookingRepository.class);
    private DriverRepository driverRepository = mock(DriverRepository.class);
    private EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
    private HotelRepository hotelRepository = mock(HotelRepository.class);

    // SUT
    private Contract contractImpl = new ContractImpl(addressRepository,employeeRepository,driverRepository,carRepository,bookingRepository);

    //Test data
    private Address address;
    private Address address2;
    private Car car1;
    private Car car2;
    private Car car3;
    private Driver driver;
    private Hotel hotel;
    private Airport airport;
    private Employee employee;

    @BeforeEach
    void setup() {
        address = new Address("testvej", 1111, "testby");
        address2 = new Address("testvej2", 11112, "testby2");
        car1 = new Car("", "testPlate", Type.B, 200.0, 2, false);
        car2 = new Car("", "testPlate2", Type.B, 200.0, 2, false);
        car3 = new Car("", "testPlate3", Type.A, 400.0, 4, true);
        driver = new Driver("simon", address, "simon@simonsen.dk", new Date(), 1234, true, 1232344L);
        hotel = new Hotel("testHotel", address, true, 1, Rating.FIVE);
        airport = new Airport("Københavns Lufthavn", address2, true,  "123nrk");
        employee = new Employee("martin", address2, "martin@martinson.dk", new Date(), 1234, true, "demo", "demo");
    }

    @Test
    void createBookingTest() throws InvalidInputException {
        booking.entity.Place pickupplace = new booking.entity.Place("airport", address, true);
        booking.entity.Place deliveryplace = new booking.entity.Place("hotel", address2, true);
        BookingCriteria bookingCriteria = new BookingCriteria(pickupplace, deliveryplace, LocalDateTime.now(), LocalDateTime.now());
        DriverDetails driverDetails = new DriverDetails(driver, driver.getLicenseNo());
        EmployeeDetails employeeDetails = new EmployeeDetails(employee);
        CarSummary carSummary = new CarSummary(car1, pickupplace);
        BookingDetails booking = contractImpl.createBooking(bookingCriteria, carSummary.getCar().getPrice(), driverDetails, employeeDetails, carSummary);

        assertEquals(bookingCriteria, booking.getBookingCriteria());
        assertEquals(driverDetails, booking.getDriverDetails());
        assertEquals(employeeDetails, booking.getEmployeeDetails());
        assertEquals(carSummary, booking.getCar());
    }

    @Test
    void calculateFeeTest_PickupAndDeliveryAreTheSame() throws InvalidInputException {
        //arrange
        var pickupAddress = new Address("pickupVej",9999,"pickupCity");
        var pickupPlace = new Place("airport",pickupAddress,true);
        var deliveryAddress = new Address("pickupVej",9999,"pickupCity");
        var deliveryPlace = new Place("airport",deliveryAddress,true);
        var bookingCriteria = new BookingCriteria(pickupPlace,deliveryPlace,null,null);

        //act
        var fee =  contractImpl.calculateFee(bookingCriteria);

        //assert
        assertEquals(0.0,fee);
    }

    @Test
    void calculateFeeTest_MaxFee() throws InvalidInputException {
        //arrange
        var pickupAddress = new Address("pickupVej",9999,"pickupCity");
        var pickupPlace = new Place("airport",pickupAddress,true);
        var deliveryAddress = new Address("deliveryVej",8888,"deliveryCity");
        var deliveryPlace = new Place("hotel",deliveryAddress,true);
        var bookingCriteria = new BookingCriteria(pickupPlace,deliveryPlace,null,null);

        //act
        var fee =  contractImpl.calculateFee(bookingCriteria);

        //assert
        assertEquals(100.0,fee);
    }

    @Test
    void calculateFeeTest_OnlyStreetNameDiffers() throws InvalidInputException {
        //arrange
        var pickupAddress = new Address("pickupVej",9999,"pickupCity");
        var pickupPlace = new Place("airport",pickupAddress,true);
        var deliveryAddress = new Address("deliveryVej",9999,"pickupCity");
        var deliveryPlace = new Place("hotel",deliveryAddress,true);
        var bookingCriteria = new BookingCriteria(pickupPlace,deliveryPlace,null,null);

        //act
        var fee =  contractImpl.calculateFee(bookingCriteria);

        //assert
        assertEquals(25.0,fee);
    }

    @Test
    void calculateFeeTest_StreetNameAndCityDiffers() throws InvalidInputException {
        //arrange
        var pickupAddress = new Address("pickupVej",9999,"pickupCity");
        var pickupPlace = new Place("airport",pickupAddress,true);
        var deliveryAddress = new Address("deliveryVej",9999,"deliveryCity");
        var deliveryPlace = new Place("hotel",deliveryAddress,true);
        var bookingCriteria = new BookingCriteria(pickupPlace,deliveryPlace,null,null);

        //act
        var fee =  contractImpl.calculateFee(bookingCriteria);

        //assert
        assertEquals(50.0,fee);
    }

    @Test
    void calculateFeeTest_ThrowExceptionIfPickupPlaceIsNull() throws InvalidInputException {
        //arrange
        var deliveryAddress = new Address("deliveryVej",9999,"deliveryCity");
        var deliveryPlace = new Place("hotel",deliveryAddress,true);
        var bookingCriteria = new BookingCriteria(null,deliveryPlace,null,null);

        //act


        //assert
        Assertions.assertThrows(InvalidInputException.class, () -> {
            var fee =  contractImpl.calculateFee(bookingCriteria);
        });
    }

    @Test
    void calculateFeeTest_ThrowExceptionIfDeliveryPlaceIsNull() throws InvalidInputException {
        //arrange
        var pickupAddress = new Address("pickupVej",9999,"pickupCity");
        var pickupPlace = new Place("airport",pickupAddress,true);
        var bookingCriteria = new BookingCriteria(pickupPlace,null,null,null);

        //assert
        Assertions.assertThrows(InvalidInputException.class, () -> {
            var fee =  contractImpl.calculateFee(bookingCriteria);
        });
    }

    @Test
    void calculateFeeTest_ThrowExceptionIfBothPlacesAreNull() throws InvalidInputException {
        //arrange
        var bookingCriteria = new BookingCriteria(null,null,null,null);

        //assert
        Assertions.assertThrows(InvalidInputException.class, () -> {
            var fee =  contractImpl.calculateFee(bookingCriteria);
        });
    }



}
