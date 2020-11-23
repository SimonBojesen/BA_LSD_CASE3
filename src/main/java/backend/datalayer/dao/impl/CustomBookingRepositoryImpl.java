package backend.datalayer.dao.impl;

import backend.datalayer.dao.BookingRepository;
import backend.datalayer.dao.CustomBookingRepository;
import backend.datalayer.entity.BookingDB;
import booking.dto.BookingDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;


public class CustomBookingRepositoryImpl implements CustomBookingRepository
{
    @Autowired
    @Lazy
    BookingRepository bookingRepository;

    @Autowired
    EntityManager entityManager;

    @Override
    public BookingDB findBooking(Long id)
    {
        return bookingRepository.findById(id).get();
    }
}
