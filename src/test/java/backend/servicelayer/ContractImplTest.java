package backend.servicelayer;

import backend.datalayer.dao.BookingRepository;
import backend.datalayer.dao.CarRepository;
import backend.datalayer.dao.impl.CarRepositoryImpl;
import booking.Contract;
import booking.dto.BookingCriteria;
import booking.dto.BookingIdentifier;
import booking.dto.CarSummary;
import booking.entity.Address;
import booking.entity.Car;
import booking.entity.Place;
import booking.entity.Type;
import booking.eto.InvalidInputException;
import booking.eto.NotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.InstanceOf;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.*;

public class ContractImplTest
{
    // DOC
    CarRepository carRepository = mock(CarRepository.class);
    BookingRepository bookingRepository = mock(BookingRepository.class);

    // SUT
    Contract contractImpl = new ContractImpl(carRepository, bookingRepository);

    //Test data
    BookingCriteria bookingCriteria;
    Place samplePlace;
    Address sampleAddress;
    Car sampleCar;
    CarSummary sampleCarSummary;

    @BeforeEach
    public void setup() {
        sampleAddress = new Address("testvej", 1111, "testby");
        sampleCar = new Car("", "", Type.B, 200.0, 2, false);

        samplePlace = new Place("", sampleAddress, true);
        sampleCarSummary = new CarSummary(sampleCar, samplePlace);
        bookingCriteria = new BookingCriteria(samplePlace, samplePlace, LocalDateTime.now(), LocalDateTime.now());
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
        carList.add(sampleCarSummary);
        when(carRepository.findAvailableCars(any(BookingCriteria.class))).thenReturn(carList);

        // Act
        Collection<CarSummary> cars = contractImpl.listAvailableCars(bookingCriteria);

        // Assert
        assertNotNull(cars);
    }

    @Test
    public void mustCallBookingRepositoryWhenFindingBooking() throws NotFoundException, InvalidInputException
    {
        // Arrange
        // Act
        contractImpl.findBooking(new BookingIdentifier(1));

        // Assert
        verify(bookingRepository, times(1)).findBooking(anyInt());
    }


}
