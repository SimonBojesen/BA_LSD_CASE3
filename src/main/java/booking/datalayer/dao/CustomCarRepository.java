package booking.datalayer.dao;

import booking.dto.BookingCriteria;
import booking.dto.CarSummary;
import java.util.Collection;

public interface CustomCarRepository {
    Collection<CarSummary> findAvailableCars(BookingCriteria bookingCriteria);
}
