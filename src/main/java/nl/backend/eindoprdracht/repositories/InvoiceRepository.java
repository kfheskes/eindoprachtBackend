package nl.backend.eindoprdracht.repositories;

import nl.backend.eindoprdracht.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByCustomerAccountId(Long customerAccountId);
}
