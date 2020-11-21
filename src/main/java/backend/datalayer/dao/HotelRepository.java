package backend.datalayer.dao;

import backend.datalayer.entity.HotelDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<HotelDB, Long> {
}
