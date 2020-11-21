package backend.datalayer.dao.impl;

import backend.datalayer.dao.CarRepository;
import backend.datalayer.dao.CustomCarRepository;
import booking.dto.BookingCriteria;
import booking.dto.CarSummary;
import booking.entity.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.time.LocalDateTime;
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
