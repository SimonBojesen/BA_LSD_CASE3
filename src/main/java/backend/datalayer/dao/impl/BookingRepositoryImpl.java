package backend.datalayer.dao.impl;

import backend.datalayer.dao.BookingRepository;
import backend.datalayer.dao.CustomBookingRepository;
import backend.datalayer.entity.BookingDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;


public class BookingRepositoryImpl implements CustomBookingRepository
{
    @Autowired
    @Lazy
    BookingRepository bookingRepository;

    @Override
    public BookingDB findBooking(int id)
    {
        return null;
    }
}
