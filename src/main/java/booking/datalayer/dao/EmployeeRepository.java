package booking.datalayer.dao;

import booking.datalayer.entity.EmployeeDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeDB, Long> {
    Optional<EmployeeDB> findBySocialSecurityNumber(int socialSecurityNumber);
}
