package backend.datalayer.dao;

import backend.datalayer.entity.CarDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<CarDB, Long>, CustomCarRepository {
}

