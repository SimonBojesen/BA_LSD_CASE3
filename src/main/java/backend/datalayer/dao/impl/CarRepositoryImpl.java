package backend.datalayer.dao.impl;

import backend.datalayer.dao.CarRepository;
import booking.entity.Place;

import java.time.LocalDateTime;

public class CarRepositoryImpl implements CarRepository
{
    void findAvailableCars(LocalDateTime pickUpDate, Place pickUpPlace);
}
