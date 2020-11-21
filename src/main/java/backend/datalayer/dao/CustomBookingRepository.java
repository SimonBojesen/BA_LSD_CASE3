package backend.datalayer.dao;

import backend.datalayer.entity.BookingDB;
import booking.dto.BookingDetails;
import booking.dto.BookingIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

public interface CustomBookingRepository {
    // Skal måske wrappes i noget andet, så det ikke er DB objekt der returneres...
    BookingDB findBooking(int id);

}
