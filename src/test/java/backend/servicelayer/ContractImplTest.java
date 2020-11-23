package backend.servicelayer;

import backend.datalayer.dao.AirportRepository;
import backend.datalayer.dao.BookingRepository;
import backend.datalayer.dao.CarRepository;
import backend.datalayer.dao.HotelRepository;
import backend.datalayer.entity.*;
import booking.Contract;
import booking.dto.BookingCriteria;
import booking.dto.BookingDetails;
import booking.dto.BookingIdentifier;
import booking.dto.CarSummary;
import booking.entity.*;
import booking.eto.InvalidInputException;
import booking.eto.NotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;

public class ContractImplTest
{
    // DOC
    CarRepository carRepository = mock(CarRepository.class);
    BookingRepository bookingRepository = mock(BookingRepository.class);
    AirportRepository airportRepository = mock(AirportRepository.class);
    HotelRepository hotelRepository = mock(HotelRepository.class);

    // SUT
    Contract contractImpl = new ContractImpl(carRepository, bookingRepository, airportRepository, hotelRepository);

    //Test data
    Car car = new Car("asdf", "bif1964", Type.E, 200.0, 5, true);
    Address hotelAddress = new Address("Ellehammervej yy", 2300, "København S");
    Address driverAddress = new Address("Hovedgaden", 5000, "Odense");
    Address deliveryAddress = new Address("Ellehøjvej", 2800, "Lyngby");
    Place pickupPlace = new Place("Hilton CPH airport", hotelAddress, true);
    Place deliveryPlace = new Place("Butikken", deliveryAddress, true);
    CarSummary carSummary = new CarSummary(car, pickupPlace);

    AddressDB hotelAddressDB = new AddressDB(hotelAddress);
    AddressDB driverAddressDB = new AddressDB(driverAddress);
    AddressDB deliveryAddressDB = new AddressDB(deliveryAddress);

    HotelDB hotelDB = new HotelDB(pickupPlace.getName(), hotelAddressDB, true, Rating.FIVE);


    CarDB carDB = new CarDB(car, backend.datalayer.constants.Place.HOTEL, hotelAddressDB);
    DriverDB driverDB = new DriverDB("Anders Sand", driverAddressDB, "anders@sand.nu", new Date(), 123, true, 543L);

    BookingCriteria bookingCriteria = new BookingCriteria(pickupPlace, deliveryPlace, LocalDateTime.now(), LocalDateTime.now());


    @BeforeEach
    public void setup() {
    }

    @Test
    public void mustCallCarRepositoryWhenListingAvailableCars() throws NotFoundException, InvalidInputException
    {
        // Arrange
        // Act
        contractImpl.listAvailableCars(bookingCriteria);

        // Assert
        verify(carRepository, times(1)).findAvailableCars(any(BookingCriteria.class));
    }

    @Test
    public void mustReturnListOfCars() throws NotFoundException, InvalidInputException
    {
        // Arrange
        Collection<CarSummary> carList = new ArrayList<>();
        carList.add(carSummary);
        when(carRepository.findAvailableCars(any(BookingCriteria.class))).thenReturn(carList);

        // Act
        Collection<CarSummary> cars = contractImpl.listAvailableCars(bookingCriteria);

        // Assert
        assertNotNull(cars);
    }

    @Test
    public void mustCallBookingRepositoryWhenFindingBooking() throws NotFoundException, InvalidInputException, NoSuchFieldException, IllegalAccessException
    {
        // Arrange
        BookingDB bookingDB = mock(BookingDB.class);
        when(bookingDB.getCar()).thenReturn(carDB);
        when(bookingDB.getPickUpDate()).thenReturn(new Date());
        when(bookingDB.getPickUpPlace()).thenReturn(hotelAddressDB);
        when(bookingDB.getDeliveryDate()).thenReturn(new Date());
        when(bookingDB.getDeliveryPlace()).thenReturn(deliveryAddressDB);
        when(bookingDB.getDrivers()).thenReturn(driverDB);
        when(bookingDB.getPrice()).thenReturn(550.5);
        when(bookingDB.getId()).thenReturn(1L);

        when(bookingRepository.findBooking(anyLong())).thenReturn(bookingDB);
        when(hotelRepository.findOne(any(Example.class))).thenReturn(Optional.of(hotelDB));

        // Act
        contractImpl.findBooking(new BookingIdentifier(1));

        // Assert
        verify(bookingRepository, times(1)).findBooking(anyLong());
    }

    @Test
    public void mustReturnBookingDetailsWhenFindingBooking() throws NotFoundException, InvalidInputException
    {
        // Arrange
        var expected = BookingDetails.class;
        BookingDB bookingDB = mock(BookingDB.class);
        when(bookingDB.getCar()).thenReturn(carDB);
        when(bookingDB.getPickUpDate()).thenReturn(new Date());
        when(bookingDB.getPickUpPlace()).thenReturn(hotelAddressDB);
        when(bookingDB.getDeliveryDate()).thenReturn(new Date());
        when(bookingDB.getDeliveryPlace()).thenReturn(deliveryAddressDB);
        when(bookingDB.getDrivers()).thenReturn(driverDB);
        when(bookingDB.getPrice()).thenReturn(550.5);
        when(bookingDB.getExtraFee()).thenReturn(7.5);
        when(bookingDB.getId()).thenReturn(1L);

        when(bookingRepository.findBooking(anyLong())).thenReturn(bookingDB);
        when(hotelRepository.findOne(any(Example.class))).thenReturn(Optional.of(hotelDB));

        // Act
        BookingDetails result = contractImpl.findBooking(new BookingIdentifier(4));

        // Assert
        assertEquals(expected, result.getClass());
    }
}
