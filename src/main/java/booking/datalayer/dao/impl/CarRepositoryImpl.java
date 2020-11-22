package booking.datalayer.dao.impl;

import booking.datalayer.dao.CarRepository;
import booking.datalayer.dao.CustomCarRepository;
import booking.dto.BookingCriteria;
import booking.dto.CarSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Collection;

public class CarRepositoryImpl implements CustomCarRepository
{
    @Autowired
    @Lazy
    CarRepository carRepository;


    @Override
    public Collection<CarSummary> findAvailableCars(BookingCriteria bookingCriteria) {
        return null;
    }
}
