package nl.backend.eindoprdracht.repositories;

import nl.backend.eindoprdracht.models.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, Long> {

}
