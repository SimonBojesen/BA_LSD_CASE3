package booking.datalayer.dao;

import booking.datalayer.entity.CarDB;
import booking.dto.BookingCriteria;
import booking.dto.CarSummary;
import java.util.Collection;
import java.util.List;

public interface CustomCarRepository {
    List<CarDB> findAvailableCars(BookingCriteria bookingCriteria);
}
