package nl.backend.eindoprdracht.repositories;

import nl.backend.eindoprdracht.models.EmployeeAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeAccountRepository extends JpaRepository<EmployeeAccount, Long> {

}
