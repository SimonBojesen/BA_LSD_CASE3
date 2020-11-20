package backend.servicelayer;

import backend.datalayer.dao.CarRepository;
import booking.Contract;
import booking.dto.BookingCriteria;
import booking.entity.Place;
import booking.eto.InvalidInputException;
import booking.eto.NotFoundException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

public class ContractImplTest
{
    // SUT
    Contract contractImpl = new ContractImpl();
    // DOC
    CarRepository carRepository = mock(CarRepository.class);
    BookingCriteria bookingCriteria = mock(BookingCriteria.class);

    @Test
    public void mustCallCarRepositoryWhenListingAvailableCars() throws NotFoundException, InvalidInputException
    {
        // Arrange
        // Act
        contractImpl.listAvailableCars(bookingCriteria);

        // Assert
        //verify(carRepository, times(1)).findAvailableCars(any(BookingCriteria.class));
        // egentlig er carRepository vel kun interesseret i at kende til sted og tid?
        verify(carRepository, times(1)).findAvailableCars(any(LocalDateTime.class), any(Place.class));
    }
}
