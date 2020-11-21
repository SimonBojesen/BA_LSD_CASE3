package backend.datalayer.dao;

import backend.datalayer.entity.EmployeeDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeDB, Long> {
}
