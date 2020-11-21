package backend.datalayer.dao;

import backend.datalayer.entity.BookingDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingDB, Long> {
}
