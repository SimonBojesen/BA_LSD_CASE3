package backend.datalayer.dao;

import backend.datalayer.entity.BookingDB;
import booking.dto.BookingDetails;
import booking.dto.BookingIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

public interface CustomBookingRepository {
    BookingDB findBooking(Long id);

}
